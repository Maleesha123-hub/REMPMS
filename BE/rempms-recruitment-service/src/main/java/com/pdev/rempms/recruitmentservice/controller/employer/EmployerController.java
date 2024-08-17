package com.pdev.rempms.recruitmentservice.controller.employer;

import com.pdev.rempms.recruitmentservice.dto.employer.EmployerDTO;
import com.pdev.rempms.recruitmentservice.service.employer.EmployerService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruitment/v1/employer")
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping(value = "/saveOrModify")
    public ResponseEntity<CommonResponse> save(@RequestBody EmployerDTO dto) {
        log.info("EmployerController.save() => started.");
        return ResponseEntity.ok(employerService.save(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable Integer id) {
        log.info("EmployerController.getById() => started.");
        return ResponseEntity.ok(employerService.getById(id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<CommonResponse> deleteById(@PathVariable Integer id) {
        log.info("EmployerController.deleteById() => started.");
        return ResponseEntity.ok(employerService.deleteById(id));
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<CommonResponse> getAll() {
        log.info("EmployerController.getAll() => started.");
        return ResponseEntity.ok(employerService.getAll());
    }

}
