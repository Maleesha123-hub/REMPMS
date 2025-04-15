package com.pdev.rempms.draftservice.service.v2;

import com.pdev.rempms.draftservice.dto.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.draftservice.util.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
public interface CandidateCommonProfileDraftServiceV2 {

    /**
     * find by candidate id
     *
     * @param idCandidate
     * @return {@link CommonResponse} - draft by candidate id
     * @author @Maleesha99
     */
    CommonResponse findByIdCandidate(Integer idCandidate);

    /**
     * This method is allowed to save or modify candidate common profile
     *
     * @param commonProfileRequest {@link CommonProfileRequestDTO}
     * @param documentList         {@link MultipartFile[]}
     * @return {@link CommonResponse} - draft saved response
     * @author @Maleesha99
     */
    CommonResponse createOrModify(CommonProfileRequestDTO commonProfileRequest, MultipartFile[] documentList);

}
