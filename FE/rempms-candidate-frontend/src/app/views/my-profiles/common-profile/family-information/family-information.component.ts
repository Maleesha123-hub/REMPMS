import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { FamilyInformation } from '../../../../model/candidate/familyInformation/FamilyInformation';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-family-information',
  templateUrl: './family-information.component.html',
  styleUrl: './family-information.component.scss',
})
export class FamilyInformationComponent implements OnInit {
  familyInformationForm: FormGroup | any;
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  showUpdateButton = false;
  showSaveButton = true;
  tempList: FamilyInformation[] = [];
  tableList: FamilyInformation[] = [];
  familyInformation: FamilyInformation[] = [];
  emptyListErrorVisible = false;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.familyInformationForm = this.formBuilder.group({
      idFamilyInformation: [''],
      name: ['', Validators.required],
      relationship: ['', Validators.required],
      designation: ['', Validators.required],
      dob: ['', Validators.required],
      gender: ['', Validators.required],
      schoolOrOrganization: ['', Validators.required],
      remark: ['', Validators.required],
      isDependent: ['', Validators.required],
    });
  }

  removeFamilyInformationItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  familyInformationTempListTable() {
    if (this.familyInformationForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.familyInformationForm.value;

      const information: FamilyInformation = {
        idFamilyInformation: formValue.idFamilyInformation,
        name: formValue.name,
        relationship: formValue.relationship,
        designation: formValue.designation,
        dob: formValue.dob,
        gender: formValue.gender,
        schoolOrOrganization: formValue.schoolOrOrganization,
        remark: formValue.remark,
        isDependant: formValue.isDependent,
      };

      this.tempList.push(information);
    } else {
      this.familyInformationForm.markAllAsTouched();
    }
  }

  deleteFamilyInformation(information: FamilyInformation) {
    this.tableList = this.tableList.filter((item) => item !== information);

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
      familyInformation: this.tableList.length == 0 ? null : this.tableList,
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
          console.log('deleteReferee() >> ' + error.get);
        },
      );
  }

  createOrModifyCommonProfileDraft() {
    if (this.familyInformationForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: FamilyInformation[] = this.tempList.concat(
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
          familyInformation: mergedArray,
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
      this.familyInformationForm.markAllAsTouched();
    }
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

          this.commonProfileDraft.familyInformation.forEach(
            (information: FamilyInformation) => {
              this.tableList.push(information);
            },
          );
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  resetCommonProfileForm() {
    this.tempList = [];
    this.tableList = [];
    this.familyInformationForm.reset();
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'job-preferences' route
    this.router.navigate(['/common-profile/job-preferences']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'referees' route
    this.router.navigate(['/common-profile/referees']);
  }
}
