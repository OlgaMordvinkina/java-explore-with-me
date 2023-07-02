package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.VisitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void create(VisitDto visitDto) {
        post("/hit", visitDto);
    }

    public ResponseEntity<Object> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        String urisString = String.join(",", uris);
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", urisString,
                "unique", unique
        );
        return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }

    public ResponseEntity<Object> findViews(List<Long> ids) {
        String eventIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        Map<String, Object> parameters = Map.of(
                "eventIds", eventIds
        );
        return get("/views?eventIds={eventIds}", parameters);
    }
}
