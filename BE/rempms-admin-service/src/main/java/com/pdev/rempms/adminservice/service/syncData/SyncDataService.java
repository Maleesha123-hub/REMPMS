package com.pdev.rempms.adminservice.service.syncData;

import com.pdev.rempms.adminservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
public interface SyncDataService {

    /**
     * sync role and user data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> syncUserData();

    /**
     * sync secret quation data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> syncSecQuation();
}
