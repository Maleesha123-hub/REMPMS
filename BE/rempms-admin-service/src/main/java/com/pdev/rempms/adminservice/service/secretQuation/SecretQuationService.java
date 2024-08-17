package com.pdev.rempms.adminservice.service.secretQuation;

import com.pdev.rempms.adminservice.dto.secretQuation.SecretQuationDTO;
import com.pdev.rempms.adminservice.util.CommonResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author maleeshasa
 * @Date 29/12/2023
 */
public interface SecretQuationService {

    /**
     * save or update secret quation
     *
     * @param dto - secret quation saving or updating details
     * @return - save or update success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> saveUpdate(SecretQuationDTO dto);

    /**
     * Get active secret quetion by id
     *
     * @param idSecretQuetion - secret quetion id
     * @return - Active secret quetion data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getActiveById(Long idSecretQuetion);

    /**
     * Get all active secret quations
     *
     * @return - All Active secret quations data.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> getAllActive();

    /**
     * Delete secret quation by secret quation id
     *
     * @param idSecretQuation - secret quation id
     * @return - secret quation deleted success info.
     * @author maleeshasa
     */
    ResponseEntity<CommonResponse> deleteById(Long idSecretQuation);
}
