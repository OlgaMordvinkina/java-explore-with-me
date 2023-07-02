package ru.practicum.main.user.mapper;

import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.models.User;

public class UserMapper {
    public static User toUser(NewUserRequest userRequest) {
        return new User(
                null,
                userRequest.getName(),
                userRequest.getEmail()
        );
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
