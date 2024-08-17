package com.pdev.rempms.recruitmentservice.service.impl.employer;

import com.pdev.rempms.recruitmentservice.builder.UniqueNumberBuilder;
import com.pdev.rempms.recruitmentservice.constants.enums.ReferenceNo;
import com.pdev.rempms.recruitmentservice.dto.employer.EmployerDTO;
import com.pdev.rempms.recruitmentservice.dto.employer.EmployerSavedLazyResponseDTO;
import com.pdev.rempms.recruitmentservice.exception.BaseException;
import com.pdev.rempms.recruitmentservice.exception.RecordNotFoundException;
import com.pdev.rempms.recruitmentservice.mapper.employer.EmployerMapper;
import com.pdev.rempms.recruitmentservice.model.employer.Employer;
import com.pdev.rempms.recruitmentservice.repository.employer.EmployerRepository;
import com.pdev.rempms.recruitmentservice.service.employer.EmployerService;
import com.pdev.rempms.recruitmentservice.util.CommonResponse;
import com.pdev.rempms.recruitmentservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;
    private final UniqueNumberBuilder uniqueNumberBuilder;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse save(EmployerDTO dto) {

        Employer employer = new Employer();

        if (!CommonValidation.integerNullValidation(dto.getId())) {

            if (CommonValidation.stringNullValidation(dto.getEmployerNo())) {
                throw new RecordNotFoundException("As an existing employer, should contain employer number.");
            }

            employer = employerRepository.findById(dto.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Employer is not exists."));
        }

        if (CommonValidation.integerNullValidation(dto.getId()) &&
                CommonValidation.stringNullValidation(dto.getEmployerNo())) {
            employer.setEmployerNo(uniqueNumberBuilder.generateReferenceNumber(ReferenceNo.EMPLOYER_REF));
        }

        try {
            Employer savedEmployer = employerRepository.save(employerMapper.toEntity(employer, dto));

            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Employer saved success.");
            commonResponse.setData(new EmployerSavedLazyResponseDTO(savedEmployer.getId(), savedEmployer.getEmployerName(),
                    savedEmployer.getAddress(), savedEmployer.getTelephoneNo(), savedEmployer.getMobileNo(),
                    savedEmployer.isActive()));
            return commonResponse;

        } catch (Exception e) {
            throw new BaseException(500, "Exception occurred while saving employer. " + e.getMessage());
        }

    }

    @Override
    public CommonResponse getById(Integer id) {

        CommonResponse commonResponse = new CommonResponse();
        Optional<Employer> employer = employerRepository.findById(id);

        if (employer.isPresent()) {
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Employer is exists.");
            commonResponse.setData(employerMapper.toDto(new EmployerDTO(), employer.get()));

        } else {
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Employer is not exists.");
            commonResponse.setData(null);

        }
        return commonResponse;

    }

    @Override
    public CommonResponse deleteById(Integer id) {

        Employer employer;

        employer = employerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Employer is not exists."));

        if (!employer.getJobVacancies().isEmpty()){
            throw new BaseException(500, "Employer can not delete. It has job vacancies.");
        }

        try {
            employer.setActive(Boolean.FALSE);
            employerRepository.save(employer);
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Employer deleted success.");
            commonResponse.setData(null);
            return commonResponse;

        } catch (Exception e) {
            throw new BaseException(500, "Exception occurred while deleting employer. " + e.getMessage());
        }

    }

    @Override
    public CommonResponse getAll() {

        CommonResponse commonResponse = new CommonResponse();

        List<EmployerDTO> employerList = employerRepository.findByActive(Boolean.TRUE)
                .stream()
                .map(employer -> employerMapper.toDto(new EmployerDTO(), employer))
                .toList();

        if (employerList.isEmpty()) {
            commonResponse.setStatus(HttpStatus.NO_CONTENT);
            commonResponse.setMessage("Employees not exists.");
            commonResponse.setData(null);

        } else {
            commonResponse.setData(employerList);
            commonResponse.setMessage("Employers are exists.");
            commonResponse.setStatus(HttpStatus.OK);

        }
        return commonResponse;

    }

}
