import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProfessionalExperience } from '../../../../model/candidate/professionalExperience/ProfessionalExperience';
import { JobCategoryService } from '../../../../service/jobCategory/jobCategory/job-category.service';
import { JobCategory } from '../../../../model/candidate/jobCategory/JobCategory';
import { IndustryService } from '../../../../service/industry/industry.service';
import { Industry } from '../../../../model/candidate/industry/Industry';
import Swal from 'sweetalert2';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';

@Component({
  selector: 'app-professional-experiences',
  templateUrl: './professional-experiences.component.html',
  styleUrl: './professional-experiences.component.scss',
})
export class ProfessionalExperiencesComponent implements OnInit {
  professionalExperienceForm: FormGroup | any;
  tempList: ProfessionalExperience[] = []; // Initialize tempList as an empty array
  tableList: ProfessionalExperience[] = []; // Initialize tableList as an empty array
  jobCategories: JobCategory[] | any;
  industries: Industry[] | any;
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
    private jobCategoryService: JobCategoryService,
    private industryService: IndustryService,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveJobCategories();
    this.getAllActiveIndustries();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.professionalExperienceForm = this.formBuilder.group({
      idProfessionalExperience: [''],
      organization: ['', Validators.required],
      designation: ['', Validators.required],
      commencedDate: ['', Validators.required],
      completionDate: ['', Validators.required],
      description: ['', Validators.required],
      idIndustry: ['-1', Validators.required],
      idJobCategory: ['-1', Validators.required],
    });
  }

  professionalExperiencesForTempListTable() {
    if (this.professionalExperienceForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.professionalExperienceForm.value;

      // Mapping form values to ProfessionalExperience object
      const professionalExperience: ProfessionalExperience = {
        idProfessionalExperience: formValue.idProfessionalExperience,
        organization: formValue.organization,
        designation: formValue.designation,
        commencedDate: formValue.commencedDate,
        completionDate: formValue.completionDate,
        description: formValue.description,
        idIndustry: formValue.idIndustry,
        idJobCategory: formValue.idJobCategory,
      };

      // Add professional experience to tempList
      this.tempList.push(professionalExperience);
    } else {
      this.professionalExperienceForm.markAllAsTouched();
    }
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/higher-education']);
  }

  createOrModifyCommonProfileDraft() {
    if (this.professionalExperienceForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: ProfessionalExperience[] = this.tempList.concat(
          this.tableList,
        );

        // Create an instance of CommonProfileDraft and assign professionalExperience
        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: mergedArray, // Assuming professionalExperienceForm has the same structure as ProfessionalExperience
          higherEducations: [],
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
                this.tableList = [];
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
      this.professionalExperienceForm.markAllAsTouched();
    }
  }

  resetCommonProfileForm() {
    this.professionalExperienceForm.reset();
    this.professionalExperienceForm.get('idIndustry')?.patchValue('-1');
    this.professionalExperienceForm.get('idJobCategory')?.patchValue('-1');
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'higher-education' route
    this.router.navigate(['/common-profile/personal-details']);
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

  removeTempItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
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
          this.commonProfileDraft.professionalExperiences.forEach(
            (professionalExperience: ProfessionalExperience) => {
              this.tableList.push(professionalExperience);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  editProfessionalExperience(experience: ProfessionalExperience) {
    this.showUpdateButton = true;
    this.showSaveButton = false;
    this.tempList = [];

    this.professionalExperienceForm.patchValue({
      idProfessionalExperience: experience.idProfessionalExperience,
      organization: experience.organization,
      designation: experience.designation,
      commencedDate: experience.commencedDate,
      completionDate: experience.completionDate,
      description: experience.description,
      idIndustry: experience.idIndustry,
      idJobCategory: experience.idJobCategory,
    });
  }

  deleteProfessionalExperience(experience: ProfessionalExperience) {
    // Assuming experience is the specific object you want to remove
    this.tableList = this.tableList.filter((item) => item !== experience);

    // Create an instance of CommonProfileDraft and assign professionalExperience
    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences:
        this.tableList.length == 0 ? null : this.tableList,
      higherEducations: [],
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
          console.log('deleteProfessionalExperience() >> ' + error.get);
        },
      );
  }
}
