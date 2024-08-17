package com.pdev.rempms.adminservice.dto.jobCategory;

import lombok.Data;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@Data
public class JobCategoryDTO {
    private String idJobCategory;
    private String jobCategoryName;
    private String jobCategoryDescription;
    private String commonStatus;
}
