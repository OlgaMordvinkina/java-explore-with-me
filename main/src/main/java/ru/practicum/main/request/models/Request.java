package ru.practicum.main.request.models;

import lombok.*;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.request.enums.StateParticipation;
import ru.practicum.main.user.models.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "id")
    private User requester;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StateParticipation status;
    @Column(nullable = false)
    private LocalDateTime created;
}


