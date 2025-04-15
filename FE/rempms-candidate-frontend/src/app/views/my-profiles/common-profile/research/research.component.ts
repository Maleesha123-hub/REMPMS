import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonProfileDraft } from '../../../../model/candidate/commonProfileDraft/CommonProfileDraft';
import { Research } from '../../../../model/candidate/research/Research';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import { LocalStorageService } from '../../../../service/localStorage/local-storage.service';
import { ResearchAreaService } from '../../../../service/researchArea/research-area.service';
import { ResearchArea } from '../../../../model/candidate/researchArea/ResearchArea';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-research',
  templateUrl: './research.component.html',
  styleUrl: './research.component.scss',
})
export class ResearchComponent implements OnInit {
  researchAreaForm: FormGroup | any;
  tempList: Research[] = [];
  idCandidate: string | any;
  idDraft: string | any;
  tableList: Research[] = [];
  researchAreas: ResearchArea[] | any;
  commonProfileDraft: CommonProfileDraft | any;
  emptyListErrorVisible = false;
  showUpdateButton = false;
  showSaveButton = true;
  files: File[] = [];

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private draftService: CommonProfileDraftService,
    private researchAreaService: ResearchAreaService,
  ) {}

  ngOnInit(): void {
    this.getAllActiveResearchAreas();
    this.findDraftByIdCandidate(sessionStorage.getItem('idCandidate') ?? '{}');

    this.researchAreaForm = this.formBuilder.group({
      idResearch: [''],
      commencedDate: ['', Validators.required],
      completionDate: ['', Validators.required],
      description: ['', Validators.required],
      idResearchArea: ['-1', Validators.required],
    });
  }

  removeTempResearchItem(index: number) {
    this.tempList.splice(index, 1); // Remove the item at the specified index
  }

  deleteResearch(research: Research) {
    this.tableList = this.tableList.filter((item) => item !== research);

    const commonProfileDraft: CommonProfileDraft = {
      id: this.idDraft,
      idCandidate: this.idCandidate,
      personalDetail: null,
      professionalExperiences: [],
      higherEducations: [],
      schoolEducations: [],
      memberships: [],
      languageProficiencies: [],
      researches: this.tableList.length == 0 ? null : this.tableList,
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
          console.log('deleteResearch() >> ' + error.get);
        },
      );
  }

  researchForTempListTable() {
    if (this.researchAreaForm.valid) {
      this.emptyListErrorVisible = false;
      const formValue = this.researchAreaForm.value;

      const selectedIdResearchArea =
        this.researchAreaForm.get('idResearchArea')?.value;
      const selectedResearchArea =
        selectedIdResearchArea != '-1'
          ? this.researchAreas.find(
              (researchArea: ResearchArea) =>
                researchArea.id == selectedIdResearchArea,
            )
          : null;

      const research: Research = {
        idResearch: formValue.idResearch,
        commencedDate: formValue.commencedDate,
        completionDate: formValue.completionDate,
        description: formValue.description,
        researchArea:
          selectedResearchArea != null ? selectedResearchArea.name : null,
        idResearchArea: formValue.idResearchArea,
      };

      this.tempList.push(research);
    } else {
      this.researchAreaForm.markAllAsTouched();
    }
  }

  createOrModifyCommonProfileDraft() {
    if (this.researchAreaForm.valid) {
      if (this.tempList.length == 0) {
        this.emptyListErrorVisible = true;
      } else {
        this.emptyListErrorVisible = false;

        const mergedArray: Research[] = this.tempList.concat(this.tableList);

        const commonProfileDraft: CommonProfileDraft = {
          id: this.idDraft,
          idCandidate: this.idCandidate,
          personalDetail: null,
          professionalExperiences: [],
          higherEducations: [],
          schoolEducations: [],
          memberships: [],
          languageProficiencies: [],
          researches: mergedArray,
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
      this.researchAreaForm.markAllAsTouched();
    }
  }

  private getAllActiveResearchAreas() {
    this.researchAreaService.getAllActiveResearchAreas().subscribe(
      (data) => {
        if (data.data != null) {
          this.researchAreas = data.data;
        }
      },
      (error) => {
        console.log('getAllActiveResearchAreas() >> ' + error.get);
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

          this.commonProfileDraft.researches.forEach((research: Research) => {
            const selectedResearchArea = this.researchAreas.find(
              (researchArea: ResearchArea) =>
                researchArea.id == research.idResearchArea,
            );

            research.researchArea = selectedResearchArea.name;

            this.tableList.push(research);
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
    this.researchAreaForm.reset();
    this.researchAreaForm.get('idResearchArea')?.patchValue('-1');
  }

  redirectToNext() {
    // Use the Router service to navigate to the 'achievements' route
    this.router.navigate(['/common-profile/achievements']);
  }

  redirectToBack() {
    // Use the Router service to navigate to the 'language-proficiency' route
    this.router.navigate(['/common-profile/language-proficiency']);
  }
}
