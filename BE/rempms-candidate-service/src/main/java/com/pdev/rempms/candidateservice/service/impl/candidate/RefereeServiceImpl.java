package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.referee.RefereeMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.referee.Referee;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.RefereeRepository;
import com.pdev.rempms.candidateservice.service.candidate.RefereeService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/10
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RefereeServiceImpl implements RefereeService {

    private final RefereeRepository refereeRepository;
    private final CandidateRepository candidateRepository;
    private final RefereeMapper refereeMapper;
    private String message;

    /**
     * This method is allowed to save or update referees
     *
     * @param dto {@link CommonProfileRequestDTO} - including referees details
     * @return {@link CommonResponse} - referees saved response
     * @author @Maleesha99
     */
    @Override
    @Transactional
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("RefereeServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<Referee> refereeList = new ArrayList<>();

        try {
            message = "Referees saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getReferees().forEach(

                    refereeDTO -> {

//                        log.info("Mapping Referee...");
//                        Referee referee = refereeMapper.dtoToModel(refereeDTO);
//                        referee.setCandidate(candidate);
//                        refereeList.add(referee);
//                        log.info("Referee is mapped.");

                    }
            );

            log.info("Saving referees details...");
            refereeRepository.saveAll(refereeList);
            log.info("Referees details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Referees already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Referees details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("RefereeServiceImpl.saveUpdate() => ended.");
        return response;

    }

}
