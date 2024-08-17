import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { selectJobPositionsDetails } from '../job-position/store/job-position.selector';
import { selectEmployersData } from '../employer/store/employer.selector';
import {
  selectJobVacanciesDetails,
  selectJobVacancyErrorResponse,
  selectJobVacancySavedResponse,
} from '../job-vacancy/store/job.vacancy.selector';
import * as JobPositionActions from '../job-position/store/job-position.actions';
import * as JobVacancyActions from '../job-vacancy/store/job.vacancy.actions';
import * as EmployerActions from '../employer/store/employer.actions';
import { Store, select } from '@ngrx/store';
import { JobPositionState } from '../job-position/store/job-position.reducer';
import { EmployerState } from '../employer/store/employer.reducer';
import { Observable, Subject, catchError, map, takeUntil } from 'rxjs';
import { JobPositionResponse } from '../../model/jobPosition/JobPositionResponse';
import Swal from 'sweetalert2';
import { Employer } from '../../model/employer/Employer';
import { JobVacancyState } from './store/job.vacancy.reducer';
import { HandleErrors } from 'src/app/model/errors/handle-errors';
import { JobVacancyRequest } from '../../model/jobVacancy/JobVacancyRequest';
import { JobVacancySavedLazyResponse } from '../../model/jobVacancy/JobVacancySavedLazyResponse';
import { JobVacancyResponse } from '../../model/jobVacancy/JobVacancyResponse';
import { DocumentService } from 'src/app/service/documentService/document.service';

@Component({
  selector: 'app-job-vacancy',
  templateUrl: './job-vacancy.component.html',
  styleUrls: ['./job-vacancy.component.scss'],
})
export class JobVacancyComponent implements OnInit {
  public jobPositions: JobPositionResponse[] = [];
  public jobVacancies: JobVacancyResponse[] = [];
  public employers: Employer[] = [];
  private unsubscribe$ = new Subject<void>();
  private employersResponseDetails$!: Observable<any>;
  private jobPositionsResponseDetails$!: Observable<any>;
  private error$!: Observable<any>;
  // Observables for job vacancy.
  private selectJobVacanciesDetails$!: Observable<any>;
  private selectJobVacancySavedResponse$!: Observable<any>;
  private selectJobVacancyDeletedResponse$!: Observable<any>;
  private selectPosterImageResponse$!: Observable<any>;

  jobVacancyForm: FormGroup | any;
  imageSrc: any = 'assets/img/default_image.jpg';
  @ViewChild('fileInput') fileInputRef!: ElementRef;
  file: File | any;

  constructor(
    private formBuilder: FormBuilder,
    private store: Store<{ jobVacancy: JobVacancyState }>,
    private jobPositionStore: Store<{ jobPosition: JobPositionState }>,
    private employerStore: Store<{ employer: EmployerState }>,
    private documentService: DocumentService
  ) {}

  ngOnInit(): void {
    this.jobVacancyForm = this.formBuilder.group({
      jobVacancyId: [''],
      positionId: ['-1', Validators.required],
      employerId: ['-1', Validators.required],
      vacancyDescription: ['', Validators.required],
      closingDate: ['', Validators.required],
      govJob: [''],
      walksInInterview: [''],
      partTime: [''],
      jobPoster: ['', Validators.required],
    });
    this.getAllEmployers();
    this.getAllJobPositions();
    this.getAllJobVacancies();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  getAllJobPositions(): void {
    this.jobPositionStore.dispatch(JobPositionActions.getAllJobPositions());
    this.jobPositionsResponseDetails$ = this.jobPositionStore.pipe(
      select(selectJobPositionsDetails)
    );
    this.jobPositionsResponseDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          this.jobPositions = state.data;
        } else {
          this.handleError();
        }
      });
  }

  getAllEmployers(): void {
    this.employerStore.dispatch(EmployerActions.loadEmployers());
    this.employersResponseDetails$ = this.employerStore.pipe(
      select(selectEmployersData)
    );
    this.employersResponseDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          this.employers = state.data;
        } else {
          this.handleError();
        }
      });
  }

  saveOrModifyJobVacancy() {
    if (this.jobVacancyForm.valid) {
      const formValue = this.jobVacancyForm.value;
      const jobVacancy: JobVacancyRequest = {
        id: formValue.jobVacancyId,
        description: formValue.vacancyDescription,
        closingDate: formValue.closingDate,
        govtJob: formValue.govJob,
        walksInInterview: formValue.walksInInterview,
        partTime: formValue.partTime,
        employerId: formValue.employerId,
        jobPositionId: formValue.positionId,
      };

      const fileInput = <HTMLInputElement>document.getElementById('jobPoster');
      if (fileInput.files && fileInput.files.length > 0) {
        this.file = fileInput.files[0];
      }
      this.store.dispatch(
        JobVacancyActions.saveOrModifyJobVacancy({
          jobVacancy: jobVacancy,
          file: this.file,
        })
      );
      this.selectJobVacancySavedResponse$ = this.store.select(
        selectJobVacancySavedResponse
      );
      this.selectJobVacancySavedResponse$
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe((state) => {
          if (state) {
            const message: string = state.message;
            const res: JobVacancySavedLazyResponse = state.data;
            Swal.fire({
              title: 'Saved!',
              text: message + ' Reference No: ' + res.refNo,
              icon: 'success',
            });
            //this.resetForm();
          } else {
            this.handleError();
          }
        });
    } else {
      this.jobVacancyForm.markAllAsTouched();
    }
  }

  getAllJobVacancies() {
    this.store.dispatch(JobVacancyActions.loadJobVacancies());
    this.selectJobVacanciesDetails$ = this.store.select(
      selectJobVacanciesDetails
    );
    this.selectJobVacanciesDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          this.jobVacancies = state.data;
          //this.resetForm();
        } else {
          this.handleError();
        }
      });
  }

  getJobVacancyById(id: number) {
    const jv = this.jobVacancies.find((jv) => jv.id === id);
    if (jv) {
      this.jobVacancyForm.patchValue({
        jobVacancyId: jv.id,
        positionId: jv.jobPosition.id,
        employerId: jv.employer.id,
        vacancyDescription: jv.description,
        closingDate: jv.closingDate,
        govtJob: jv.govtJob,
        walksInInterview: jv.walksInInterview,
        partTime: jv.partTime,
      });
      this.downloadImage('JPEG', jv.posterUrl);
    }
  }

  downloadImage(type: string, path: string) {
    let paths: string[] = [];
    paths.push(path);
    this.documentService.downloadImage(type, paths).subscribe(
      (blob) => {
        this.displayImage(blob);
      },
      (error) => {
        Swal.fire({
          title: 'Error!',
          text: 'Document download fail.',
          icon: 'error',
          confirmButtonText: 'OK',
        });
      }
    );
  }

  // Display image in the place holder
  displayImage(blob: Blob) {
    const reader = new FileReader();
    reader.onload = () => {
      this.imageSrc = reader.result;
    };
    reader.readAsDataURL(blob);
  }

  handleError(): void {
    this.error$ = this.store.pipe(select(selectJobVacancyErrorResponse));
    this.error$.pipe(takeUntil(this.unsubscribe$)).subscribe((state) => {
      if (state) {
        const err: HandleErrors = state;
        // Checking for custom errors by developer
        if (err.error.httpStatus) {
          Swal.fire({
            title: 'Error!',
            text: err.error.message,
            icon: 'error',
            confirmButtonText: 'OK',
          });
          // Checking for default errors
        } else if (err.error.status) {
          Swal.fire({
            title: 'Error!',
            text: err.error.error,
            icon: 'error',
            confirmButtonText: 'OK',
          });
        }
      } else {
        console.log('state not view...');
      }
    });
  }

  resetForm(): void {
    this.jobVacancyForm.reset();
    this.imageSrc = 'assets/img/default_image.jpg';
    const updateButton = document.querySelector(
      'input[value="Update"]'
    ) as HTMLInputElement;
    if (updateButton) {
      updateButton.value = 'Create';
    }
    this.getAllEmployers();
    this.getAllJobPositions();
    this.getAllJobVacancies();
  }

  previewImage() {
    const fileInputElement = this.fileInputRef.nativeElement;
    const file = fileInputElement.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imageSrc = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }
}
