package ru.practicum.main.event.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.dto.EventFilterAdminDto;
import ru.practicum.main.event.dto.EventFilterDto;
import ru.practicum.main.event.enums.State;
import ru.practicum.main.event.enums.StateSort;
import ru.practicum.main.event.models.Event;
import ru.practicum.main.event.models.EventShort;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final EntityManager entityManager;

    @Override
    public List<EventShort> findByParams(EventFilterDto filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EventShort> cq = cb.createQuery(EventShort.class);
        Root<Event> event = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(event.get(Event.PROP_STATE), State.PUBLISHED));

        if (checkForNullOrEmpty(filter.getPaid())) {
            predicates.add(cb.equal(event.get(Event.PROP_PAID), filter.getPaid()));
        }

        if (filter.isOnlyAvailable()) {
            predicates.add(cb.equal(event.get(Event.PROP_LIMIT), filter.isOnlyAvailable()));
        }

        if (checkForNullOrEmpty(filter.getCategories())) {
            CriteriaBuilder.In<Long> inCategories = cb.in(event.get(Event.PROP_CATEGORY));
            filter.getCategories().forEach(inCategories::value);
            predicates.add(inCategories);
        }

        LocalDateTime start = checkForNullOrEmpty(filter.getRangeStart()) ? filter.getRangeStart() : LocalDateTime.now();
        if (checkForNullOrEmpty(filter.getRangeStart()) && checkForNullOrEmpty(filter.getRangeEnd())) {
            predicates.add(cb.between(event.get(Event.PROP_DATE), start, filter.getRangeEnd()));
        } else {
            predicates.add(cb.greaterThanOrEqualTo(event.get(Event.PROP_DATE), start));
        }

        if (checkForNullOrEmpty(filter.getText())) {
            Predicate likeTitle = cb.like(cb.lower(event.get(Event.PROP_ANNOTATION)), "%" + filter.getText().toLowerCase() + "%");
            Predicate likeDescription = cb.like(cb.lower(event.get(Event.PROP_DESCRIPTION)), "%" + filter.getText().toLowerCase() + "%");
            predicates.add(cb.or(likeTitle, likeDescription));
        }
        cq.multiselect(event.get(Event.PROP_ID), event.get(Event.PROP_TITLE), event.get(Event.PROP_ANNOTATION),
                        event.get(Event.PROP_CATEGORY), event.get(Event.PROP_PAID), event.get(Event.PROP_DATE),
                        event.get(Event.PROP_INITIATOR), event.get(Event.PROP_PUBLISHED))
                .groupBy(event.get("id"));

        if (checkForNullOrEmpty(filter.getSort())) {
            if (filter.getSort().equals(StateSort.EVENT_DATE)) {
                cq.orderBy(cb.asc(event.get(Event.PROP_DATE)));
            }
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).setFirstResult(filter.getFrom()).setMaxResults(filter.getSize()).getResultList();
    }

    @Override
    public List<Event> findByParamAdmin(EventFilterAdminDto filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> event = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        if (checkForNullOrEmpty(filter.getUsers())) {
            CriteriaBuilder.In<Long> inUsers = cb.in(event.get(Event.PROP_INITIATOR));
            filter.getUsers().forEach(inUsers::value);
            predicates.add(inUsers);
        }

        if (checkForNullOrEmpty(filter.getStates())) {
            CriteriaBuilder.In<State> inStates = cb.in(event.get(Event.PROP_STATE));
            filter.getStates().forEach(inStates::value);
            predicates.add(inStates);
        }

        if (checkForNullOrEmpty(filter.getCategories())) {
            CriteriaBuilder.In<Long> inCategories = cb.in(event.get(Event.PROP_CATEGORY));
            filter.getCategories().forEach(inCategories::value);
            predicates.add(inCategories);
        }

        LocalDateTime start = checkForNullOrEmpty(filter.getRangeStart()) ? filter.getRangeStart() : LocalDateTime.now();
        if (checkForNullOrEmpty(filter.getRangeStart()) && checkForNullOrEmpty(filter.getRangeEnd())) {
            predicates.add(cb.between(event.get(Event.PROP_DATE), start, filter.getRangeEnd()));
        } else {
            predicates.add(cb.greaterThanOrEqualTo(event.get(Event.PROP_DATE), start));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(cq).setFirstResult(filter.getFrom()).setMaxResults(filter.getSize()).getResultList();
    }

    private boolean checkForNullOrEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else {
            return !obj.toString().isEmpty();
        }
    }
}
