package com.pdev.rempms.recruitmentservice.controller.jobVacancy;

import com.pdev.rempms.recruitmentservice.dto.jobVacancy.JobVacancyRequest;
import com.pdev.rempms.recruitmentservice.service.jobVacancy.JobVacancyService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitment/v1/job-vacancy")
public class JobVacancyController {

    private final JobVacancyService jobVacancyService;

    @PostMapping(value = "/saveOrModify")
    public ResponseEntity<CommonResponse> saveOrModify(@RequestPart("jobVacancyDetails") JobVacancyRequest dto,
                                                       @RequestPart("document") MultipartFile file) {
        return ResponseEntity.ok(jobVacancyService.saveOrModify(dto, file));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(jobVacancyService.getById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(jobVacancyService.deleteById(id));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<CommonResponse> getAll() {
        return ResponseEntity.ok(jobVacancyService.getAll());
    }

}
