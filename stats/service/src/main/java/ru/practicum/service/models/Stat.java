package ru.practicum.service.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "stats")
public class Stat {
    public static final String PROP_ID = "id";
    public static final String PROP_APP = "app";
    public static final String PROP_URI = "uri";
    public static final String PROP_IP = "ip";
    public static final String PROP_TIMESTAMP = "timestamp";

    public Stat(String app, String uri, String ip) {
        this.app = app;
        this.uri = uri;
        this.ip = ip;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private LocalDateTime timestamp;
}
