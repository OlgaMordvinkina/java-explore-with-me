package ru.practicum.service.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.model.StatDto;
import ru.practicum.model.VisitDto;
import ru.practicum.service.models.StatMapper;
import ru.practicum.service.models.UserRequestDto;
import ru.practicum.service.repositories.StatsRepository;

import java.util.List;

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
}
