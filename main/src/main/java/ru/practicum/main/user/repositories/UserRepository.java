package ru.practicum.main.user.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.user.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAllByIdIn(List<Long> ids, Pageable pageable);
}
