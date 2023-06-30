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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CompilationPublicController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompilationPublicControllerTest {
    @MockBean
    private final CompilationService service;
    private final ObjectMapper mapper;
    private final MockMvc mvc;
    private final String path = "/compilations";
    public static final long id = 1L;
    public static final String title = "title";
    private final CompilationDto compilationDto = new CompilationDto(id, title, true, Collections.singletonList(new EventShortDto()));
    private final NewCompilationDto newCompilationDto = new NewCompilationDto(title, true, Collections.singletonList(1L));

    @Test
    void getCategories_returnListCompilationDto_Test() throws Exception {
        List<CompilationDto> compilations = Collections.singletonList(compilationDto);
        when(service.getCompilations(anyBoolean(), anyInt(), anyInt()))
                .thenReturn(compilations);

        mvc.perform(get(path + "?pinned=true&from=0&size=10")
                        .content(mapper.writeValueAsString(compilations))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getCompilation_returnCompilationDto_Test() throws Exception {
        when(service.getCompilationById(anyLong())).thenReturn(compilationDto);

        mvc.perform(get(path + "/" + id)
                        .content(mapper.writeValueAsString(compilationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.pinned").isBoolean())
                .andExpect(jsonPath("$.events").isNotEmpty());
    }
}
