package com.pdev.rempms.candidateservice.dto.jobCategory;

import lombok.Getter;
import lombok.Setter;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Getter
@Setter
public class JobCategoryDTO {

    private Integer idJobCategory;

    private String jobCategoryName;

    private String jobCategoryDescription;

    private String commonStatus;

}
