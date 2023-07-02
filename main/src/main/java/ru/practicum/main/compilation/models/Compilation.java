package ru.practicum.main.compilation.models;

import lombok.*;
import ru.practicum.main.event.models.Event;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private boolean pinned;
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;
}
