import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import Swal from 'sweetalert2';
import { Achievement } from '../../../../model/candidate/achievments/Achievement';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import { Research } from '../../../../model/candidate/research/Research';

@Component({
  selector: 'app-achievements',
  templateUrl: './achievements.component.html',
  styleUrl: './achievements.component.scss',
})
export class AchievementsComponent implements OnInit {
  files: File[] = [];
  achievementForm: FormGroup | any;
  idCandidate: string | any;
  idDraft: string | any;
  commonProfileDraft: CommonProfileDraft | any;
  showUpdateButton = false;
  showSaveButton = true;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private draftService: CommonProfileDraftService,
  ) {}

  ngOnInit(): void {
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.achievementForm = this.formBuilder.group({
      idAchievement: [''],
      achievement: ['', Validators.required],
    });
  }

  createOrModifyCommonProfileDraft() {
    if (this.achievementForm.valid) {
      const formValue = this.achievementForm.value;

      const achievement: Achievement = {
        idAchievement: formValue.idAchievement,
        achievements: formValue.achievement,
      };

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
        achievements: achievement,
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

              //this.resetCommonProfileForm();
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
    } else {
      this.achievementForm.markAllAsTouched();
    }
  }

  findDraftByIdCandidate(idCandidate: string) {
    this.draftService.findDraftByIdCandidate(idCandidate).subscribe(
      (data) => {
        if (data.data != null) {
          this.commonProfileDraft = data.data;

          console.log(this.commonProfileDraft);

          this.idDraft = this.commonProfileDraft.id;
          this.idCandidate = this.commonProfileDraft.idCandidate;

          this.achievementForm.patchValue({
            achievement:
              this.commonProfileDraft.achievements != null
                ? this.commonProfileDraft.achievements.achievements
                : null,
          });

          this.achievementForm.patchValue({
            idAchievement:
              this.commonProfileDraft.achievements != null
                ? this.commonProfileDraft.achievements.idAchievement
                : null,
          });
        }
      },
      (error) => {
        console.log('findDraftByIdCandidate() >> ' + error.get);
      },
    );
  }

  // resetCommonProfileForm() {
  //
  //   this.achievementForm.reset();
  //
  // }

  redirectToNext() {
    // Use the Router service to navigate to the 'referees' route
    this.router.navigate(['/common-profile/referees']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'research' route
    this.router.navigate(['/common-profile/research']);
  }
}
