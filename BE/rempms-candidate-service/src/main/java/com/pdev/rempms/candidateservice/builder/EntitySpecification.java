package com.pdev.rempms.candidateservice.builder;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author rifazaf
 * @date 2024/10/16
 */

@Component
public class EntitySpecification<T> {

    /**
     * Specification builder for filtering search criteria on different input
     *
     * @param searchCriteria required dto for specification builder
     * @return generic type specification class containing filtering data
     */
    public Specification<T> specificationBuilder(SearchCriteria searchCriteria) {
        if (Objects.nonNull(searchCriteria)) {
            List<SearchCriteria.Filter> filters = searchCriteria.getFilters();
            List<Specification<T>> specifications = filters.stream()
                    .map(this::createSpecification)
                    .collect(Collectors.toList());

            return Specification.allOf(specifications);
        }
        return null;
    }

    /**
     * Create specification different cases
     *
     * @param filter required data for create specification
     * @return generic type specification containing different case of operation data
     */
    private Specification<T> createSpecification(SearchCriteria.Filter filter) {
        switch (filter.getOperator()) {

            case EQUALS:
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(filter.getField()), filter.getValue());

            case BOOLEAN_EQUALS:
                return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(filter.getField()), filter.getBooleanValue());

            case EQUALS_IGNORE_CASE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get(filter.getField())), filter.getValue().toUpperCase());

            case NOT_EQUALS:
                return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(filter.getField()), filter.getValue());

            case GREATER_THAN:
                return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), filter.getValue());

            case GREATER_THAN_DATE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(filter.getField()), filter.getDateValue());

            case LESS_THAN:
                return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), filter.getValue());

            case LESS_THAN_DATE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(filter.getField()), filter.getDateValue());

            case LIKE:
                return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get(filter.getField())), "%" + filter.getValue().toUpperCase() + "%");
            default:
                return null;
        }
    }


}
