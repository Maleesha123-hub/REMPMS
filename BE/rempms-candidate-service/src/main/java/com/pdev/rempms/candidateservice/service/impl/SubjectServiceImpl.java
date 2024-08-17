package com.pdev.rempms.candidateservice.service.impl;

import com.pdev.rempms.candidateservice.dto.subject.SubjectDTO;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.subject.SubjectMapper;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Scheme;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.Subject;
import com.pdev.rempms.candidateservice.repository.SchemeRepository;
import com.pdev.rempms.candidateservice.repository.SubjectRepository;
import com.pdev.rempms.candidateservice.service.SubjectService;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SchemeRepository schemeRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public CommonResponse getBySchoolEduQualification(String schoolEduQualification) {

        CommonResponse response = new CommonResponse();
        List<Subject> subjects = subjectRepository.findBySchoolEduQualification(schoolEduQualification);

        List<SubjectDTO> subjectDTOS = subjects.stream()
                .map(subject -> subjectMapper.toDto(new SubjectDTO(), subject)).toList();

        if (!subjectDTOS.isEmpty()) {

            response.setData(subjectDTOS);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Subjects are exists.");

        } else {

            response.setData(null);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Subjects are not exists.");

        }

        return response;

    }

    @Override
    public CommonResponse getBySchoolEduQualificationAndScheme(String schoolEduQualification, Integer schemeId) {

        CommonResponse response = new CommonResponse();

        Scheme scheme = schemeRepository.findById(schemeId)
                .orElseThrow(() -> new RecordNotFoundException("Scheme not exists for selected scheme."));

        List<Subject> subjects = subjectRepository.findBySchemeAndSchoolEduQualification(scheme, schoolEduQualification);

        List<SubjectDTO> subjectDTOS = subjects.stream()
                .map(subject -> subjectMapper.toDto(new SubjectDTO(), subject)).toList();

        if (!subjectDTOS.isEmpty()) {

            response.setData(subjectDTOS);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Subjects are exists.");

        } else {

            response.setData(null);
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Subjects are not exists.");

        }

        return response;

    }

}
