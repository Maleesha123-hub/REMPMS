package com.pdev.rempms.recruitmentservice.listeners;

import com.pdev.rempms.recruitmentservice.builder.UniqueNumberBuilder;
import com.pdev.rempms.recruitmentservice.constants.enums.ReferenceNo;
import com.pdev.rempms.recruitmentservice.helper.AutowiredHelper;
import com.pdev.rempms.recruitmentservice.model.jobVacancy.JobVacancy;
import com.pdev.rempms.recruitmentservice.service.jobVacancy.JobVacancyService;
import com.pdev.rempms.recruitmentservice.util.CommonValidation;
import jakarta.persistence.PrePersist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class VacancyEntityListener {

    private static final Logger logger = LoggerFactory.getLogger(VacancyEntityListener.class);

    @Autowired
    private JobVacancyService jobVacancyService;

    @Autowired
    private UniqueNumberBuilder uniqueNumberBuilder;

    @PrePersist
    private void beforeCreate(JobVacancy jobVacancy) {
        logger.info("VacancyEntityListener.beforeCreate() => accessed.");
        AutowiredHelper.autowire(this, jobVacancyService);
        setJobVacancyRefNo(jobVacancy);
    }

    private void setJobVacancyRefNo(JobVacancy jobVacancy) {
        logger.info("VacancyEntityListener.setJobVacancyRefNo => accessed.");
        try {
            if (CommonValidation.stringNullValidation(jobVacancy.getRefNo())) {
                logger.warn("setJobVacancyRefNo -> before persist ref no not found");
                jobVacancy.setRefNo(uniqueNumberBuilder.generateReferenceNumber(ReferenceNo.JOB_VACANCY_REF));

            } else {
                logger.info("setJobVacancyRefNo -> before persist ref no already exists.");
            }

        } catch (Exception e) {
            logger.error("VacancyEntityListener => setJobVacancyRefNo() Error while create ref no");
            logger.error(e.getMessage());
        }
    }

}
