package ru.practicum.main.request.cervices;

import org.springframework.expression.AccessException;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getRequestsUser(Long userId);

    ParticipationRequestDto addRequestUsers(Long userId, Long eventId) throws AccessException;

    ParticipationRequestDto updateRequestUsers(Long userId, Long requestId);
}
