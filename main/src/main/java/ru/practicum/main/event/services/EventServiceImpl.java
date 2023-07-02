package ru.practicum.main.event.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.VisitDto;
import ru.practicum.main.category.exceptions.NotFoundCategoryException;
import ru.practicum.main.category.models.Category;
import ru.practicum.main.category.repositories.CategoryRepository;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.enums.State;
import ru.practicum.main.event.enums.StateAction;
import ru.practicum.main.event.enums.StateSort;
import ru.practicum.main.event.exceptions.NotFoundEventException;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.models.EventShort;
import ru.practicum.main.event.models.Location;
import ru.practicum.main.event.repositories.EventRepository;
import ru.practicum.main.event.repositories.LocationRepository;
import ru.practicum.main.exceptions.AccessException;
import ru.practicum.main.exceptions.LimitException;
import ru.practicum.main.exceptions.NotAvailableException;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.enums.StateParticipation;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.models.Request;
import ru.practicum.main.request.repositories.RequestRepository;
import ru.practicum.main.user.exceptions.NotFoundUserException;
import ru.practicum.main.user.models.User;
import ru.practicum.main.user.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@ComponentScan(basePackages = {"ru.practicum.client"})
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final StatsClient client;

    @Override
    public List<EventShortDto> getEventsUser(Long userId, int from, int size) {
        User user = existUserById(userId);
        List<EventShortDto> events = eventRepository.findAllByInitiator(user, PageRequest.of(from / size, size))
                .map(EventMapper::toEventShortDto)
                .toList();
        log.info("Получены Events: {}", events);
        return events;
    }

    @Transactional
    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEvent) {
        User user = existUserById(userId);
        Category category = existCategoryById(newEvent.getCategory());
        Location location = addLocation(newEvent.getLocation());
        Event entity = EventMapper.toEvent(newEvent, category, user, location);
        Event event = eventRepository.save(entity);
        log.info("Добавлен Event: {}", event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto getEventUser(Long userId, Long eventId) {
        existUserById(userId);
        Event event = existEventById(eventId);
        checkOfAuthorship(userId, eventId, event.getInitiator().getId());
        EventFullDto fullEvent = EventMapper.toEventFullDto(event);
        log.info("Получен Event: {}", fullEvent);
        return fullEvent;
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest request) {
        existUserById(userId);
        Event event = existEventById(eventId);

        checkOfAuthorship(userId, eventId, event.getInitiator().getId());

        if (event.getState().equals(State.PUBLISHED)) {
            throw new NotAvailableException();
        }
        event = updateEventByFilter(
                event, request.getTitle(), request.getAnnotation(), request.getCategory(),
                request.getPaid(), request.getEventDate(), request.getDescription(), request.getParticipantLimit(),
                request.getLocation(), request.getRequestModeration(), request.getStateAction()
        );

        log.info("Изменён Event: {}", event);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<ParticipationRequestDto> getEventUserParticipation(Long userId, Long eventId) {
        existUserById(userId);
        Event event = existEventById(eventId);
        checkOfAuthorship(userId, eventId, event.getInitiator().getId());

        List<ParticipationRequestDto> requests = requestRepository.findAllByEventId(eventId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());

        log.info("Получены Requests: {}", requests);
        return requests;
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult setStatusEvent(Long userId, Long eventId, EventRequestStatusUpdateRequest request) {
        existUserById(userId);
        Event event = existEventById(eventId);

        checkOfAuthorship(userId, eventId, event.getInitiator().getId());

        List<ParticipationRequestDto> confirmed = new ArrayList<>();
        List<ParticipationRequestDto> rejected = new ArrayList<>();

        List<Request> requests = requestRepository.findAllById(request.getRequestIds());
        Long confirmedRequest = requestRepository.getConfirmedRequest(eventId);

        if (event.getParticipantLimit() != 0) {
            for (int i = 0; i < requests.size(); i++) {
                Request obj = requests.get(i);

                if (!obj.getStatus().equals(StateParticipation.PENDING)) {
                    throw new NotAvailableException();
                }

                if (request.getStatus().equals(StateParticipation.REJECTED) && event.getParticipantLimit() != confirmedRequest) {
                    saveRejectedRequest(obj, rejected);
                }

                if (event.getParticipantLimit() == confirmedRequest) {
                    saveRejectedRequest(obj, rejected);

                    if (i == requests.size() - 1) {
                        throw new LimitException();
                    }

                    continue;
                }

                obj.setStatus(request.getStatus());
                confirmed.add(RequestMapper.toRequestDto(obj));
                ++confirmedRequest;
                requestRepository.save(obj);
            }
        }

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(confirmed, rejected);

        log.info("Обработаны Requests: {}", result);
        return result;
    }

    @Override
    public List<EventShortDto> getEventsFilter(EventFilterDto filter, HttpServletRequest request) {
        addStat(request);

        List<EventShort> entityShort = eventRepository.findByParams(filter);

        List<Long> eventIds = entityShort.stream()
                .map(EventShort::getId)
                .collect(Collectors.toList());

        Map<Long, Long> confirmedRequest = convertToMap(requestRepository.getConfirmedRequest(eventIds));

        Map<Long, Long> views = convertToMap(client.findViews(eventIds).getBody());

        List<EventShortDto> events = entityShort
                .stream()
                .map(it -> EventMapper.toEventShortDto(
                        it,
                        checkNullOrEmpty(confirmedRequest, it.getId()) ? confirmedRequest.get(it.getId()) : 0,
                        checkNullOrEmpty(views, it.getId()) ? views.get(it.getId()) : 0)
                )
                .collect(Collectors.toList());
        log.info("Получены Events: {}", events);
        if (filter.getSort() != null && filter.getSort().equals(StateSort.VIEWS)) {
            return events.stream().sorted(Comparator.comparing(EventShortDto::getViews)).collect(Collectors.toList());
        }
        return events;
    }

    @Override
    public EventFullDto getEventFilterById(Long eventId, HttpServletRequest request) {
        Event event = existEventById(eventId);
        if (event.getState().equals(State.CANCELED) || event.getState().equals(State.PENDING)) {
            throw new NotFoundEventException(eventId);
        }
        Long confirmedRequest = requestRepository.getConfirmedRequest(eventId);
        addStat(request);

        Map<Long, Long> stats = convertToMap(client.findViews(List.of(eventId)).getBody());
        EventFullDto eventFull = EventMapper.toEventFullDto(
                event,
                confirmedRequest,
                checkNullOrEmpty(stats, eventId) ? stats.get(eventId) : 0
        );
        log.info("Получены Event: {}", eventFull);
        return eventFull;
    }

    @Override
    public List<EventFullDto> getEventsFilterAdmin(EventFilterAdminDto filter) {
        List<Event> events = eventRepository.findByParamAdmin(filter);

        List<Long> eventIds = events.stream()
                .map(Event::getId)
                .collect(Collectors.toList());

        Map<Long, Long> confirmedRequest = convertToMap(requestRepository.getConfirmedRequest(eventIds));

        Map<Long, Long> views = convertToMap(client.findViews(eventIds).getBody());

        List<EventFullDto> result = events.stream()
                .map(it -> EventMapper.toEventFullDto(
                        it,
                        checkNullOrEmpty(confirmedRequest, it.getId()) ? confirmedRequest.get(it.getId()) : 0,
                        checkNullOrEmpty(views, it.getId()) ? views.get(it.getId()) : 0
                ))
                .collect(Collectors.toList());
        log.info("Получены Event: {}", result);
        return result;
    }

    @Transactional
    @Override
    public EventFullDto updateEventFilterAdminById(Long eventId, UpdateEventAdminRequest request) {
        Event event = existEventById(eventId);


        if (event.getState().equals(State.PUBLISHED) || event.getState().equals(State.CANCELED)) {
            throw new NotAvailableException();
        }

        event = updateEventByFilter(
                event, request.getTitle(), request.getAnnotation(), request.getCategory(),
                request.getPaid(), request.getEventDate(), request.getDescription(), request.getParticipantLimit(),
                request.getLocation(), request.getRequestModeration(), request.getStateAction()
        );

        Event save = eventRepository.save(event);
        log.info("Изменён Event: {}", event);
        if (save.getState().equals(State.REJECTED)) {
            throw new NotAvailableException();
        }

        return EventMapper.toEventFullDto(save);
    }

    private Event updateEventByFilter(Event event, String title, String annotation, Long category, Boolean paid,
                                      LocalDateTime eventDate, String description, Integer participantLimit, Location location,
                                      Boolean requestModeration, StateAction state) {
        if (title != null) event.setTitle(title);
        if (annotation != null) event.setAnnotation(annotation);
        if (category != null) event.setCategory(existCategoryById(category));
        if (paid != null) event.setPaid(paid);
        if (eventDate != null) event.setEventDate(eventDate);
        if (description != null) event.setDescription(description);
        if (participantLimit != null) event.setParticipantLimit(participantLimit);
        if (location != null) event.setLocation(addLocation(location));
        if (requestModeration != null) event.setRequestModeration(requestModeration);

        if (state != null) {
            switch (state) {
                case PUBLISH_EVENT: {
                    event.setState(State.PUBLISHED);
                    event.setPublishedOn(getLocalDate());
                    break;
                }
                case SEND_TO_REVIEW: {
                    event.setState(State.PENDING);
                    break;
                }
                case REJECT_EVENT: {
                    event.setState(State.REJECTED);
                    break;
                }
                case CANCEL_REVIEW: {
                    event.setState(State.CANCELED);
                    break;
                }
            }
        }
        return event;
    }

    private Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    private void addStat(HttpServletRequest request) {
        client.create(
                VisitDto.builder()
                        .app("ewm-main")
                        .uri(request.getRequestURI())
                        .ip(request.getRemoteAddr())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    private Event existEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
    }

    private User existUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
    }

    private Category existCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundCategoryException(catId));

    }

    private void checkOfAuthorship(Long userId, Long eventId, Long owner) {
        if (!Objects.equals(owner, userId)) {
            throw new AccessException(eventId, "Event");
        }
    }

    private boolean checkNullOrEmpty(Map<Long, Long> map, Long id) {
        return map != null && !map.isEmpty() && map.get(id) != null;
    }

    public static Map<Long, Long> convertToMap(Object object) {
        Map<String, Integer> map = (HashMap) object;
        Map<Long, Long> result = new LinkedHashMap<>();

        if (object == null) {
            return result;
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.put(Long.parseLong(entry.getKey()), Long.valueOf(entry.getValue()));
        }
        return result;
    }

    public static Map<Long, Long> convertToMap(List<String> list) {
        return list.stream()
                .map(it -> (it.split(",")))
                .collect(Collectors.toMap(p -> Long.parseLong(p[0]), p -> Long.parseLong(p[1]), (r, s) -> r));
    }

    private LocalDateTime getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
    }

    private void saveRejectedRequest(Request obj, List<ParticipationRequestDto> rejected) {
        obj.setStatus(StateParticipation.REJECTED);
        rejected.add(RequestMapper.toRequestDto(obj));
        requestRepository.save(obj);
    }
}
