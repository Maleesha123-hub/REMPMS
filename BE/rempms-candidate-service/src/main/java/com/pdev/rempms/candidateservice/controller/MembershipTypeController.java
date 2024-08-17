package com.pdev.rempms.candidateservice.controller;

import com.pdev.rempms.candidateservice.service.MembershipTypeService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/candidate/v1/membership-type")
public class MembershipTypeController {

    private final MembershipTypeService membershipTypeService;

    @GetMapping(value = "/getAllActive")
    public ResponseEntity<CommonResponse> getAllActive() {

        return ResponseEntity.ok(membershipTypeService.getAllActive());

    }

}
