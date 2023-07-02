package ru.practicum.main.user.cervices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.exceptions.NotFoundUserException;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.models.User;
import ru.practicum.main.user.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        if (ids == null) {
            List<UserDto> users = repository.findAll(PageRequest.of(from / size, size)).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
            log.info("Получены Users: {}", users);
            return users;
        }
        List<UserDto> users = repository.findAllByIdIn(ids, PageRequest.of(from / size, size)).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
        log.info("Получены Users: {}", users);
        return users;
    }

    @Override
    public UserDto addUser(NewUserRequest userRequest) {
        User user = repository.save(UserMapper.toUser(userRequest));
        log.info("Добавлен User: {}", user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = repository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        log.info("Удален User: {}", user);
        repository.deleteById(userId);
    }
}
