package ru.practicum.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.dto.VisitDto;
import ru.practicum.service.models.StatMapper;
import ru.practicum.service.repositories.StatsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository repository;

    @Override
    public void createStat(VisitDto visitDto) {
        log.info("Сохранен VisitDto {}", visitDto);
        repository.save(StatMapper.toStat(visitDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatDto> getStats(UserRequestDto request) {
        List<StatDto> stats = repository.findByParams(request);
        log.info("Получена Stats: {}", stats);
        return stats;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Long> findViews(List<String> eventIds) {
        Map<Long, Long> views = new HashMap<>();
        repository.getViews(eventIds).forEach(it -> views.put(Long.valueOf(it.getUri().replace("/events/", "")), it.getViews()));
        log.info("Получены views: {}", views);
        return views;
    }
}
