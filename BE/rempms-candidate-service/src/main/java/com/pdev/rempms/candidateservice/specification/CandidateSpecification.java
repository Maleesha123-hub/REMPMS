package com.pdev.rempms.candidateservice.specification;

import com.pdev.rempms.candidateservice.dto.candidate.CandidateSearchParamsDTO;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEducation;
import com.pdev.rempms.candidateservice.model.candidate.prefferedJobLocation.PreferredJobLocation;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import com.pdev.rempms.candidateservice.util.DateTimeUtil;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/04/11
 */
@Component
@Slf4j
public class CandidateSpecification {

    /**
     * This specification method is allowed to search candidates, represents a specification for filtering entities.
     * {This method is a utility for dynamically constructing JPA Criteria API predicates based on the properties provided
     * in the CandidateSearchParamsDTO. It allows for flexible querying of Candidate entities based on various criteria.}
     *
     * @param searchParams {@link CandidateSearchParamsDTO} - search params
     * @return {@link Specification<Candidate>} -  searched results
     * @author @Maleesha99
     */
    public Specification<Candidate> searchCandidates(CandidateSearchParamsDTO searchParams) {
        log.info("CandidateSpecification.searchCandidates() => started.");

        return ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Candidate no
            if (!CommonValidation.stringNullValidation(searchParams.getCandidateNo())) {
                predicates.add(criteriaBuilder.equal(root.get("candidateNo"), searchParams.getCandidateNo()));
            }

            // Notice period
            if (!CommonValidation.stringNullValidation(searchParams.getNoticePeriod())) {
                predicates.add(criteriaBuilder.equal(root.get("personalDetail").get("noticePeriod"), searchParams.getNoticePeriod()));
            }

            // Dob
            if (!CommonValidation.integerNullValidation(searchParams.getAgeGraterThan()) &&
                    !CommonValidation.integerNullValidation(searchParams.getAgeLessThan())) {
                // Get the today date
                LocalDate todayDate = DateTimeUtil.getSriLankaTodayDate();

                // Generate the birth date from and to by minus given age
                LocalDate birthDateFrom = todayDate.minusYears(searchParams.getAgeLessThan());
                LocalDate birthDateTo = todayDate.minusYears(searchParams.getAgeGraterThan());

                predicates.add(criteriaBuilder.between(root.get("personalDetail").get("dob"), birthDateFrom, birthDateTo));

            }

            // Preferred job location
            if (!CommonValidation.integerNullValidation(searchParams.getPreferredJobLocationId())) {
                Join<Candidate, PreferredJobLocation> preferredJobLocationJoin = root.join("preferredJobLocations");

                predicates.add(criteriaBuilder.equal(preferredJobLocationJoin.get("id"), searchParams.getPreferredJobLocationId()));

            }

            // Area of study
            if (!CommonValidation.integerNullValidation(searchParams.getAreaOfStudyId())) {
                Join<Candidate, HigherEducation> candidateHigherEducationJoin = root.join("higherEducationList");

                predicates.add(criteriaBuilder.equal(candidateHigherEducationJoin.get("areaOfStudy").get("id"), searchParams.getAreaOfStudyId()));

            }

            // Higher education qualification
            if (!CommonValidation.integerNullValidation(searchParams.getHigherEduQualificationId())) {
                Join<Candidate, HigherEduQualification> candidateHigherEduQualificationJoin = root.join("higherEduQualification");

                predicates.add(criteriaBuilder.equal(candidateHigherEduQualificationJoin.get("id"), searchParams.getHigherEduQualificationId()));

            }

            // Result
            if (!CommonValidation.stringNullValidation(searchParams.getResult())) {
                Join<Candidate, HigherEduQualification> candidateHigherEduQualificationJoin = root.join("higherEduQualification");

                predicates.add(criteriaBuilder.equal(candidateHigherEduQualificationJoin.get("result"), searchParams.getResult()));

            }

            // Combine all predicates using conjunctions
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });

    }

}
