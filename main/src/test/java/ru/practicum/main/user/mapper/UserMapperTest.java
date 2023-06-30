package ru.practicum.main.user.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.models.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {
    public static final String name = "name";
    public static final String mail = "mail@mail.ru";

    private final User user = new User(1L, name, mail);
    private final UserDto userDto = new UserDto(1L, name, mail);
    private final NewUserRequest newUserRequest = new NewUserRequest(mail, name);

    @Test
    void toUserMappingTest() {
        User expectedUser = UserMapper.toUser(newUserRequest);
        expectedUser.setId(1L);
        assertEquals(user.toString(), expectedUser.toString());
    }

    @Test
    void toUserDtoMappingTest() {
        UserDto expectedUserDto = UserMapper.toUserDto(user);
        assertEquals(userDto.toString(), expectedUserDto.toString());
    }
}
