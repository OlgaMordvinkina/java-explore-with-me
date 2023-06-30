package ru.practicum.service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.VisitDto;
import ru.practicum.service.services.StatsService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StatsControllerTest {
    @MockBean
    private final StatsService service;
    private final ObjectMapper mapper;
    private final MockMvc mvc;

    private VisitDto visit = new VisitDto(1L, "ewm-main-service", "/events/1", "100.000.00.1", LocalDateTime.now());
    private StatDto stat = new StatDto("app-main-service", "/events/1", 5);
    private List<StatDto> stats = Collections.singletonList(stat);

    @Test
    void createStatTest() throws Exception {
        mvc.perform(post("/hit")
                        .content(mapper.writeValueAsString(visit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getStatsTest() throws Exception {
        when(service.getStats(any())).thenReturn(stats);

        mvc.perform(get("/stats?start=2020-01-01 20:00:00&end=2022-01-06 11:00:23&unique=false")
                        .content(mapper.writeValueAsString(stats)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getStats_WrongDateStartTest() throws Exception {
        when(service.getStats(any())).thenReturn(stats);

        mvc.perform(get("/stats?start=28-01-01 20:00:00&end=2022-01-06 11:00:23&unique=true")
                        .content(mapper.writeValueAsString(stats)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getStats_StartMoreEndTest() throws Exception {
        when(service.getStats(any())).thenReturn(stats);

        mvc.perform(get("/stats?start=2028-01-01 20:00:00&end=2022-01-06 11:00:23&unique=false")
                        .content(mapper.writeValueAsString(stats)))
                .andExpect(status().isBadRequest());
    }
}
