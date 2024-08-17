package com.pdev.rempms.adminservice.controller;

import com.pdev.rempms.adminservice.service.syncData.SyncDataService;
import com.pdev.rempms.adminservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maleeshasa
 * @Date 25/12/2023
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/sync-data")
public class SyncDataController {

    private final SyncDataService syncDataService;

    /**
     * sync role and user data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/syncUser")
    public ResponseEntity<CommonResponse> syncData() {
        return syncDataService.syncUserData();
    }

    /**
     * sync secret quation data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/syncSecQuation")
    public ResponseEntity<CommonResponse> syncSecQuation() {
        return syncDataService.syncSecQuation();
    }
}
