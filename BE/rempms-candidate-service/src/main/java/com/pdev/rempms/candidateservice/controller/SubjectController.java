package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.SubjectService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author @maleeshasa
 * @Date 2024/03/32
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/candidate/v1/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping(value = "/getBySchoolEduQualification")
    public ResponseEntity<CommonResponse> getBySchoolEduQualification(@RequestParam(name = "schoolEduQualification") String schoolEduQualification) {

        return ResponseEntity.ok(subjectService.getBySchoolEduQualification(schoolEduQualification));

    }

    @GetMapping(value = "/getBySchoolEduQualificationAndScheme")
    public ResponseEntity<CommonResponse> getBySchoolEduQualificationAndScheme(@RequestParam(name = "schoolEduQualification") String schoolEduQualification,
                                                                               @RequestParam(name = "schemeId") Integer schemeId) {

        return ResponseEntity.ok(subjectService.getBySchoolEduQualificationAndScheme(schoolEduQualification, schemeId));

    }

}
