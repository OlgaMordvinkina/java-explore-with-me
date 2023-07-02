package ru.practicum.main.user.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.user.cervices.UserServiceImpl;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.exceptions.NotFoundUserException;
import ru.practicum.main.user.models.User;
import ru.practicum.main.user.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;
    public static final long id = 1L;
    public static final String name = "name";
    public static final String mail = "mail@mail.ru";
    private final User user = new User(id, name, mail);
    private final UserDto userDto = new UserDto(id, name, mail);
    private final NewUserRequest newUserRequest = new NewUserRequest(mail, name);
    private final List<UserDto> users = Collections.singletonList(userDto);

    @Test
    void addUserTest() {
        when(repository.save(any())).thenReturn(user);
        service.addUser(newUserRequest);
        verify(repository).save(any());
    }

    @Test
    void deleteUserTest() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(user));

        service.deleteUser(1L);
        verify(repository).deleteById(anyLong());
    }

    @Test
    void deleteUser_returnNotFoundUserExceptionTest() {
        assertThrows(NotFoundUserException.class, () -> service.deleteUser(id));
    }

    @Test
    void getUsersTest() {
        when(repository.findAllByIdIn(any(), any(Pageable.class))).thenReturn(new PageImpl<>(Collections.singletonList(user)));

        List<UserDto> expectedUsers = service.getUsers(Collections.singletonList(1L), 0, 10);

        assertEquals(expectedUsers, users);
        assertNotNull(expectedUsers);
        assertFalse(expectedUsers.isEmpty());
    }

}
