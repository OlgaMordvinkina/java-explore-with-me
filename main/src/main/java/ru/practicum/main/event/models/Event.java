package ru.practicum.main.event.models;

import lombok.*;
import ru.practicum.main.category.models.Category;
import ru.practicum.main.event.enums.State;
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
@Table(name = "events")
public class Event {
    public static final String PROP_ID = "id";
    public static final String PROP_TITLE = "title";
    public static final String PROP_ANNOTATION = "annotation";
    public static final String PROP_CATEGORY = "category";
    public static final String PROP_PAID = "paid";
    public static final String PROP_DATE = "eventDate";
    public static final String PROP_INITIATOR = "initiator";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_LIMIT = "participantLimit";
    public static final String PROP_CREATED = "createdOn";
    public static final String PROP_LOCATION = "location";
    public static final String PROP_MODERATION = "requestModeration";
    public static final String PROP_PUBLISHED = "publishedOn";
    public static final String PROP_STATE = "state";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(nullable = false)
    private boolean paid;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User initiator;
    @Column(nullable = false)
    private String description;
    @Column(name = "participant_limit", nullable = false)
    private int participantLimit;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @Column(name = "request_moderation", nullable = false)
    private boolean requestModeration;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;
}
