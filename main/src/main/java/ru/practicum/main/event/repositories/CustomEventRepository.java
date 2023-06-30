package ru.practicum.main.event.repositories;

import ru.practicum.main.event.dto.EventFilterAdminDto;
import ru.practicum.main.event.dto.EventFilterDto;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.models.EventShort;

import java.util.List;

public interface CustomEventRepository {
    List<EventShort> findByParams(EventFilterDto filter);

    List<Event> findByParamAdmin(EventFilterAdminDto filter);
}
