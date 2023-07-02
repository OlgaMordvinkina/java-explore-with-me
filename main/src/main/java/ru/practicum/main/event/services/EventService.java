package ru.practicum.main.event.services;

import ru.practicum.main.event.dto.*;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventShortDto> getEventsUser(Long userId, int from, int size);

    EventFullDto addEvent(Long userId, NewEventDto newEvent);

    EventFullDto getEventUser(Long userId, Long eventId);

    EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest request);

    List<ParticipationRequestDto> getEventUserParticipation(Long userId, Long eventId);

    EventRequestStatusUpdateResult setStatusEvent(Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    List<EventShortDto> getEventsFilter(EventFilterDto filterDto, HttpServletRequest request);

    EventFullDto getEventFilterById(Long id, HttpServletRequest request);

    List<EventFullDto> getEventsFilterAdmin(EventFilterAdminDto filter);

    EventFullDto updateEventFilterAdminById(Long eventId, UpdateEventAdminRequest event);

}
