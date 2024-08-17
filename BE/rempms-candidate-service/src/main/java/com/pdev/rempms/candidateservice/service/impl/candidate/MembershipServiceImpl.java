package com.pdev.rempms.candidateservice.service.impl.candidate;

import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.member.MemberMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.member.Membership;
import com.pdev.rempms.candidateservice.model.candidate.member.MembershipType;
import com.pdev.rempms.candidateservice.repository.MembershipTypeRepository;
import com.pdev.rempms.candidateservice.repository.candidate.CandidateRepository;
import com.pdev.rempms.candidateservice.repository.candidate.MembershipRepository;
import com.pdev.rempms.candidateservice.service.candidate.MembershipService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/05
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipServiceImpl implements MembershipService {

    private final CandidateRepository candidateRepository;
    private final RestLocationInfoClientService restLocationInfoClientService;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MemberMapper memberMapper;
    private final MembershipRepository membershipRepository;
    private String message;

    /**
     * This method is allowed to save membership details
     *
     * @param dto {@link CommonProfileRequestDTO} - including membership details
     * @return {@link CommonResponse} - membership saved response
     * @author @Maleesha99
     */
    @Override
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {
        log.info("MembershipServiceImpl.saveUpdate() => started.");

        CommonResponse response = new CommonResponse();
        List<Membership> membershipList = new ArrayList<>();

        try {
            message = "Membership saved success.";

            log.info("Fetching candidate by id...");
            Candidate candidate = candidateRepository.findById(Integer.valueOf(dto.getIdCandidate()))
                    .orElseThrow(() -> new RecordNotFoundException("Candidate not exist!"));
            log.info("Candidate is fetched by id.");

            dto.getMemberships().forEach(

                    membership -> {

                        log.info("Get country by id from location service...");
                        CountryDTO country = restLocationInfoClientService.getById(membership.getIdCountry());
                        log.info("Country is fetched by id.");

                        log.info("Fetching membership type by id...");
                        MembershipType membershipType = membershipTypeRepository.findById(membership.getIdMembershipType())
                                .orElseThrow(() -> new RecordNotFoundException("Membership type not exists."));
                        log.info("Fetched membership type.");

                        membershipList.add(memberMapper.toEntity(new Membership(), membership, country, membershipType, candidate));

                    }
            );

            log.info("Saving membership details...");
            membershipRepository.saveAll(membershipList);
            log.info("Membership details are saved success.");

            response.setStatus(HttpStatus.CREATED);
            response.setMessage(message);

        } catch (ObjectOptimisticLockingFailureException e) {
            message = "Membership already updated.";

            response.setData(null);
            response.setStatus(HttpStatus.NOT_MODIFIED);
            response.setMessage(message);

        } catch (Exception e) {
            message = "Membership details created failed.";

            response.setData(null);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(message);

        }

        log.info("MembershipServiceImpl.saveUpdate() => ended.");
        return response;

    }

}
