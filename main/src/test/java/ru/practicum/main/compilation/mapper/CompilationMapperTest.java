package ru.practicum.main.compilation.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.dto.UpdateCompilationRequest;
import ru.practicum.main.compilation.models.Compilation;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilationMapperTest {
    public static final long id = 1L;
    public static final String title = "title";
    private final Compilation compilation = new Compilation(id, title, true, new ArrayList<>());
    private final CompilationDto compilationDto = new CompilationDto(id, title, true, new ArrayList<>());
    private final NewCompilationDto newCompilationDto = new NewCompilationDto(title, true, Collections.singletonList(1L));
    private final UpdateCompilationRequest request = new UpdateCompilationRequest(title, true, new ArrayList<>());

    @Test
    void toCompilationFromNewCompilationDtoMappingTest() {
        Compilation expectedCompilation = CompilationMapper.toCompilation(newCompilationDto, new ArrayList<>());
        expectedCompilation.setId(1L);
        assertEquals(compilation.toString(), expectedCompilation.toString());
    }

    @Test
    void toCompilationFromUpdateCompilationRequestMappingTest() {
        Compilation expectedCompilation = CompilationMapper.toCompilation(id, request, new ArrayList<>());
        expectedCompilation.setId(1L);
        assertEquals(compilation.toString(), expectedCompilation.toString());
    }

    @Test
    void toCompilationDtoFromCompilationMappingTest() {
        CompilationDto expectedCompilationDto = CompilationMapper.toCompilationDto(compilation);
        assertEquals(compilationDto.toString(), expectedCompilationDto.toString());
    }
}
