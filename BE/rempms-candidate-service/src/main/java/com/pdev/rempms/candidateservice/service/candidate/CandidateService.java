package com.pdev.rempms.candidateservice.service.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.CandidateDTO;
import com.pdev.rempms.candidateservice.util.CommonResponse;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface CandidateService {

    /**
     * save candidate
     *
     * @param dto - candidate data
     * @return - save success info.
     * @author maleeshasa
     */
    CommonResponse saveCandidate(CandidateDTO dto);

    /**
     * This method is allowed to get candidate details by id
     *
     * @param idCandidate {@link Integer} - candidate id
     * @return - {@link CommonResponse} - candidate response.
     * @author @Maleesha99
     */
    CommonResponse getById(Integer idCandidate);

}
