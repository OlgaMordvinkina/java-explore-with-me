package ru.practicum.main.category.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.main.category.cervices.CategoryService;
import ru.practicum.main.category.dto.CategoryDto;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryPublicController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryPublicControllerTest {
    @MockBean
    private final CategoryService service;
    private final ObjectMapper mapper;
    private final MockMvc mvc;
    private final String path = "/categories";
    public static final long id = 1L;
    private final CategoryDto categoryDto = new CategoryDto(id, "nameCategory");
    private final List<CategoryDto> categories = Collections.singletonList(categoryDto);

    @Test
    void getCategories_returnListCategoryDto_Test() throws Exception {
        when(service.getCategories(anyInt(), anyInt())).thenReturn(categories);

        mvc.perform(get(path)
                        .content(mapper.writeValueAsString(categories))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void getCategory_returnCategoryDto_Test() throws Exception {
        when(service.getCategory(anyLong())).thenReturn(categoryDto);

        mvc.perform(get(path + "/" + id)
                        .content(mapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("nameCategory"));
    }
}
