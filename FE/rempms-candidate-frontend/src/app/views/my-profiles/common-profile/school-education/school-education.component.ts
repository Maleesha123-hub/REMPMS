import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SchoolEducationService } from '../../../../service/schoolEducation/school-education.service';
import { Scheme } from '../../../../model/candidate/scheme/Scheme';
import { SchoolQualification } from '../../../../enums/SchoolQualification';
import { Country } from '../../../../model/location/country/Country';
import { Language } from '../../../../model/communication/language/Language';
import { LanguageService } from '../../../../service/communication/language/language.service';
import { CountryService } from '../../../../service/location/country/country.service';
import { SubjectService } from '../../../../service/subject/subject.service';
import { Subject } from '../../../../model/candidate/subject/Subject';
import { SubjectHasSchoolEduGrade } from '../../../../model/candidate/schoolEducation/SubjectHasSchoolEduGrade';
import { Grade } from '../../../../enums/Grade';
import { SchoolEducation } from '../../../../model/candidate/schoolEducation/SchoolEducation';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import Swal from 'sweetalert2';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';

@Component({
  selector: 'app-school-education',
  templateUrl: './school-education.component.html',
  styleUrl: './school-education.component.scss',
})
export class SchoolEducationComponent implements OnInit {
  schoolEducationForm: FormGroup | any;
  subjectHasSchoolEduGradeTempList: SubjectHasSchoolEduGrade[] = [];
  schemes: Scheme[] | any;
  countries: Country[] | any;
  languages: Language[] | any;
  subjects: Subject[] | any;
  tempList: SchoolEducation[] = [];
  tableList: SchoolEducation[] = [];
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  emptyListErrorVisible = false;
  showUpdateButton = false;
  showSaveButton = true;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private schoolEduService: SchoolEducationService,
    private languagesService: LanguageService,
    private countryService: CountryService,
    private subjectService: SubjectService,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveCountries();
    this.getAllActiveLanguages();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.schoolEducationForm = this.formBuilder.group({
      idSchoolEducation: [''],
      schoolEduQualification: ['-1', Validators.required],
      idScheme: ['-1', Validators.required],
      school: ['', Validators.required],
      idSubject: ['-1', Validators.required],
      grade: ['-1', Validators.required],
      achievedOn: ['', Validators.required],
      idLanguage: ['-1', Validators.required],
      idCountry: ['-1', Validators.required],
      description: ['', Validators.required],
    });
  }

  schoolEducationsForTempListTable() {
    if (this.schoolEducationForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.schoolEducationForm.value;

      const selectedSchemeId = this.schoolEducationForm.get('idScheme')?.value;
      const selectedScheme =
        selectedSchemeId != '-1'
          ? this.schemes.find((scheme: Scheme) => scheme.id == selectedSchemeId)
          : null;

      const schoolQualificationValue = this.schoolEducationForm.get(
        'schoolEduQualification',
      ).value;
      // Casting the expression to the enum type to avoid TypeScript error
      const selectedSchoolQualification =
        SchoolQualification[
          schoolQualificationValue as keyof typeof SchoolQualification
        ];

      const schoolEducation: SchoolEducation = {
        idSchoolEducation: formValue.idSchoolEducation,
        schoolEduQualification: selectedSchoolQualification, //enum : school qualification
        school: formValue.school,
        achievedOn: formValue.achievedOn,
        idLanguage: formValue.idLanguage,
        idCountry: formValue.idCountry,
        description: formValue.description,
        idScheme: formValue.idScheme,
        schemeName: selectedScheme?.schemeName ?? '-',
        subjectHasSchoolEduGrades: this.subjectHasSchoolEduGradeTempList,
      };

      this.tempList.push(schoolEducation);
    } else {
      this.schoolEducationForm.markAllAsTouched();
    }
  }

  createOrModifyCommonProfileDraft() {
    if (this.schoolEducationForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: SchoolEducation[] = this.tempList.concat(
          this.tableList,
        );
        console.log(mergedArray);
        // Create an instance of CommonProfileDraft and assign SchoolEducation
        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: [],
          higherEducations: [],
          schoolEducations: mergedArray, // Assuming schoolEducationForm has the same structure as SchoolEducation
          memberships: [],
          languageProficiencies: [],
          researches: [],
          achievements: null,
          referees: [],
          familyInformation: [],
          jobPreferences: [],
          preferredJobLocations: null,
          documentDetails: [],
        };

        this.draftService
          .createOrModifyCommonProfileDraft(commonProfileDraft, this.files)
          .subscribe(
            (data) => {
              if (data.status == 'OK') {
                Swal.fire('Draft saved success.!', data.message, 'success');

                this.resetCommonProfileForm();
                this.findDraftByIdCandidate(
                  sessionStorage.getItem('idCandidate') ?? '{}',
                );

                this.showUpdateButton = false;
                this.showSaveButton = true;
              } else {
                Swal.fire('Cancelled', data.message, 'error');
              }
            },
            (error) => {
              console.log('createOrModifyCommonProfileDraft() >> ' + error.get);
            },
          );
      }
    } else {
      this.schoolEducationForm.markAllAsTouched();
    }
  }

  deleteSchoolEducation(schoolEducation: SchoolEducation) {
    // Assuming school education is the specific object you want to remove
    this.tableList = this.tableList.filter((item) => item !== schoolEducation);

    // Create an instance of CommonProfileDraft and assign SchoolEducation
    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences: [],
      higherEducations: [],
      schoolEducations: this.tableList.length == 0 ? null : this.tableList,
      memberships: [],
      languageProficiencies: [],
      researches: [],
      achievements: null,
      referees: [],
      familyInformation: [],
      jobPreferences: [],
      preferredJobLocations: null,
      documentDetails: [],
    };

    this.draftService
      .createOrModifyCommonProfileDraft(commonProfileDraft, this.files)
      .subscribe(
        (data) => {
          if (data.status == 'OK') {
            Swal.fire('Deleted success.!', data.message, 'success');

            this.resetCommonProfileForm();
            this.findDraftByIdCandidate(
              sessionStorage.getItem('idCandidate') ?? '{}',
            );

            this.showUpdateButton = false;
            this.showSaveButton = true;
          } else {
            Swal.fire('Cancelled', data.message, 'error');
          }
        },
        (error) => {
          console.log('deleteSchoolEducation() >> ' + error.get);
        },
      );
  }

  editSchoolEducation(schoolEducation: SchoolEducation) {
    this.showUpdateButton = true;
    this.showSaveButton = false;
    this.tempList = [];
    let schoolEduQuaType: string = '';

    for (const key in SchoolQualification) {
      if (
        SchoolQualification[key as keyof typeof SchoolQualification] ===
        schoolEducation.schoolEduQualification
      ) {
        schoolEduQuaType = key as keyof typeof SchoolQualification;
      }
    }

    let scheme: Scheme = //TODO: scheme by scheme id api.
      this.schoolEducationForm.patchValue({
        idSchoolEducation: [''],
        schoolEduQualification: schoolEduQuaType,
        idScheme: schoolEducation.idScheme,
        school: schoolEducation.school,
        idSubject: '-1',
        grade: '-1',
        achievedOn: schoolEducation.achievedOn,
        idLanguage: schoolEducation.idLanguage,
        idCountry: schoolEducation.idCountry,
        description: schoolEducation.description,
      });

    this.subjectHasSchoolEduGradeTempList = [];
    this.subjectHasSchoolEduGradeTempList =
      schoolEducation.subjectHasSchoolEduGrades;
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.tableList = [];
    this.tempList = [];

    this.draftService.findDraftByIdCandidate(idCandidate).subscribe(
      (data) => {
        if (data.data != null) {
          this.commonProfileDraft = data.data;

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          //Patch values to the form
          this.commonProfileDraft.schoolEducations.forEach(
            (schoolEducation: SchoolEducation) => {
              this.tableList.push(schoolEducation);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  subjectHasSchoolEduGradeListForTempListTable() {
    const formValue = this.schoolEducationForm.value;

    if (formValue.idSubject != '-1' && formValue.grade != '-1') {
      const selectedSubjectId =
        this.schoolEducationForm.get('idSubject')?.value;
      const selectedSubject = this.subjects.find(
        (subject: Subject) => subject.id == selectedSubjectId,
      );

      const gradeValue = this.schoolEducationForm.get('grade').value;
      const selectedGrade = Grade[gradeValue as keyof typeof Grade];

      // Mapping form values to SubjectHasSchoolEduGrade object
      const schoolEduGrade: {
        idSubjectHasSchoolEduGradeDTO: string | any;
        idSchoolEducation: string | any;
        grade: string | any;
        idSubject: string | any;
        subject: string | any;
      } = {
        idSubject: formValue.idSubject,
        subject: selectedSubject.subjectName,
        grade: selectedGrade,
        idSubjectHasSchoolEduGradeDTO: null,
        idSchoolEducation: null,
      };

      // Add school subject has grade to tempList
      this.subjectHasSchoolEduGradeTempList.push(schoolEduGrade);
    }
  }

  removeTempSubHasGradeItem(index: number) {
    this.subjectHasSchoolEduGradeTempList.splice(index, 1); // Remove the item at the specified index
  }

  removeTempListItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  getSubjectsBySchoolEduQualification(event: Event): void {
    this.subjects = [];

    const schoolEduQualification = (event.target as HTMLSelectElement).value;

    // getting schoolEduQualification type name
    // Casting the expression to the enum type to avoid TypeScript error
    const selectedSchoolEduQualification =
      SchoolQualification[
        schoolEduQualification as keyof typeof SchoolQualification
      ];

    this.subjectService
      .getBySchoolEduQualification(selectedSchoolEduQualification)
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.subjects = data.data;
          }
        },
        (error) => {
          console.log('getSubjectsBySchoolEduQualification() >> ' + error.get);
        },
      );
  }

  getBySchoolEduQualificationAndScheme(event: Event): void {
    this.subjects = [];

    const schemeId = (event.target as HTMLSelectElement).value;
    const schoolEduQualification = this.schoolEducationForm.get(
      'schoolEduQualification',
    ).value;
    // Casting the expression to the enum type to avoid TypeScript error
    const selectedSchoolEduQualification =
      SchoolQualification[
        schoolEduQualification as keyof typeof SchoolQualification
      ];

    this.subjectService
      .getBySchoolEduQualificationAndScheme(
        selectedSchoolEduQualification,
        schemeId,
      )
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.subjects = data.data;
          }
        },
        (error) => {
          console.log('getBySchoolEduQualificationAndScheme() >> ' + error.get);
        },
      );
  }

  private getAllActiveCountries() {
    this.countryService.getAllActiveCountries().subscribe(
      (data) => {
        if (data.data != null) {
          this.countries = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveCountries() >> ' + error.get);
      },
    );
  }

  private getAllActiveLanguages() {
    this.languagesService.getAllActiveLanguages().subscribe(
      (data) => {
        if (data.data != null) {
          this.languages = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveLanguages() >> ' + error.get);
      },
    );
  }

  onQualificationChange(event: Event): void {
    const selectedQualificationId = (event.target as HTMLSelectElement).value;

    const schemeDropdown = this.schoolEducationForm.get('idScheme');

    if (selectedQualificationId === 'G_C_E_O_L') {
      schemeDropdown.disable();
      schemeDropdown.setValue('-1'); // Reset selection
    } else {
      schemeDropdown.enable();
    }
  }

  getBySchoolEduQualification(event: Event): void {
    const schoolEduQualification = (event.target as HTMLSelectElement).value;

    // getting schoolEduQualification type name
    // Casting the expression to the enum type to avoid TypeScript error
    const selectedSchoolEduQualification =
      SchoolQualification[
        schoolEduQualification as keyof typeof SchoolQualification
      ];

    this.schoolEduService
      .getBySchoolEduQualification(selectedSchoolEduQualification)
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.schemes = data.data;
          }
        },
        (error) => {
          console.log('getBySchoolEduQualification() >> ' + error.get);
        },
      );
  }

  resetCommonProfileForm() {
    this.tempList = [];
    this.subjectHasSchoolEduGradeTempList = [];
    this.schoolEducationForm.reset();
    this.schoolEducationForm.get('schoolEduQualification')?.patchValue('-1');
    this.schoolEducationForm.get('idScheme')?.patchValue('-1');
    this.schoolEducationForm.get('idSubject')?.patchValue('-1');
    this.schoolEducationForm.get('grade')?.patchValue('-1');
    this.schoolEducationForm.get('idCountry')?.patchValue('-1');
    this.schoolEducationForm.get('idLanguage')?.patchValue('-1');
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/membership']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/higher-education']);
  }
}
