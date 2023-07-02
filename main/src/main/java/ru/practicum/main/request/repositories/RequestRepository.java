package ru.practicum.main.request.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.request.models.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByEventId(Long eventId);

    Request findRequestByRequesterIdAndEventId(Long requesterId, Long eventId);

    @Query("SELECT count(r.status) " +
            "FROM Request r " +
            "where r.event.id = :eventId AND r.status = 'CONFIRMED'")
    Long getConfirmedRequest(Long eventId);

    @Query("SELECT r.event.id, count(r.status) " +
            "FROM Request r " +
            "where r.event.id IN :eventIds AND r.status = 'CONFIRMED' " +
            "GROUP BY r.event.id")
    List<String> getConfirmedRequest(List<Long> eventIds);
}
