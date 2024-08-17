package com.pdev.rempms.adminservice.proxyClient;

import com.pdev.rempms.adminservice.dto.secretQuation.SecretQuationDTO;
import com.pdev.rempms.adminservice.dto.syncData.SyncDataRequestDTO;
import com.pdev.rempms.adminservice.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author maleeshasa
 * @Date 26/12/2023
 */
@FeignClient(name = "rempms-user-service")
@Headers("Content-Type: application/json")
public interface UserServiceClient {

    /**
     * sync role and user data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/api/v1/user/sync-data/syncUser")
    CommonResponse syncUserData(@RequestBody SyncDataRequestDTO dto);

    /**
     * sync secret quation data
     *
     * @return - save or update success info.
     * @author maleeshasa
     */
    @PostMapping(value = "/api/v1/user/sync-data/syncSecQuation")
    CommonResponse syncSecQuationData(@RequestBody List<SecretQuationDTO> dtoList);
}
