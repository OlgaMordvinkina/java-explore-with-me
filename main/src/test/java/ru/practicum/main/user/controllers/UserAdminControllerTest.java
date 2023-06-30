package ru.practicum.main.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.user.cervices.UserService;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.dto.UserDto;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserAdminController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserAdminControllerTest {
    @MockBean
    private final UserService service;
    private final ObjectMapper mapper;
    private final MockMvc mvc;
    private final String path = "/admin/users";
    public static final long id = 1L;
    public static final String name = "name";
    public static final String mail = "mail@mail.ru";
    private final UserDto userDto = new UserDto(id, name, mail);
    private final List<UserDto> users = Collections.singletonList(userDto);
    private final NewUserRequest newUserRequest = new NewUserRequest(mail, name);

    @Test
    void getUsers_returnListUserDto_Test() throws Exception {
        when(service.getUsers(any(), anyInt(), anyInt())).thenReturn(users);

        mvc.perform(get(path + "?ids=1&from=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addUser_returnUserDto_Test() throws Exception {
        when(service.addUser(any())).thenReturn(userDto);

        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(newUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.email").value(mail))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void addUserNameIsEmpty_returnBadRequest_Test() throws Exception {
        newUserRequest.setName("");
        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(newUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserEmailIsEmpty_returnBadRequest_Test() throws Exception {
        newUserRequest.setEmail("");
        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(newUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserEmailIsNotValid_returnBadRequest_Test() throws Exception {
        newUserRequest.setEmail("mail");
        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(newUserRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUser_returnIsNoContent_Test() throws Exception {
        mvc.perform(delete(path + "/" + id))
                .andExpect(status().isNoContent());
    }
}
