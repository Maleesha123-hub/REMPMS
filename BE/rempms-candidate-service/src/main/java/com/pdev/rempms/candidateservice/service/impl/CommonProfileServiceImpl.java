package com.pdev.rempms.candidateservice.service.impl;

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
import com.pdev.rempms.candidateservice.dto.location.LocationInformationRequestDTO;
import com.pdev.rempms.candidateservice.dto.location.country.CountryDTO;
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
import com.pdev.rempms.candidateservice.service.rest.RestLocationInfoClientService;
import com.pdev.rempms.candidateservice.service.validation.CandidateProfileValidation;
import com.pdev.rempms.candidateservice.specification.CandidateSpecification;
import com.pdev.rempms.candidateservice.util.CommonResponse;
import com.pdev.rempms.candidateservice.util.CommonValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private final CandidateRepository candidateRepository;
    private final PersonalDetailRepository personalDetailRepository;
    private final ProfessionalExperienceRepository professionalExperienceRepository;
    private final HigherEducationRepository higherEducationRepository;
    private final SchoolEducationRepository schoolEducationRepository;
    private final MembershipRepository membershipRepository;
    private final LanguageProficiencyRepository languageProficiencyRepository;
    private final ResearchRepository researchRepository;
    private final AchievementsRepository achievementsRepository;
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

    private final CandidateProfileValidation candidateProfileValidation;

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

        log.info("CommonProfileServiceImpl -> saveUpdate() => started!");

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
        personalDetailMapper.toEntity(personalDetail, candidate, idCommunication, idLocation, dto.getPersonalDetail());

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
                    CountryDTO country = restLocationInfoClientService.getById(pjl.getCountryId());
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
        saveDocumentList(documentList);

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
     * @param dto - common profile data
     * @return - {@link CommonResponse} - save success info.
     * @author @Maleesha99
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse saveUpdateByJPA(CommonProfileRequestDTO dto) {
        log.info("CommonProfileServiceImpl -> saveUpdateByJPA() => started!");

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
        personalDetailMapper.toEntity(personalDetail, candidate, idCommunication, idLocation, dto.getPersonalDetail());

        // Achievements
        Achievement achievement = CommonValidation.integerNullValidation(dto.getAchievements().getIdAchievement()) ? new Achievement() :
                achievementsRepository.findById(dto.getAchievements().getIdAchievement()).
                        orElseThrow(() -> new RecordNotFoundException("Achievements not exists."));
        achievementMapper.dtoToModel(achievement, dto.getAchievements(), candidate);

        // Professional experiences
        List<ProfessionalExperience> professionalExperienceList = new ArrayList<>();
        dto.getProfessionalExperiences().forEach(pfx -> {

            Industry industry = industryRepository.findById(pfx.getIdIndustry())
                    .orElseThrow(() -> new RecordNotFoundException("Industry not found!"));

            JobCategory jobCategory = jobCategoryRepository.findById(pfx.getIdJobCategory())
                    .orElseThrow(() -> new RecordNotFoundException("Job category not found!"));

            ProfessionalExperience professionalExperience = CommonValidation.integerNullValidation(pfx.getIdProfessionalExperience()) ? new ProfessionalExperience() :
                    professionalExperienceRepository.findById(pfx.getIdProfessionalExperience())
                            .orElseThrow(() -> new RecordNotFoundException("Professional experience not exists."));

            professionalExperienceList.add(professionalExperienceMapper.toEntity(professionalExperience, pfx, industry, jobCategory, candidate));

        });

        //Higher educations
        List<HigherEducation> higherEducationList = new ArrayList<>();
        dto.getHigherEducations().forEach(higherEducation -> {

            HigherEduQualification higherEduQualification = higherEduQualificationRepository.findById(higherEducation.getIdHigherEduQualification())
                    .orElseThrow(() -> new RecordNotFoundException("Higher education qualification not found!"));

            AreaOfStudy areaOfStudy = areaOfStudyRepository.findById(higherEducation.getIdAreaOfStudy())
                    .orElseThrow(() -> new RecordNotFoundException("Area of study not found!"));

            CountryDTO country = restLocationInfoClientService.getById(higherEducation.getIdCountry());
            LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(higherEducation.getIdLanguage());

            HigherEducation education = CommonValidation.integerNullValidation(higherEducation.getIdHigherEducation()) ? new HigherEducation() :
                    higherEducationRepository.findById(higherEducation.getIdHigherEducation())
                            .orElseThrow(() -> new RecordNotFoundException("Higher education not exists."));

            higherEducationList.add(higherEducationMapper.toEntity(education, higherEducation, higherEduQualification, areaOfStudy, country, language, candidate));

        });

        // School educations
        List<SchoolEducation> schoolEducationList = new ArrayList<>();
        dto.getSchoolEducations().forEach(schoolEducation -> {

            CountryDTO country = restLocationInfoClientService.getById(schoolEducation.getIdCountry());
            LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(schoolEducation.getIdLanguage());

            SchoolEducation education = CommonValidation.integerNullValidation(schoolEducation.getIdSchoolEducation()) ? new SchoolEducation() :
                    schoolEducationRepository.findById(schoolEducation.getIdSchoolEducation())
                            .orElseThrow(() -> new RecordNotFoundException("School education not exists."));

            schoolEducationList.add(schoolEducationMapper.toEntity(education, schoolEducation, country, language, candidate));

        });

        // Memberships
        List<Membership> membershipList = new ArrayList<>();
        dto.getMemberships().forEach(membership -> {

            CountryDTO country = restLocationInfoClientService.getById(membership.getIdCountry());

            MembershipType membershipType = membershipTypeRepository.findById(membership.getIdMembershipType())
                    .orElseThrow(() -> new RecordNotFoundException("Membership type not exists."));

            Membership membership1 = CommonValidation.integerNullValidation(membership.getIdMembership()) ? new Membership() :
                    membershipRepository.findById(membership.getIdMembership())
                            .orElseThrow(() -> new RecordNotFoundException("Membership not exists."));

            membershipList.add(memberMapper.toEntity(membership1, membership, country, membershipType, candidate));

        });

        // Language proficiencies
        List<LanguageProficiency> languageProficiencyList = new ArrayList<>();
        dto.getLanguageProficiencies().forEach(languageProficiency -> {

            LanguageDTO language = restCommunicationInfoClientService.getActiveLanguageById(languageProficiency.getIdLanguage());

            LanguageProficiency proficiency = CommonValidation.integerNullValidation(languageProficiency.getIdLanguageProficiency()) ? new LanguageProficiency() :
                    languageProficiencyRepository.findById(languageProficiency.getIdLanguageProficiency())
                            .orElseThrow(() -> new RecordNotFoundException("Language proficiency not exists."));

            languageProficiencyList.add(languageProficiencyMapper.dtoToModel(proficiency, languageProficiency, language, candidate));

        });


        // Researches
        List<Research> researchList = new ArrayList<>();
        dto.getResearches().forEach(rsc -> {

            Research research = CommonValidation.integerNullValidation(rsc.getIdResearch()) ? new Research() :
                    researchRepository.findById(rsc.getIdResearch())
                            .orElseThrow(() -> new RecordNotFoundException("Research not exists."));

            researchList.add(researchMapper.dtoToModel(research, rsc, candidate));

        });

        // Referees
        List<Referee> refereeList = new ArrayList<>();
        dto.getReferees().forEach(rfr -> {

            Referee referee = CommonValidation.integerNullValidation(rfr.getIdReferee()) ? new Referee() :
                    refereeRepository.findById(rfr.getIdReferee())
                            .orElseThrow(() -> new RecordNotFoundException("Referee not exists."));

            refereeList.add(refereeMapper.dtoToModel(referee, rfr, candidate));

        });

        // Family information
        List<FamilyInformation> familyInformationList = new ArrayList<>();
        dto.getFamilyInformation().forEach(fmli -> {

            FamilyInformation familyInformation = CommonValidation.integerNullValidation(fmli.getIdFamilyInformation()) ? new FamilyInformation() :
                    familyInformationRepository.findById(fmli.getIdFamilyInformation())
                            .orElseThrow(() -> new RecordNotFoundException("Family information not exists."));

            familyInformationList.add(familyInformationMapper.dtoToModel(familyInformation, fmli, candidate));

        });

        // Job preferences
        List<JobPreference> jobPreferenceList = new ArrayList<>();
        dto.getJobPreferences().forEach(jpref -> {

            JobPreference jobPreference = CommonValidation.integerNullValidation(jpref.getIdJobPreference()) ? new JobPreference() :
                    jobPreferenceRepository.findById(jpref.getIdJobPreference())
                            .orElseThrow(() -> new RecordNotFoundException("Job preference not exists."));

            jobPreferenceList.add(jobPreferenceMapper.dtoToModel(jobPreference, jpref, candidate));

        });

        // Preferred job location
        List<PreferredJobLocation> preferredJobLocationList = new ArrayList<>();
        dto.getPreferredJobLocations().forEach(prefjl -> {

            candidate.getPreferredJobLocations().forEach(previousJobLocation -> {
                previousJobLocation.setActive(false);
                preferredJobLocationList.add(previousJobLocation);
            });

            CountryDTO country = restLocationInfoClientService.getById(prefjl.getCountryId());

            preferredJobLocationList.add(preferredJobLocationMapper.dtoToModel(new PreferredJobLocation(), prefjl, country, candidate));

        });

        //Document details
        List<Document> documentList = new ArrayList<>();
        dto.getDocumentDetails().forEach(doc -> {

            candidate.getDocumentList().forEach(previousDoc -> {
                previousDoc.setActive(false);
                documentList.add(previousDoc);
            });

            documentList.add(documentMapper.toEntity(new Document(), doc, candidate));

        });

        // Candidate
        candidateMapper.toEntity(candidate, personalDetail, professionalExperienceList, higherEducationList, schoolEducationList, membershipList,
                languageProficiencyList, researchList, achievement, refereeList, familyInformationList, jobPreferenceList, preferredJobLocationList, documentList);

        CommonResponse commonResponse = new CommonResponse();

        try {

            // Save candidate
            Candidate savedCandidate = candidateRepository.save(candidate);

            commonResponse.setData(
                    new CandidateSaveLazyResponseDTO(savedCandidate.getId(), savedCandidate.getCandidateNo()));
            commonResponse.setStatus(HttpStatus.CREATED);
            commonResponse.setMessage("Candidate profile save successful for BiDirectional relationship with jpa.");

        } catch (Exception e) {

            e.printStackTrace();
            commonResponse.setData(null);
            commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            commonResponse.setMessage("Candidate profile save failed.");

        }

        log.info("CommonProfileServiceImpl -> saveUpdateByJPA() => ended!");
        return commonResponse;

    }

    private void saveProfessionalExperiences(List<ProfessionalExperience> professionalExperienceList) {

        try {

            log.info("Saving professional experiences...");
            professionalExperienceRepository.saveAll(professionalExperienceList);
            log.info("Professional experiences saved.");

        } catch (Exception e) {
            log.warn("Professional experiences save failed: {}", e.getMessage());

            throw new BaseException(500, "Professional experiences save failed.");

        }

    }

    private void saveHigherEducationLists(List<HigherEducation> higherEducationList) {

        try {

            log.info("Saving higher education list...");
            higherEducationRepository.saveAll(higherEducationList);
            log.info("Higher education list saved.");

        } catch (Exception e) {
            log.warn("Higher education list save failed: {}", e.getMessage());

            throw new BaseException(500, "Higher education list save failed.");

        }

    }

    private void saveSchoolEducationList(List<SchoolEducation> schoolEducations) {

        try {

            log.info("Saving school education list...");
            schoolEducationRepository.saveAll(schoolEducations);
            log.info("School education list saved.");

        } catch (Exception e) {
            log.warn("School education list save failed: {}", e.getMessage());

            throw new BaseException(500, "School education list save failed.");

        }

    }

    private void saveMembershipList(List<Membership> membershipList) {

        try {

            log.info("Saving memberships...");
            membershipRepository.saveAll(membershipList);
            log.info("Memberships saved.");

        } catch (Exception e) {
            log.warn("Memberships save failed: {}", e.getMessage());

            throw new BaseException(500, "Memberships save failed.");

        }

    }

    private void saveLanguageProficiencyList(List<LanguageProficiency> languageProficiencyList) {

        try {

            log.info("Saving language proficiencies...");
            languageProficiencyRepository.saveAll(languageProficiencyList);
            log.info("Language proficiencies saved.");

        } catch (Exception e) {
            log.warn("Language proficiencies save failed: {}", e.getMessage());

            throw new BaseException(500, "Language proficiencies save failed.");

        }

    }

    private void saveResearchList(List<Research> researchList) {

        try {

            log.info("Saving research list...");
            researchRepository.saveAll(researchList);
            log.info("Research list saved.");

        } catch (Exception e) {
            log.warn("Researches save failed: {}", e.getMessage());

            throw new BaseException(500, "Researches save failed.");

        }

    }

    private void saveRefereeList(List<Referee> refereeList) {

        try {

            log.info("Saving referee list...");
            refereeRepository.saveAll(refereeList);
            log.info("Referee list saved.");

        } catch (Exception e) {
            log.warn("Referees save failed: {}", e.getMessage());

            throw new BaseException(500, "Referees save failed.");

        }

    }

    private void saveFamilyInformationList(List<FamilyInformation> familyInformationList) {

        try {

            log.info("Saving family information list...");
            familyInformationRepository.saveAll(familyInformationList);
            log.info("Family information list saved.");

        } catch (Exception e) {
            log.warn("Family information list save failed: {}", e.getMessage());

            throw new BaseException(500, "Family information list save failed.");

        }

    }

    private void saveJobPreferenceList(List<JobPreference> jobPreferenceList) {

        try {

            log.info("Saving job preference list...");
            jobPreferenceRepository.saveAll(jobPreferenceList);
            log.info("Job preference list saved.");

        } catch (Exception e) {
            log.warn("Job preferences save failed: {}", e.getMessage());

            throw new BaseException(500, "Job preferences save failed.");

        }

    }

    private void savePreferredJobLocation(List<PreferredJobLocation> preferredJobLocations) {

        try {

            log.info("Saving preferred job location...");
            preferredJobLocationRepository.saveAll(preferredJobLocations);
            log.info("Preferred job location saved.");

        } catch (Exception e) {
            log.warn("Preferred job locations save failed: {}", e.getMessage());

            throw new BaseException(500, "Preferred job locations save failed.");

        }

    }

    private void saveDocumentList(List<Document> documentList) {

        try {

            log.info("Saving document list...");
            documentRepository.saveAll(documentList);
            log.info("Document list saved.");

        } catch (Exception e) {
            log.warn("Documents save failed: {}", e.getMessage());

            throw new BaseException(500, "Documents save failed.");

        }

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
