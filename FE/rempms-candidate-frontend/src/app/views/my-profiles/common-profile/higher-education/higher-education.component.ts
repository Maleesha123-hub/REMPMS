import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { HigherEducation } from '../../../../model/candidate/higherEducation/HigherEducation';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import Swal from 'sweetalert2';
import { AreaOfStudy } from '../../../../model/candidate/areaOfStudy/AreaOfStudy';
import { AreaOfStudyService } from '../../../../service/areaOfStudy/area-of-study.service';
import { Language } from '../../../../model/communication/language/Language';
import { Country } from '../../../../model/location/country/Country';
import { LanguageService } from '../../../../service/communication/language/language.service';
import { CountryService } from '../../../../service/location/country/country.service';
import { HigherEduQualificationService } from '../../../../service/higherEduQualification/higher-edu-qualification.service';
import { HigherEduQualification } from '../../../../model/candidate/higherEduQualification/HigherEduQualification';
import { catchError, map, Observable, throwError } from 'rxjs';
import { AwardType } from '../../../../enums/AwardType';

@Component({
  selector: 'app-higher-education',
  templateUrl: './higher-education.component.html',
  styleUrl: './higher-education.component.scss',
})
export class HigherEducationComponent implements OnInit {
  higherEducationForm: FormGroup | any;
  tempList: HigherEducation[] = []; // Initialize tempList as an empty array
  tableList: HigherEducation[] = []; // Initialize tableList as an empty array
  areaOfStudies: AreaOfStudy[] = [];
  higherEduQualifications: HigherEduQualification[] = [];
  languages: Language[] = [];
  countries: Country[] = [];
  idCandidate: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  idDraft: string | any;
  emptyListErrorVisible = false;
  showUpdateButton = false;
  showSaveButton = true;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private draftService: CommonProfileDraftService,
    private localStorageService: LocalStorageService,
    private areaOfStudyService: AreaOfStudyService,
    private languagesService: LanguageService,
    private countryService: CountryService,
    private higherEduQualificationService: HigherEduQualificationService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveAreOfStudies();
    this.getAllActiveLanguages();
    this.getAllActiveCountries();
    this.getAllActiveHigherEduQualification();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.higherEducationForm = this.formBuilder.group({
      idHigherEducation: [''],
      instituteOfStudy: ['', Validators.required],
      affiliatedInstitute: ['', Validators.required],
      awardType: ['-1', Validators.required],
      idHigherEduQualification: ['-1', Validators.required],
      idAreaOfStudy: ['-1', Validators.required],
      commencedDate: ['', Validators.required],
      completionDate: ['', Validators.required],
      studentId: ['', Validators.required],
      result: ['', Validators.required],
      idCountry: ['-1', Validators.required],
      idLanguage: ['-1', Validators.required],
      description: ['', Validators.required],
    });
  }

  higherEducationForTempListTable() {
    if (this.higherEducationForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.higherEducationForm.value;

      // getting area of study name
      const selectedAreaOfStudyId =
        this.higherEducationForm.get('idAreaOfStudy')?.value;
      const selectedAreaOfStudy = this.areaOfStudies.find(
        (area: AreaOfStudy) => area.id == selectedAreaOfStudyId,
      );

      // getting higher edu qualification name
      const selectedHigherEduQualificationId = this.higherEducationForm.get(
        'idHigherEduQualification',
      )?.value;
      const selectedHigherEduQualification = this.higherEduQualifications.find(
        (higherEduQua: HigherEduQualification) =>
          higherEduQua.id == selectedHigherEduQualificationId,
      );

      // getting award type name
      // assuming awardTypeValue is a string representing the name of the enum value
      const awardTypeValue = this.higherEducationForm.get('awardType').value;
      // Casting the expression to the enum type to avoid TypeScript error
      const selectedAwardType =
        AwardType[awardTypeValue as keyof typeof AwardType];

      // Mapping form values to ProfessionalExperience object
      const higherEducation: HigherEducation = {
        idHigherEducation: formValue.idHigherEducation,
        instituteOfStudy: formValue.instituteOfStudy,
        affiliatedInstitute: formValue.affiliatedInstitute,
        awardType: formValue.awardType,
        awardTypeValue: selectedAwardType,
        idHigherEduQualification: formValue.idHigherEduQualification,
        idAreaOfStudy: formValue.idAreaOfStudy,
        commencedDate: formValue.commencedDate,
        completionDate: formValue.completionDate,
        studentId: formValue.studentId,
        result: formValue.result,
        idCountry: formValue.idCountry,
        idLanguage: formValue.idLanguage,
        description: formValue.description,
        higherEduQualification: selectedHigherEduQualification?.name,
        areaOfStudy: selectedAreaOfStudy?.name,
      };

      this.tempList.push(higherEducation);
    } else {
      this.higherEducationForm.markAllAsTouched();
    }
  }

  removeTempItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  fetchHigherEduQualificationById(
    id: string,
  ): Observable<HigherEduQualification> {
    return this.higherEduQualificationService.getById(id).pipe(
      map((response) => {
        if (response && response.data) {
          return response.data as HigherEduQualification;
        } else {
          throw new Error('No data returned');
        }
      }),
      catchError((error) => {
        console.error('fetchHigherEduQualificationById() error:', error);

        return throwError('Something went wrong, please try again later.');
      }),
    );
  }

  createOrModifyCommonProfileDraft() {
    if (this.higherEducationForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: HigherEducation[] = this.tempList.concat(
          this.tableList,
        );

        // Create an instance of CommonProfileDraft and assign higherEducation
        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: [],
          higherEducations: mergedArray, // Assuming higherEducationForm has the same structure as higherEducation
          schoolEducations: [],
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
      this.higherEducationForm.markAllAsTouched();
    }
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
          this.commonProfileDraft.higherEducations.forEach(
            (higherEducation: HigherEducation) => {
              // getting area of study name
              const selectedAreaOfStudy = this.areaOfStudies.find(
                (area: AreaOfStudy) => area.id == higherEducation.idAreaOfStudy,
              );

              // getting higher edu qualification name
              const selectedHigherEduQualification =
                this.higherEduQualifications.find(
                  (higherEduQua: HigherEduQualification) =>
                    higherEduQua.id == higherEducation.idHigherEduQualification,
                );

              // getting award type name
              // Casting the expression to the enum type to avoid TypeScript error
              const selectedAwardType =
                AwardType[higherEducation.awardType as keyof typeof AwardType];

              higherEducation.areaOfStudy = selectedAreaOfStudy?.name;
              higherEducation.higherEduQualification =
                selectedHigherEduQualification?.name;
              higherEducation.awardTypeValue = selectedAwardType;

              this.tableList.push(higherEducation);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  private getAllActiveAreOfStudies() {
    this.areaOfStudyService.getAllActiveAreOfStudies().subscribe(
      (data) => {
        if (data.data != null) {
          this.areaOfStudies = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveAreOfStudies() >> ' + error.get);
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

  private getAllActiveHigherEduQualification() {
    this.higherEduQualificationService
      .getAllActiveHigherEduQualification()
      .subscribe(
        (data) => {
          if (data.data != null) {
            this.higherEduQualifications = data.data;
          }
        },
        (error) => {
          console.log('getAllActiveHigherEduQualification() >> ' + error.get);
        },
      );
  }

  resetCommonProfileForm() {
    this.higherEducationForm.reset();
    this.higherEducationForm.get('awardType')?.patchValue('-1');
    this.higherEducationForm.get('idHigherEduQualification')?.patchValue('-1');
    this.higherEducationForm.get('idAreaOfStudy')?.patchValue('-1');
    this.higherEducationForm.get('idCountry')?.patchValue('-1');
    this.higherEducationForm.get('idLanguage')?.patchValue('-1');
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/school-education']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/professional-experiences']);
  }

  editHigherEducation(higherEdu: HigherEducation) {
    this.showUpdateButton = true;
    this.showSaveButton = false;
    this.tempList = [];

    this.higherEducationForm.patchValue({
      idHigherEducation: higherEdu.idHigherEducation,
      instituteOfStudy: higherEdu.instituteOfStudy,
      affiliatedInstitute: higherEdu.affiliatedInstitute,
      awardType: higherEdu.awardType,
      idHigherEduQualification: higherEdu.idHigherEduQualification,
      idAreaOfStudy: higherEdu.idAreaOfStudy,
      commencedDate: higherEdu.commencedDate,
      completionDate: higherEdu.completionDate,
      studentId: higherEdu.studentId,
      result: higherEdu.result,
      idCountry: higherEdu.idCountry,
      idLanguage: higherEdu.idLanguage,
      description: higherEdu.description,
    });
  }

  deleteHigherEducation(higherEdu: HigherEducation) {
    // Assuming higher education is the specific object you want to remove
    this.tableList = this.tableList.filter((item) => item !== higherEdu);

    // Create an instance of CommonProfileDraft and assign HigherEducation
    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences: [],
      higherEducations: this.tableList.length == 0 ? null : this.tableList,
      schoolEducations: [],
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
            this.tableList = [];
            this.findDraftByIdCandidate(
              sessionStorage.getItem('idCandidate') ?? '{}',
            );
          } else {
            Swal.fire('Cancelled', data.message, 'error');
          }
        },
        (error) => {
          console.log('deleteHigherEducation() >> ' + error.get);
        },
      );
  }
}
