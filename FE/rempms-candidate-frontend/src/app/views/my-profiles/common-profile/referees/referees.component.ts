import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import Swal from 'sweetalert2';
import { Referee } from '../../../../model/candidate/referee/Referee';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';

@Component({
  selector: 'app-referees',
  templateUrl: './referees.component.html',
  styleUrl: './referees.component.scss',
})
export class RefereesComponent implements OnInit {
  refereeForm: FormGroup | any;
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  showUpdateButton = false;
  showSaveButton = true;
  tempList: Referee[] = [];
  tableList: Referee[] = [];
  referees: Referee[] = [];
  emptyListErrorVisible = false;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.refereeForm = this.formBuilder.group({
      idReferee: [''],
      name: ['', Validators.required],
      relationship: ['', Validators.required],
      designation: ['', Validators.required],
      phone: ['', Validators.required],
      email: ['', Validators.required],
      address: ['', Validators.required],
    });
  }

  refereeTempListTable() {
    if (this.refereeForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.refereeForm.value;

      const referee: Referee = {
        idReferee: formValue.idReferee,
        name: formValue.name,
        relationship: formValue.relationship,
        designation: formValue.designation,
        phone: formValue.phone,
        email: formValue.email,
        address: formValue.address,
      };

      this.tempList.push(referee);
    } else {
      this.refereeForm.markAllAsTouched();
    }
  }

  removeTempRefereeItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  deleteReferee(referee: Referee) {
    this.tableList = this.tableList.filter((item) => item !== referee);

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
      referees: this.tableList.length == 0 ? null : this.tableList,
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
          console.log('deleteReferee() >> ' + error.get);
        },
      );
  }

  createOrModifyCommonProfileDraft() {
    if (this.refereeForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: Referee[] = this.tempList.concat(this.tableList);

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
          referees: mergedArray,
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
      this.refereeForm.markAllAsTouched();
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

          this.commonProfileDraft.referees.forEach((referee: Referee) => {
            this.tableList.push(referee);
          });
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
    this.refereeForm.reset();
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'family-information' route
    this.router.navigate(['/common-profile/family-information']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'achievements' route
    this.router.navigate(['/common-profile/achievements']);
  }
}
