package ru.shift.baldezh.licenses.service.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

public class EntitySpecification<T> implements Specification<T> {
    private final Map<String, Object> fieldMap;

    public EntitySpecification(Map<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate[] predicates = fieldMap.entrySet()
                .stream()
                .filter(e -> e.getValue() != null)
                .map(e -> criteriaBuilder.equal(root.get(e.getKey()), e.getValue()))
                .toArray(Predicate[]::new);


        return criteriaBuilder.and(predicates);
    }
}
