package ru.practicum.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.service.models.Stat;

@Repository
public interface StatsRepository extends JpaRepository<Stat, Long>, CustomStatsRepository {
}
