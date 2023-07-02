package ru.practicum.service.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.dto.StatDto;
import ru.practicum.dto.UserRequestDto;
import ru.practicum.service.models.Stat;
import ru.practicum.service.models.View;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomStatsRepositoryImpl implements CustomStatsRepository {
    private final EntityManager entityManager;

    @Override
    public List<StatDto> findByParams(UserRequestDto request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StatDto> cq = cb.createQuery(StatDto.class);
        Root<Stat> root = cq.from(Stat.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.greaterThanOrEqualTo(root.get(Stat.PROP_TIMESTAMP), request.getStart()));
        predicates.add(cb.lessThanOrEqualTo(root.get(Stat.PROP_TIMESTAMP), request.getEnd()));

        if (request.getUris() != null && !request.getUris().isEmpty()) {
            CriteriaBuilder.In<String> inUri = cb.in(root.get(Stat.PROP_URI));
            request.getUris().forEach(inUri::value);
            predicates.add(inUri);
        }

        if (request.isUnique()) {
            cq.multiselect(root.get(Stat.PROP_APP), root.get(Stat.PROP_URI), cb.countDistinct(root.get(Stat.PROP_IP)));
        } else {
            cq.multiselect(root.get(Stat.PROP_APP), root.get(Stat.PROP_URI), cb.count(root.get(Stat.PROP_IP)));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])))
                .groupBy(root.get(Stat.PROP_APP), root.get(Stat.PROP_URI), root.get(Stat.PROP_IP))
                .orderBy(cb.desc(cb.literal(2)));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<View> getViews(List<String> ids) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<View> cq = cb.createQuery(View.class);
        Root<Stat> stat = cq.from(Stat.class);

        cq.multiselect(stat.get("uri"), cb.countDistinct(stat.get("ip")))
                .where(stat.get("uri").in(ids.stream().map(uri -> "/events/" + uri).collect(Collectors.toList())))
                .groupBy(stat.get("uri"));

        return entityManager.createQuery(cq).getResultList();
    }
}
