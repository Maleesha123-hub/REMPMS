package com.pdev.rempms.recruitmentservice.controller.vacancyHasCandidates;

import com.pdev.rempms.recruitmentservice.dto.vacancyHasCandidates.VacancyHasCandidatesRequest;
import com.pdev.rempms.recruitmentservice.service.vacancyHasCandidates.VacancyHasCandidatesService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitment/v1/vacancy-has-candidates")
public class VacancyHasCandidatesController {

    private final VacancyHasCandidatesService vacancyHasCandidatesService;

    @PostMapping(value = "/saveOrModify")
    public ResponseEntity<CommonResponse> saveOrModify(@RequestPart(value = "vacancyHasCandidateDetails") VacancyHasCandidatesRequest dto,
                                                       @RequestPart(value = "cvDocument", required = false) MultipartFile[] file) {

        return ResponseEntity.ok(vacancyHasCandidatesService.saveOrModify(dto, file));
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<CommonResponse> getById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(vacancyHasCandidatesService.getById(id));
    }

}
