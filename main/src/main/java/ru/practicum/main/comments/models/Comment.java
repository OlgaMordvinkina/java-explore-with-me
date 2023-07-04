package ru.practicum.main.comments.models;

import lombok.*;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.user.models.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
    @Column(nullable = false)
    private String text;
    @Column(name = "created", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "updated")
    private LocalDateTime updateDate;
}

