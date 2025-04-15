import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Language } from '../../../../model/communication/language/Language';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LanguageService } from '../../../../service/communication/language/language.service';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { LanguageProficiency } from '../../../../model/candidate/languageProficiency/LanguageProficiency';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import Swal from 'sweetalert2';
import { Proficiency } from '../../../../enums/Proficiency';

@Component({
  selector: 'app-language-proficiency',
  templateUrl: './language-proficiency.component.html',
  styleUrl: './language-proficiency.component.scss',
})
export class LanguageProficiencyComponent implements OnInit {
  languageProficiencyForm: FormGroup | any;
  languages: Language[] | any;
  tempList: LanguageProficiency[] = [];
  tableList: LanguageProficiency[] = [];
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
    private languageService: LanguageService,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveLanguages();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.languageProficiencyForm = this.formBuilder.group({
      idLanguageProficiency: [''],
      idLanguage: ['-1', Validators.required],
      spoken: ['-1', Validators.required],
      reading: ['-1', Validators.required],
      writing: ['-1', Validators.required],
    });
  }

  removeTempLangProfItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  tempListTable() {
    if (this.languageProficiencyForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.languageProficiencyForm.value;

      const selectedLanguage = this.languages.find(
        (language: Language) => language.idLanguage == formValue.idLanguage,
      );

      const languageProf: LanguageProficiency = {
        idLanguageProficiency: formValue.idLanguageProficiency,
        idLanguage: formValue.idLanguage,
        languageName: selectedLanguage.languageName,
        spoken: formValue.spoken,
        reading: formValue.reading,
        writing: formValue.writing,
      };

      this.tempList.push(languageProf);
    } else {
      this.languageProficiencyForm.markAllAsTouched();
    }
  }

  createOrModifyCommonProfileDraft() {
    if (this.languageProficiencyForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: LanguageProficiency[] = this.tempList.concat(
          this.tableList,
        );

        // Create an instance of CommonProfileDraft and assign Membership
        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: [],
          higherEducations: [],
          schoolEducations: [],
          memberships: [],
          languageProficiencies: mergedArray,
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
      this.languageProficiencyForm.markAllAsTouched();
    }
  }

  deleteLanguageProficiency(languageProficiency: LanguageProficiency) {
    // Assuming languageProficiency is the specific object you want to remove
    this.tableList = this.tableList.filter(
      (item) => item !== languageProficiency,
    );

    // Create an instance of CommonProfileDraft and assign LanguageProficiency
    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences: [],
      higherEducations: [],
      schoolEducations: [],
      memberships: [],
      languageProficiencies: this.tableList.length == 0 ? null : this.tableList,
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
          console.log('deleteMembership() >> ' + error.get);
        },
      );
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.tableList = [];
    this.tempList = [];

    this.draftService.findDraftByIdCandidate(idCandidate).subscribe(
      (data) => {
        if (data.data != null) {
          this.commonProfileDraft = data.data;

          console.log(this.commonProfileDraft);

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          this.commonProfileDraft.languageProficiencies.forEach(
            (languageProficiency: LanguageProficiency) => {
              const selectedLanguage = this.languages.find(
                (language: Language) =>
                  language.idLanguage == languageProficiency.idLanguage,
              );

              const spoken =
                Proficiency[
                  languageProficiency.spoken as keyof typeof Proficiency
                ];

              const reading =
                Proficiency[
                  languageProficiency.reading as keyof typeof Proficiency
                ];

              const writing =
                Proficiency[
                  languageProficiency.writing as keyof typeof Proficiency
                ];

              languageProficiency.languageName = selectedLanguage.languageName;
              languageProficiency.spoken = spoken;
              languageProficiency.writing = writing;
              languageProficiency.reading = reading;

              this.tableList.push(languageProficiency);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  private getAllActiveLanguages() {
    this.languageService.getAllActiveLanguages().subscribe(
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

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/research']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/membership']);
  }

  resetCommonProfileForm() {
    this.tempList = [];
    this.tableList = [];
    this.languageProficiencyForm.reset();
    this.languageProficiencyForm.get('spoken')?.patchValue('-1');
    this.languageProficiencyForm.get('reading')?.patchValue('-1');
    this.languageProficiencyForm.get('writing')?.patchValue('-1');
    this.languageProficiencyForm.get('idLanguage')?.patchValue('-1');
  }
}
