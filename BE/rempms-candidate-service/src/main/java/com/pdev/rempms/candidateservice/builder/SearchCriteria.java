package com.pdev.rempms.candidateservice.builder;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rifazaf
 * @date 2024/10/16
 */

@Getter
@Builder(toBuilder = true)
public class SearchCriteria {

    private List<Filter> filters;

    @Getter
    @Builder(toBuilder = true)
    public static class Filter {
        // Name of the operation we like to perform
        public enum QueryOperator {
            EQUALS, NOT_EQUALS, LIKE, LESS_THAN, LESS_THAN_DATE, GREATER_THAN, GREATER_THAN_DATE, EQUALS_IGNORE_CASE, BOOLEAN_EQUALS
        }

        private String field; // Name of the filed from entity like firstName
        private QueryOperator operator; // Operator we like to apply
        private String value; // value we would like to match
        private Boolean booleanValue;
        private Integer id; // value we would like to match
        private LocalDateTime dateValue; // Date value we would like to match
    }
}