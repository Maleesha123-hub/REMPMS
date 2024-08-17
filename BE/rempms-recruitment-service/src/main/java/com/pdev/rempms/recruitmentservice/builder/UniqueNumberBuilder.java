package com.pdev.rempms.recruitmentservice.builder;

import com.pdev.rempms.recruitmentservice.constants.enums.ReferenceNo;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import com.pdev.rempms.recruitmentservice.repository.employer.EmployerRepository;
import com.pdev.rempms.recruitmentservice.repository.jobVacancy.JobVacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UniqueNumberBuilder {

    private final EmployerRepository employerRepository;
    private final JobVacancyRepository jobVacancyRepository;

    public String generateReferenceNumber(ReferenceNo refNo) {
        log.info("UniqueNumberBuilder.generateReferenceNumber >> started.");

        StringBuilder nextNoStr = new StringBuilder(refNo.getName());
        Optional<Employer> employer;
        Optional<JobVacancy> jobVacancy;

        switch (refNo) {
            case EMPLOYER_REF: {
                employer = employerRepository.findTopByOrderByIdDesc();
                if (employer.isEmpty()) {
                    log.info("Employer is empty.");
                    nextNoStr.append("000001");

                } else {
                    log.info("Employer is available for top order desc.");
                    Integer currentNo = employer.get().getId();
                    nextNoStr.append(String.format("%06d", currentNo));
                }
            }
            break;
            case JOB_VACANCY_REF: {
                jobVacancy = jobVacancyRepository.findTopByOrderByIdDesc();
                if (jobVacancy.isEmpty()) {
                    log.info("Job vacancy is empty.");
                    nextNoStr.append("000001");

                } else {
                    log.info("Job vacancy is available for top order desc.");
                    Integer currentNo = jobVacancy.get().getId();
                    nextNoStr.append(String.format("%06d", currentNo));
                }
            }
            break;
            default: {
                throw new BaseException(500, "Invalid Reference Type.");
            }
        }

        log.info("UniqueNumberBuilder.generateReferenceNumber >> ended.");
        return nextNoStr.toString();
    }
}
