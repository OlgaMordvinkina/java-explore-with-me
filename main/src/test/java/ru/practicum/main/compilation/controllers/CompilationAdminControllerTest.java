package ru.practicum.main.compilation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.services.CompilationService;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompilationAdminController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompilationAdminControllerTest {
    @MockBean
    private final CompilationService service;
    private final ObjectMapper mapper;
    private final MockMvc mvc;
    private final String path = "/admin/compilations";
    public static final long id = 1L;
    public static final String title = "title";
    private final CompilationDto compilationDto = new CompilationDto(id, title, true, Collections.singletonList(new EventShortDto()));
    private final NewCompilationDto newCompilationDto = new NewCompilationDto(title, true, Collections.singletonList(1L));

    @Test
    void addCompilation_returnCompilationDto_Test() throws Exception {
        when(service.addCompilation(any())).thenReturn(compilationDto);

        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(newCompilationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.pinned").isBoolean())
                .andExpect(jsonPath("$.events").isNotEmpty());
    }

    @Test
    void addCompilationTitleIsEmpty_returnBadRequest_Test() throws Exception {
        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(new NewCompilationDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateCompilation_returnCompilationDto_Test() throws Exception {
        when(service.updateCompilation(anyLong(), any())).thenReturn(compilationDto);

        mvc.perform(patch(path + "/" + id)
                        .content(mapper.writeValueAsString(newCompilationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.pinned").isBoolean())
                .andExpect(jsonPath("$.events").isNotEmpty());
    }

    @Test
    void updateCompilationTitleIsEmpty_returnBadRequest_Test() throws Exception {
        mvc.perform(post(path)
                        .content(mapper.writeValueAsString(new NewCompilationDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCompilation_returnIsNoContent_Test() throws Exception {
        mvc.perform(delete(path + "/" + id))
                .andExpect(status().isNoContent());
    }
}
