package ru.practicum.main.event.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.user.models.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {
    Page<Event> findAllByInitiator(User user, Pageable pageable);

    List<Event> findAllByCategoryId(Long catId);
}
