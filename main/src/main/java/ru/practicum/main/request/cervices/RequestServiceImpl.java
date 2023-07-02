package ru.practicum.main.request.cervices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.enums.State;
import ru.practicum.main.event.exceptions.NotFoundEventException;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.exceptions.AccessException;
import ru.practicum.main.exceptions.LimitException;
import ru.practicum.main.exceptions.NotAvailableException;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.enums.StateParticipation;
import ru.practicum.main.request.exceptions.NotFoundRequestException;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.models.Request;
import ru.practicum.main.request.repositories.RequestRepository;
import ru.practicum.main.user.exceptions.NotFoundUserException;
import ru.practicum.main.user.models.User;
import ru.practicum.main.user.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> getRequestsUser(Long userId) {
        existUserById(userId);
        List<ParticipationRequestDto> requests = requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        log.info("Получены Requesters: {}", requests);
        return requests;
    }

    @Transactional
    @Override
    public ParticipationRequestDto addRequestUsers(Long userId, Long eventId) {
        User user = existUserById(userId);
        Event event = existEventById(eventId);
        if (requestRepository.findRequestByRequesterIdAndEventId(userId, eventId) != null) {
            throw new NotAvailableException("Нельзя добавить повторный запрос");
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new NotAvailableException("Невозможно сделать запрос на своё событие.");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new NotAvailableException("Нельзя участвовать в неопубликованном событии.");
        }

        Long confirmedRequest = requestRepository.getConfirmedRequest(eventId);

        if (event.getParticipantLimit() != 0 && confirmedRequest == event.getParticipantLimit()) {
            throw new LimitException();
        }

        Request request = Request.builder()
                .requester(user)
                .event(event)
                .status((
                        !event.isRequestModeration() || event.getParticipantLimit() == 0
                                ? StateParticipation.CONFIRMED
                                : StateParticipation.PENDING)
                )
                .created(getLocalDate())
                .build();

        log.info("Добавлен Request: {}", request);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Transactional
    @Override
    public ParticipationRequestDto updateRequestUsers(Long userId, Long requestId) {
        existUserById(userId);
        Request request = existRequestById(requestId);

        if (!userId.equals(request.getRequester().getId())) {
            throw new AccessException("Невозможно обновить запрос чужой запрос.");
        }
        request.setStatus(StateParticipation.CANCELED);
        log.info("Обновлён Request: {}", request);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    private Event existEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
    }

    private User existUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
    }

    private Request existRequestById(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new NotFoundRequestException(requestId));
    }

    private LocalDateTime getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }
}
