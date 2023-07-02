package ru.practicum.main.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.cervices.UserService;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {
    private final UserService service;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size) {
        log.info("Получен запрос GET /admin/users");
        return service.getUsers(ids, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto addUser(@Valid @RequestBody NewUserRequest userRequest) {
        log.info("Получен запрос POST /admin/users");
        return service.addUser(userRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Получен запрос DELETE /admin/users/{userId}");
        service.deleteUser(userId);
    }

}

