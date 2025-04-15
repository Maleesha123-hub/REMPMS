import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobCategoryService } from '../../../../service/jobCategory/jobCategory/job-category.service';
import { IndustryService } from '../../../../service/industry/industry.service';
import { JobCategory } from '../../../../model/candidate/jobCategory/JobCategory';
import { Industry } from '../../../../model/candidate/industry/Industry';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { JobPreference } from '../../../../model/candidate/jobPreference/JobPreference';
import Swal from 'sweetalert2';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';

@Component({
  selector: 'app-job-preferences',
  templateUrl: './job-preferences.component.html',
  styleUrl: './job-preferences.component.scss',
})
export class JobPreferencesComponent implements OnInit {
  jobPreferenceForm: FormGroup | any;
  jobCategories: JobCategory[] | any;
  industries: Industry[] | any;
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  showUpdateButton = false;
  showSaveButton = true;
  tempList: JobPreference[] = [];
  tableList: JobPreference[] = [];
  jobPreferences: JobPreference[] = [];
  emptyListErrorVisible = false;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private jobCategoryService: JobCategoryService,
    private industryService: IndustryService,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveJobCategories();
    this.getAllActiveIndustries();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.jobPreferenceForm = this.formBuilder.group({
      idJobPreference: [''],
      idIndustry: ['-1', Validators.required],
      preference: ['-1', Validators.required],
      idJobCategory: ['-1', Validators.required],
      remark: ['', Validators.required],
    });
  }

  removeJobPreferenceItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  createOrModifyCommonProfileDraft() {
    if (this.jobPreferenceForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: JobPreference[] = this.tempList.concat(
          this.tableList,
        );

        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: [],
          higherEducations: [],
          schoolEducations: [],
          memberships: [],
          languageProficiencies: [],
          researches: [],
          achievements: null,
          referees: [],
          familyInformation: [],
          jobPreferences: mergedArray,
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
      this.jobPreferenceForm.markAllAsTouched();
    }
  }

  deleteJobPreference(jobPreference: JobPreference) {
    this.tableList = this.tableList.filter((item) => item !== jobPreference);

    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences: [],
      higherEducations: [],
      schoolEducations: [],
      memberships: [],
      languageProficiencies: [],
      researches: [],
      achievements: null,
      referees: [],
      familyInformation: [],
      jobPreferences: this.tableList.length == 0 ? null : this.tableList,
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
          console.log('deleteJobPreference() >> ' + error.get);
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

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          this.commonProfileDraft.jobPreferences.forEach(
            (jobPreference: JobPreference) => {
              const selectedIndustry = this.industries.find(
                (industry: Industry) => industry.id == jobPreference.idIndustry,
              );

              const selectedJobCategory = this.jobCategories.find(
                (jobCategory: JobCategory) =>
                  jobCategory.idJobCategory == jobPreference.idJobCategory,
              );

              jobPreference.industry = selectedIndustry.name;
              jobPreference.jobCategory = selectedJobCategory.jobCategoryName;
              this.tableList.push(jobPreference);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  jobPreferenceTempListTable() {
    if (this.jobPreferenceForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.jobPreferenceForm.value;

      const selectedIndustry = this.industries.find(
        (industry: Industry) => industry.id == formValue.idIndustry,
      );

      const selectedJobCategory = this.jobCategories.find(
        (jobCategory: JobCategory) =>
          jobCategory.idJobCategory == formValue.idJobCategory,
      );

      const jobPreference: JobPreference = {
        idJobPreference: formValue.idJobPreference,
        idIndustry: formValue.idIndustry,
        preference: formValue.preference,
        idJobCategory: formValue.idJobCategory,
        remark: formValue.remark,
        jobCategory: selectedJobCategory.jobCategoryName,
        industry: selectedIndustry.name,
      };

      this.tempList.push(jobPreference);
    } else {
      this.jobPreferenceForm.markAllAsTouched();
    }
  }

  private getAllActiveIndustries() {
    this.industryService.getAllActiveIndustries().subscribe(
      (data) => {
        if (data.data != null) {
          this.industries = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveIndustries() >> ' + error.get);
      },
    );
  }

  private getAllActiveJobCategories() {
    this.jobCategoryService.getAllActiveJobCategories().subscribe(
      (data) => {
        if (data.data != null) {
          this.jobCategories = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveJobCategories() >> ' + error.get);
      },
    );
  }

  resetCommonProfileForm() {
    this.tempList = [];
    this.tableList = [];
    this.jobPreferenceForm.reset();
    this.jobPreferenceForm.get('idIndustry')?.patchValue('-1');
    this.jobPreferenceForm.get('idJobCategory')?.patchValue('-1');
    this.jobPreferenceForm.get('preference')?.patchValue('-1');
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'preferred-job-location' route
    this.router.navigate(['/common-profile/preferred-job-location']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'family-information' route
    this.router.navigate(['/common-profile/family-information']);
  }
}
