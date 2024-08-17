package com.pdev.rempms.candidateservice.service;

import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @maleeshasa
 * @Date 2024/03/07
 */
public interface SubjectService {

    CommonResponse getBySchoolEduQualification(String schoolEduQualification);

    CommonResponse getBySchoolEduQualificationAndScheme(String schoolEduQualification, Integer schemeId);

}
