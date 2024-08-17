package com.pdev.rempms.candidateservice.service;

import com.pdev.rempms.candidateservice.dto.candidate.CandidateSearchParamsDTO;
import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import org.springframework.data.domain.PageRequest;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface CommonProfileService {

    /**
     * save candidate common profile
     *
     * @param dto - common profile data
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    CommonResponse saveUpdate(CommonProfileRequestDTO dto);

    /**
     * save candidate common profile with jap and BiDirectional relationships
     *
     * @param dto - common profile data
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    CommonResponse saveUpdateByJPA(CommonProfileRequestDTO dto);

    /**
     * This method is allowed to get candidate profile by candidate id
     *
     * @param candidateId {@link Integer candidateId} - candidate id
     * @return - {@link CommonResponse} - fetched response.
     * @author @Maleesha99
     */
    CommonResponse getByCandidateId(Integer candidateId);

    /**
     * This method is allowed to search candidates
     *
     * @param candidateSearchParams {@link CandidateSearchParamsDTO} - candidate search parameters
     * @param pageRequest           {@link PageRequest} - PageRequest object with page and limit
     * @return {@link CommonResponse} - searched candidates
     * @author @Maleesha99
     */
    CommonResponse searchCandidates(CandidateSearchParamsDTO candidateSearchParams, PageRequest pageRequest);

}
