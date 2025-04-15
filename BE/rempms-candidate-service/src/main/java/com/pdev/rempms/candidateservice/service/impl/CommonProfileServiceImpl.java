package com.pdev.rempms.candidateservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.rempms.candidateservice.controller.response.PageResponse;
import com.pdev.rempms.candidateservice.dto.candidate.CandidateSaveLazyResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.CandidateSearchLazyResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.CandidateSearchParamsDTO;
import com.pdev.rempms.candidateservice.dto.candidate.achievments.AchievementDTO;
import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileRequestDTO;
import com.pdev.rempms.candidateservice.dto.candidate.commonProfile.CommonProfileResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.document.DocumentDTO;
import com.pdev.rempms.candidateservice.dto.candidate.familyInformation.FamilyInformationDTO;
import com.pdev.rempms.candidateservice.dto.candidate.higherEducation.HigherEducationResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.jobPreference.JobPreferenceResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.languageProficiency.LanguageProficiencyResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.membership.MembershipResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.personalDetail.PersonalDetailResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.preferredJobLocation.PreferredJobLocationResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.professionalExperience.ProfessionalExperienceResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.referee.RefereeDTO;
import com.pdev.rempms.candidateservice.dto.candidate.research.ResearchResponseDTO;
import com.pdev.rempms.candidateservice.dto.candidate.schoolEducation.SchoolEducationResponseDTO;
import com.pdev.rempms.candidateservice.dto.communication.CommunicationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.communication.language.LanguageDTO;
import com.pdev.rempms.candidateservice.dto.document.upload.DocumentUploadResponseDTO;
import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
import com.pdev.rempms.candidateservice.enums.FolderType;
import com.pdev.rempms.candidateservice.exception.BaseException;
import com.pdev.rempms.candidateservice.exception.RecordNotFoundException;
import com.pdev.rempms.candidateservice.mapper.candidate.CandidateMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.achievement.AchievementMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.document.DocumentMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.familyInformation.FamilyInformationMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.higherEducation.HigherEducationMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.jobPreference.JobPreferenceMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.languageProfficiency.LanguageProficiencyMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.member.MemberMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.personalDetail.PersonalDetailMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.prefferedJobLocation.PreferredJobLocationMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.proffesionalExperience.ProfessionalExperienceMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.referee.RefereeMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.research.ResearchMapper;
import com.pdev.rempms.candidateservice.mapper.candidate.schoolEducation.SchoolEducationMapper;
import com.pdev.rempms.candidateservice.model.candidate.Candidate;
import com.pdev.rempms.candidateservice.model.candidate.achievement.Achievement;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.Document;
import com.pdev.rempms.candidateservice.model.candidate.cvOrCertificate.DocumentType;
import com.pdev.rempms.candidateservice.model.candidate.familyInformation.FamilyInformation;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.AreaOfStudy;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEduQualification;
import com.pdev.rempms.candidateservice.model.candidate.higherEducation.HigherEducation;
import com.pdev.rempms.candidateservice.model.candidate.jobPreference.JobPreference;
import com.pdev.rempms.candidateservice.model.candidate.languageProfficiency.LanguageProficiency;
import com.pdev.rempms.candidateservice.model.candidate.member.Membership;
import com.pdev.rempms.candidateservice.model.candidate.member.MembershipType;
import com.pdev.rempms.candidateservice.model.candidate.personalDetail.PersonalDetail;
import com.pdev.rempms.candidateservice.model.candidate.prefferedJobLocation.PreferredJobLocation;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.Industry;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.JobCategory;
import com.pdev.rempms.candidateservice.model.candidate.proffesionalExperience.ProfessionalExperience;
import com.pdev.rempms.candidateservice.model.candidate.referee.Referee;
import com.pdev.rempms.candidateservice.model.candidate.research.Research;
import com.pdev.rempms.candidateservice.model.candidate.schoolEducation.SchoolEducation;
import com.pdev.rempms.candidateservice.repository.*;
import com.pdev.rempms.candidateservice.repository.candidate.*;
import com.pdev.rempms.candidateservice.service.CommonProfileService;
import com.pdev.rempms.candidateservice.service.rest.RestCommunicationInfoClientService;
import com.pdev.rempms.candidateservice.service.rest.RestDocumentClientService;
import com.pdev.rempms.candidateservice.service.rest.RestDraftClientService;
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.specification.CandidateSpecification;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import com.pdev.rempms.candidateservice.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * @author @Maleesha99
 * @Date 2024/02/01
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CommonProfileServiceImpl implements CommonProfileService {

    private final RestLocationInfoClientService restLocationInfoClientService;
    private final RestCommunicationInfoClientService restCommunicationInfoClientService;
    private final RestDraftClientService restDraftClientService;
    private final RestDocumentClientService restDocumentClientService;

    private final PersonalDetailMapper personalDetailMapper;
    private final ProfessionalExperienceMapper professionalExperienceMapper;
    private final HigherEducationMapper higherEducationMapper;
    private final SchoolEducationMapper schoolEducationMapper;
    private final MemberMapper memberMapper;
    private final LanguageProficiencyMapper languageProficiencyMapper;
    private final ResearchMapper researchMapper;
    private final AchievementMapper achievementMapper;
    private final RefereeMapper refereeMapper;
    private final FamilyInformationMapper familyInformationMapper;
    private final JobPreferenceMapper jobPreferenceMapper;
    private final PreferredJobLocationMapper preferredJobLocationMapper;
    private final DocumentMapper documentMapper;
    private final CandidateMapper candidateMapper;
    private final ObjectMapper objectMapper;

    private final CandidateRepository candidateRepository;
    private final ProfessionalExperienceRepository professionalExperienceRepository;
    private final HigherEducationRepository higherEducationRepository;
    private final SchoolEducationRepository schoolEducationRepository;
    private final MembershipRepository membershipRepository;
    private final LanguageProficiencyRepository languageProficiencyRepository;
    private final ResearchRepository researchRepository;
    private final RefereeRepository refereeRepository;
    private final FamilyInformationRepository familyInformationRepository;
    private final JobPreferenceRepository jobPreferenceRepository;
    private final PreferredJobLocationRepository preferredJobLocationRepository;
    private final DocumentRepository documentRepository;
    private final IndustryRepository industryRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final HigherEduQualificationRepository higherEduQualificationRepository;
    private final AreaOfStudyRepository areaOfStudyRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final CandidateSpecification candidateSpecification;

    /**
     * save candidate common profile
     *
     * @param dto - common profile data
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveUpdate(CommonProfileRequestDTO dto) {

        /*log.info("CommonProfileServiceImpl -> saveUpdate() => started!");

        // Validate candidate profile request data
        candidateProfileValidation.validateCandidateCommonProfile(dto);

        // Find candidate by id
        Candidate candidate = candidateRepository.findById(dto.getIdCandidate())
                .orElseThrow(() -> new RecordNotFoundException("Candidate not exists."));

        // Location information save
        LocationInformationRequestDTO location = restLocationInfoClientService.saveLocationInfo(dto.getPersonalDetail());
        Integer idLocation = location.getIdLocationInformation();

        // Communication information save
        CommunicationInformationRequestDTO communication = restCommunicationInfoClientService.saveCommunicationInfo(dto.getPersonalDetail());
        Integer idCommunication = communication.getIdCommunicationInformation();

        // Personal details
        PersonalDetail personalDetail = CommonValidation.integerNullValidation(dto.getPersonalDetail().getIdPersonalDetail()) ? new PersonalDetail() :
                personalDetailRepository.findById(dto.getPersonalDetail().getIdPersonalDetail()).
                        orElseThrow(() -> new RecordNotFoundException("Personal details not exists."));
        personalDetailMapper.toEntity(personalDetail, idCommunication, idLocation, dto.getPersonalDetail());

        // Professional experiences
        List<ProfessionalExperience> professionalExperienceList = dto.getProfessionalExperiences().stream()
                .map(pfx -> {
                    Industry industry = industryRepository.findById(pfx.getIdIndustry())
                            .orElseThrow(() -> new RecordNotFoundException("Industry not found!"));

                    JobCategory jobCategory = jobCategoryRepository.findById(pfx.getIdJobCategory())
                            .orElseThrow(() -> new RecordNotFoundException("Job category not found!"));

                    return professionalExperienceMapper.toEntity(new ProfessionalExperience(), pfx, industry, jobCategory, candidate);

                }).toList();

        //Higher educations
        List<HigherEducation> higherEducationList = dto.getHigherEducations().stream()
                .map(higherEducation -> {

                    HigherEduQualification higherEduQualification = higherEduQualificationRepository.findById(higherEducation.getIdHigherEduQualification())
                            .orElseThrow(() -> new RecordNotFoundException("Higher education qualification not found!"));

                    AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(higherEducation.getIdAreaOfStudy())
                            .orElseThrow(() -> new RecordNotFoundException("Area of study not found!"));

                    CountryDTO country = restLocationInfoClientService.getById(higherEducation.getIdCountry());
                    LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(higherEducation.getIdLanguage());

                    return higherEducationMapper.toEntity(new HigherEducation(), higherEducation, higherEduQualification,
                            areaOfStudy, country, language, candidate);

                }).toList();

        // School educations
        List<SchoolEducation> schoolEducations = dto.getSchoolEducations().stream()
                .map(schoolEducation -> {
                    CountryDTO country = restLocationInfoClientService.getById(schoolEducation.getIdCountry());
                    LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(schoolEducation.getIdLanguage());

                    return schoolEducationMapper.toEntity(new SchoolEducation(), schoolEducation, country, language, candidate);

                }).toList();

        // Memberships
        List<Membership> membershipList = dto.getMemberships().stream()
                .map(membership -> {
                    CountryDTO country = restLocationInfoClientService.getById(membership.getIdCountry());

                    MembershipType membershipType = membershipTypeRepository.findById(membership.getIdMembershipType())
                            .orElseThrow(() -> new RecordNotFoundException("Membership type not exists."));

                    return memberMapper.toEntity(new Membership(), membership, country, membershipType, candidate);

                }).toList();

        // Language proficiencies
        List<LanguageProficiency> languageProficiencyList = dto.getLanguageProficiencies().stream()
                .map(languageProficiency -> {
                    LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(languageProficiency.getIdLanguage());

                    return languageProficiencyMapper.dtoToModel(new LanguageProficiency(), languageProficiency, language, candidate);

                }).toList();

        // Researches
        List<Research> researchList = dto.getResearches().stream()
                .map(research -> researchMapper.dtoToModel(new Research(), research, candidate)).toList();

        // Achievements
        Achievement achievement = CommonValidation.integerNullValidation(dto.getAchievements().getIdAchievement()) ? new Achievement() :
                achievementsRepository.findById(dto.getAchievements().getIdAchievement()).
                        orElseThrow(() -> new RecordNotFoundException("Achievements not exists."));
        achievementMapper.dtoToModel(achievement, dto.getAchievements(), candidate);

        // Referees
        List<Referee> refereeList = dto.getReferees().stream()
                .map(referee -> refereeMapper.dtoToModel(new Referee(), referee, candidate)).toList();

        // Family information
        List<FamilyInformation> familyInformationList = dto.getFamilyInformation().stream()
                .map(familyInformation -> familyInformationMapper.dtoToModel(new FamilyInformation(), familyInformation, candidate)).toList();

        // Job preferences
        List<JobPreference> jobPreferenceList = dto.getJobPreferences().stream()
                .map(jobPreference -> jobPreferenceMapper.dtoToModel(new JobPreference(), jobPreference, candidate)).toList();

        // Preferred job location
        List<PreferredJobLocation> preferredJobLocations = dto.getPreferredJobLocations()
                .stream()
                .map(pjl -> {
                    CountryDTO country = restLocationInfoClientService.getById(pjl.getIdCountry());
                    return preferredJobLocationMapper.dtoToModel(new PreferredJobLocation(), pjl, country, candidate);
                }).toList();

        // Document details
        List<Document> documentList = dto.getDocumentDetails().stream()
                .map(document -> documentMapper.toEntity(new Document(), document, candidate)).toList();

        // Save personal details
        candidate.setPersonalDetail(personalDetail);

        // Save professional experiences
        saveProfessionalExperiences(professionalExperienceList);

        // Save higher education list
        saveHigherEducationLists(higherEducationList);

        // Save school education list
        saveSchoolEducationList(schoolEducations);

        // Save memberships
        saveMembershipList(membershipList);

        // Save language proficiencies
        saveLanguageProficiencyList(languageProficiencyList);

        // Save researches
        saveResearchList(researchList);

        // Save achievements
        candidate.setAchievement(achievement);

        // Save referees
        saveRefereeList(refereeList);

        // Save family information
        saveFamilyInformationList(familyInformationList);

        // Save job preferences
        saveJobPreferenceList(jobPreferenceList);

        // Save preferred job locations
        savePreferredJobLocation(preferredJobLocations);

        // Save document details
        saveDocumentList(documentList);*/

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(null);
        commonResponse.setStatus(HttpStatus.CREATED);
        commonResponse.setMessage("Candidate profile save successful.");

        log.info("CommonProfileServiceImpl -> saveUpdate() => ended!");
        return commonResponse;

    }

    /**
     * save candidate common profile with jap and BiDirectional relationships
     *
     * @param idCandidate - common profile candidate id
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveUpdateByJPA(Integer idCandidate) {
        log.info("CommonProfileServiceImpl -> saveUpdateByJPA() => started!");

        // Fetch draft data by draft id
        CommonProfileRequestDTO dto = restDraftClientService.findByIdCandidate(idCandidate);

        // Find candidate by id
        Candidate candidate = candidateRepository.findByUserAccount(dto.getIdCandidate())
                .orElseThrow(() -> new RecordNotFoundException("Candidate not exists."));
        candidate.setIdDraft(dto.getId());
        candidate.setIsVerify(Boolean.TRUE);

        // Location information save
        LocationInformationRequestDTO location = restLocationInfoClientService.saveLocationInfo(dto.getPersonalDetail());
        Integer idLocation = location.getIdLocationInformation();

        // Communication information save
        CommunicationInformationRequestDTO communication = restCommunicationInfoClientService.saveCommunicationInfo(dto.getPersonalDetail());
        Integer idCommunication = communication.getIdCommunicationInformation();

        // Personal details
        candidate.setPersonalDetail(personalDetailMapper.toEntity(new PersonalDetail(), idCommunication, idLocation, dto.getPersonalDetail()));

        // Achievements
        Achievement achievement = new Achievement();
        achievement.setAchievements(dto.getAchievements().getAchievements());
        candidate.setAchievement(achievement);

        // Professional experiences
        List<ProfessionalExperience> professionalExperienceList = new ArrayList<>();
        professionalExperienceRepository.deleteByCandidate(candidate);
        dto.getProfessionalExperiences().forEach(pfx -> {

            Industry industry = industryRepository.findById(pfx.getIdIndustry())
                    .orElseThrow(() -> new RecordNotFoundException("Industry not found!"));

            JobCategory jobCategory = jobCategoryRepository.findById(pfx.getIdJobCategory())
                    .orElseThrow(() -> new RecordNotFoundException("Job category not found!"));

            professionalExperienceList.add(professionalExperienceMapper.toEntity(new ProfessionalExperience(), pfx, industry, jobCategory, candidate));
        });

        //Higher educations
        List<HigherEducation> higherEducationList = new ArrayList<>();
        higherEducationRepository.deleteByCandidate(candidate);
        dto.getHigherEducations().forEach(higherEducation -> {

            HigherEduQualification higherEduQualification = higherEduQualificationRepository.findById(higherEducation.getIdHigherEduQualification())
                    .orElseThrow(() -> new RecordNotFoundException("Higher education qualification not found!"));

            AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(higherEducation.getIdAreaOfStudy())
                    .orElseThrow(() -> new RecordNotFoundException("Area of study not found!"));

            CountryDTO country = restLocationInfoClientService.getById(higherEducation.getIdCountry());
            LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(higherEducation.getIdLanguage());

            higherEducationList.add(higherEducationMapper.toEntity(new HigherEducation(), higherEducation, higherEduQualification, areaOfStudy, country, language, candidate));
        });

        // School educations
        List<SchoolEducation> schoolEducationList = new ArrayList<>();
        schoolEducationRepository.deleteByCandidate(candidate);
        dto.getSchoolEducations().forEach(schoolEducation -> {

            CountryDTO country = restLocationInfoClientService.getById(schoolEducation.getIdCountry());
            LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(schoolEducation.getIdLanguage());

            schoolEducationList.add(schoolEducationMapper.toEntity(new SchoolEducation(), schoolEducation, country, language, candidate));
        });

        // Memberships
        List<Membership> membershipList = new ArrayList<>();
        membershipRepository.deleteByCandidate(candidate);
        dto.getMemberships().forEach(membership -> {

            CountryDTO country = restLocationInfoClientService.getById(membership.getIdCountry());

            MembershipType membershipType = membershipTypeRepository.findById(membership.getIdMembershipType())
                    .orElseThrow(() -> new RecordNotFoundException("Membership type not exists."));

            membershipList.add(memberMapper.toEntity(new Membership(), membership, country, membershipType, candidate));
        });

        // Language proficiencies
        List<LanguageProficiency> languageProficiencyList = new ArrayList<>();
        languageProficiencyRepository.deleteByCandidate(candidate);
        dto.getLanguageProficiencies().forEach(languageProficiency -> {

            LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(languageProficiency.getIdLanguage());

            languageProficiencyList.add(languageProficiencyMapper.dtoToModel(new LanguageProficiency(), languageProficiency, language, candidate));
        });


        // Researches
        List<Research> researchList = new ArrayList<>();
        researchRepository.deleteByCandidate(candidate);
        dto.getResearches().forEach(rsc -> researchList.add(researchMapper.dtoToModel(new Research(), rsc, candidate)));

        // Referees
        List<Referee> refereeList = new ArrayList<>();
        refereeRepository.deleteByCandidate(candidate);
        dto.getReferees().forEach(rfr -> refereeList.add(refereeMapper.dtoToModel(new Referee(), rfr, candidate)));

        // Family information
        List<FamilyInformation> familyInformationList = new ArrayList<>();
        familyInformationRepository.deleteByCandidate(candidate);
        dto.getFamilyInformation().forEach(fmli -> familyInformationList.add(familyInformationMapper.dtoToModel(new FamilyInformation(), fmli, candidate)));

        // Job preferences
        List<JobPreference> jobPreferenceList = new ArrayList<>();
        jobPreferenceRepository.deleteByCandidate(candidate);
        dto.getJobPreferences().forEach(jpref -> jobPreferenceList.add(jobPreferenceMapper.dtoToModel(new JobPreference(), jpref, candidate)));

        // Preferred job location
        List<PreferredJobLocation> preferredJobLocationList = new ArrayList<>();
        preferredJobLocationRepository.deleteByCandidate(candidate);
        dto.getPreferredJobLocations().forEach(prefjl -> {

            CountryDTO country = restLocationInfoClientService.getById(prefjl.getIdCountry());

            preferredJobLocationList.add(preferredJobLocationMapper.dtoToModel(new PreferredJobLocation(), prefjl, country, candidate));
        });

        //Document details
        List<Document> documentList = new ArrayList<>();
        documentRepository.deleteByCandidate(candidate);
        dto.getDocumentDetails().forEach(doc -> {
            HashMap<String, String> hashMap = objectMapper.convertValue(doc.getFile(), HashMap.class);
            String base64EncodedString = hashMap.get("data");
            byte[] file = Base64.getDecoder().decode(base64EncodedString);

            // Assuming you have only one file in the base64 string
            MultipartFile[] files = new MultipartFile[1];
            files[0] = new MockMultipartFile(doc.getActualFileName(), doc.getActualFileName(), CommonUtil.getContentType(doc.getActualFileName()), file);

            DocumentType type = documentTypeRepository.findById(doc.getDocumentTypeId())
                    .orElseThrow(() -> new RecordNotFoundException("Document type is not exists."));

            List<DocumentUploadResponseDTO> uploaded = restDocumentClientService.uploadDocuments(
                    FolderType.CANDIDATE,
                    candidate.getCandidateNo(),
                    type.getName(),
                    files);

            if (!uploaded.isEmpty()) {
                documentList.add(documentMapper.toEntity(new Document(), uploaded.get(0), type, candidate));
            }
        });

        // Candidate
        candidateMapper.toEntity(candidate, professionalExperienceList, higherEducationList, schoolEducationList, membershipList,
                languageProficiencyList, researchList, refereeList, familyInformationList, jobPreferenceList, preferredJobLocationList, documentList);

        CommonResponse commonResponse = new CommonResponse();

        try {
            // Save candidate
            Candidate savedCandidate = candidateRepository.save(candidate);

            commonResponse.setData(
                    new CandidateSaveLazyResponseDTO(savedCandidate.getId(), savedCandidate.getCandidateNo()));
            commonResponse.setStatus(HttpStatus.CREATED);
            commonResponse.setMessage("Candidate profile save successful for BiDirectional relationship with jpa.");

        } catch (Exception e) {
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResponse.setMessage("Candidate profile save failed.");
        }
        log.info("CommonProfileServiceImpl -> saveUpdateByJPA() => ended!");
        return commonResponse;
    }

    /**
     * This method is allowed to get candidate profile by candidate id
     *
     * @param candidateId {@link Integer candidateId} - candidate id
     * @return - {@link CommonResponse} - fetched response.
     * @author @Maleesha99
     */
    @Override
    public CommonResponse getByCandidateId(Integer candidateId) {
        log.info("CommonProfileServiceImpl -> getByCandidateId() => started!");

        CommonProfileResponseDTO commonProfile = new CommonProfileResponseDTO();

        // Fetching existing candidate
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RecordNotFoundException("Candidate is not exists."));

        commonProfile.setIdCandidate(candidate.getId());
        commonProfile.setCandidateNo(candidate.getCandidateNo());

        // Personal details
        PersonalDetailResponseDTO personalDetail = candidate.getPersonalDetail() == null ? null :
                personalDetailMapper.toDto(new PersonalDetailResponseDTO(), candidate.getPersonalDetail());
        commonProfile.setPersonalDetail(personalDetail);

        // Professional experiences
        List<ProfessionalExperienceResponseDTO> professionalExperiences = candidate.getProfessionalExperienceList().isEmpty() ? new ArrayList<>() :
                candidate.getProfessionalExperienceList().stream().map(professionalExperience ->
                        professionalExperienceMapper.toDto(new ProfessionalExperienceResponseDTO(), professionalExperience)).toList();
        commonProfile.setProfessionalExperiences(professionalExperiences);

        // Higher education
        List<HigherEducationResponseDTO> higherEducations = candidate.getHigherEducationList().isEmpty() ? new ArrayList<>() :
                candidate.getHigherEducationList().stream().map(higherEducation ->
                        higherEducationMapper.toDto(new HigherEducationResponseDTO(), higherEducation)).toList();
        commonProfile.setHigherEducations(higherEducations);

        // School education
        List<SchoolEducationResponseDTO> schoolEducations = candidate.getSchoolEducationList().isEmpty() ? new ArrayList<>() :
                candidate.getSchoolEducationList().stream().map(schoolEducation ->
                        schoolEducationMapper.toDto(new SchoolEducationResponseDTO(), schoolEducation)).toList();
        commonProfile.setSchoolEducations(schoolEducations);

        // Memberships
        List<MembershipResponseDTO> memberships = candidate.getMembershipList().isEmpty() ? new ArrayList<>() :
                candidate.getMembershipList().stream().map(membership ->
                        memberMapper.toDto(new MembershipResponseDTO(), membership)).toList();
        commonProfile.setMemberships(memberships);

        // Language proficiencies
        List<LanguageProficiencyResponseDTO> languageProficiencies = candidate.getLanguageProficiencyList().isEmpty() ? new ArrayList<>() :
                candidate.getLanguageProficiencyList().stream().map(languageProficiency ->
                        languageProficiencyMapper.toDto(new LanguageProficiencyResponseDTO(), languageProficiency)).toList();
        commonProfile.setLanguageProficiencies(languageProficiencies);

        // Researches
        List<ResearchResponseDTO> researches = candidate.getResearchList().isEmpty() ? new ArrayList<>() :
                candidate.getResearchList().stream().map(research ->
                        researchMapper.modelToDto(new ResearchResponseDTO(), research)).toList();
        commonProfile.setResearches(researches);

        // Achievements
        AchievementDTO achievement = candidate.getAchievement() == null ? null :
                achievementMapper.modelToDto(new AchievementDTO(), candidate.getAchievement());
        commonProfile.setAchievements(achievement);

        // Referees
        List<RefereeDTO> referees = candidate.getRefereeList().isEmpty() ? new ArrayList<>() :
                candidate.getRefereeList().stream().map(referee ->
                        refereeMapper.modelToDto(new RefereeDTO(), referee)).toList();
        commonProfile.setReferees(referees);

        // Family information
        List<FamilyInformationDTO> familyInformation = candidate.getFamilyInformationList().isEmpty() ? new ArrayList<>() :
                candidate.getFamilyInformationList().stream().map(fi ->
                        familyInformationMapper.modelToDto(new FamilyInformationDTO(), fi)).toList();
        commonProfile.setFamilyInformation(familyInformation);

        // Job preferences
        List<JobPreferenceResponseDTO> jobPreferences = candidate.getJobPreferenceList().isEmpty() ? new ArrayList<>() :
                candidate.getJobPreferenceList().stream().map(jpr ->
                        jobPreferenceMapper.modelToDto(new JobPreferenceResponseDTO(), jpr)).toList();
        commonProfile.setJobPreferences(jobPreferences);

        // Preferred job locations
        List<PreferredJobLocationResponseDTO> preferredJobLocations = candidate.getPreferredJobLocations().isEmpty() ? new ArrayList<>() :
                candidate.getPreferredJobLocations().stream().map(pjl ->
                        preferredJobLocationMapper.modelToDto(new PreferredJobLocationResponseDTO(), pjl)).toList();
        commonProfile.setPreferredJobLocations(preferredJobLocations);

        // Documents
        List<DocumentDTO> documents = candidate.getDocumentList().isEmpty() ? new ArrayList<>() :
                candidate.getDocumentList().stream().map(doc ->
                        documentMapper.toDto(new DocumentDTO(), doc)).toList();
        commonProfile.setDocumentDetails(documents);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setData(commonProfile);
        commonResponse.setStatus(HttpStatus.OK);
        commonResponse.setMessage("Candidate is exists.");

        log.info("CommonProfileServiceImpl -> getByCandidateId() => ended!");
        return commonResponse;

    }

    /**
     * This method is allowed to search candidates
     *
     * @param candidateSearchParams {@link CandidateSearchParamsDTO} - candidate search parameters
     * @param pageRequest
     * @return {@link CommonResponse} - searched candidates
     * @author @Maleesha99
     */
    @Override
    public CommonResponse searchCandidates(CandidateSearchParamsDTO candidateSearchParams, PageRequest pageRequest) {
        log.info("CommonProfileServiceImpl.searchCandidates() => started.");

        Page<Candidate> candidates;
        try {

            Specification<Candidate> specification = candidateSpecification.searchCandidates(candidateSearchParams);
            candidates = candidateRepository.findAll(specification, pageRequest);

            // Page response
            PageResponse pageResponse = new PageResponse();
            pageResponse.setCurrentPage(candidates.getNumber());
            pageResponse.setTotalPages(candidates.getTotalPages());
            pageResponse.setTotalElements(candidates.getTotalElements());
            pageResponse.setDataList(candidateSearchLazyResponse(candidates.getContent()));

            // common response
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(pageResponse);
            commonResponse.setStatus(HttpStatus.OK);
            commonResponse.setMessage("Searched candidate result.");

            return commonResponse;

        } catch (Exception e) {
            log.warn("Search candidates jpa criteria api predicates error {} ", e.getMessage());
            throw new BaseException(500, "Searching candidates failed.");
        }

    }

    private List<CandidateSearchLazyResponseDTO> candidateSearchLazyResponse(List<Candidate> content) {

        List<CandidateSearchLazyResponseDTO> lazyResponse = new ArrayList<>();

        if (!content.isEmpty()) {

            lazyResponse = content.stream()
                    .map(candidate -> candidateMapper.toLazyResponseDTO(new CandidateSearchLazyResponseDTO(), candidate)).toList();

        }

        return lazyResponse;

    }

}
