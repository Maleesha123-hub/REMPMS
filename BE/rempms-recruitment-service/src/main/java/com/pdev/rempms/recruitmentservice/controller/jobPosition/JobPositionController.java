package com.pdev.rempms.recruitmentservice.controller.jobPosition;

import com.pdev.rempms.recruitmentservice.dto.jobPosition.JobPositionRequestDTO;
import com.pdev.rempms.recruitmentservice.service.jobPosition.JobPositionService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment/v1/job-position")
@RequiredArgsConstructor
public class JobPositionController {

    private final JobPositionService jobPositionService;

    @PostMapping(value = "/saveOrModify")
    public ResponseEntity<CommonResponse> saveOrModify(@RequestBody JobPositionRequestDTO jobPositionRequest){
        return ResponseEntity.ok(jobPositionService.saveOrModify(jobPositionRequest));
    }

    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable Integer id){
        return ResponseEntity.ok(jobPositionService.getById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Integer id){
        return ResponseEntity.ok(jobPositionService.deleteById(id));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<CommonResponse> getAll() {
        return ResponseEntity.ok(jobPositionService.getAll());
    }

}
