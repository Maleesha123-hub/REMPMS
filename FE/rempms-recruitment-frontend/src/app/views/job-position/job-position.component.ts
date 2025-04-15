import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobPositionState } from './store/job-position.reducer';
import * as JobPositionActions from '../job-position/store/job-position.actions';
import { JobPositionResponse } from '../../model/jobPosition/JobPositionResponse';
import { JobPositionRequest } from '../../model/jobPosition/JobPositionRequest';
import { Observable, Subject, takeUntil } from 'rxjs';
import {
  selectActiveIndustries,
  selectJobPositionErrorResponse,
  selectJobPositionsDetails,
} from './store/job-position.selector';
import { selectJobPositionDeletedResponse } from './store/job-position.selector';
import { selectJobPositionSavedResponse } from './store/job-position.selector';
import { Store, select } from '@ngrx/store';
import Swal from 'sweetalert2';
import { Industry } from '../../model/industry/Industry';
import { HandleErrors } from '../../model/errors/handle-errors';

@Component({
  selector: 'app-job-position',
  templateUrl: './job-position.component.html',
  styleUrls: ['./job-position.component.scss'],
})
export class JobPositionComponent implements OnInit {
  public jobPositionForm: FormGroup | any;
  public jobPositions: JobPositionResponse[] = [];
  public industries: Industry[] = [];
  private message: string = '';
  private unsubscribe$ = new Subject<void>();
  private jobPositionsResponseDetails$!: Observable<any>;
  private jobPositionSavedResponseDetails$!: Observable<any>;
  private jobPositionDeletedResponseDetails$!: Observable<any>;
  private error$!: Observable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private store: Store<{ jobPosition: JobPositionState }>,
  ) {}

  ngOnInit(): void {
    this.jobPositionForm = this.formBuilder.group({
      jobPositionId: [''],
      position: ['', Validators.required],
      industryId: ['-1', Validators.required],
    });
    this.getAllJobPositions();
    this.getAllActiveIndustries();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  getAllJobPositions(): void {
    this.store.dispatch(JobPositionActions.getAllJobPositions());
    this.jobPositionsResponseDetails$ = this.store.pipe(
      select(selectJobPositionsDetails),
    );
    this.jobPositionsResponseDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (initialState) => {
          if (initialState) {
            if (initialState.data != null) {
              this.jobPositions = initialState.data;
            }
          } else {
            console.warn('Job positions are null or undefined');
          }
        },
        (error) => {
          Swal.fire({
            title: 'Error!',
            text: 'Something went wrong while loading the job positions.',
            icon: 'error',
            confirmButtonText: 'OK',
          });
          console.warn('Error loading job positions', error);
        },
      );
  }

  getAllActiveIndustries(): void {
    this.store.dispatch(JobPositionActions.getAllActiveIndustries());
    this.jobPositionsResponseDetails$ = this.store.pipe(
      select(selectActiveIndustries),
    );
    this.jobPositionsResponseDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (initialState) => {
          if (initialState) {
            if (initialState.data != null) {
              this.industries = initialState.data;
            }
          } else {
            console.warn('Industries are null or undefined');
          }
        },
        (error) => {
          Swal.fire({
            title: 'Error!',
            text: 'Something went wrong while loading the industries.',
            icon: 'error',
            confirmButtonText: 'OK',
          });
          console.warn('Error loading industries', error);
        },
      );
  }

  getJobPositionById(id: number) {
    const jp = this.jobPositions.find((jp) => jp.id === id);
    if (jp) {
      this.jobPositionForm.patchValue({
        jobPositionId: jp.id,
        position: jp.name,
        industryId: jp.industryId,
      });
    }
  }

  saveOrModifyJobPosition() {
    if (this.jobPositionForm.valid) {
      const formValue = this.jobPositionForm.value;
      const jobPosition: JobPositionRequest = {
        id: formValue.jobPositionId,
        name: formValue.position,
        industryId: formValue.industryId,
      };
      this.store.dispatch(
        JobPositionActions.saveOrModifyJobPosition({ jobPosition }),
      );
      this.jobPositionSavedResponseDetails$ = this.store.pipe(
        select(selectJobPositionSavedResponse),
      );
      this.jobPositionSavedResponseDetails$
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe(
          (initialState) => {
            if (initialState) {
              this.message = initialState.message;
              const res: JobPositionRequest = initialState.data;
              Swal.fire({
                title: 'Saved!',
                text: this.message + ' Job Position Name: ' + res.name,
                icon: 'success',
              });
              this.resetForm();
            } else {
              console.warn('jobPositionState is null or undefined');
            }
          },
          (error) => {
            Swal.fire({
              title: 'Error!',
              text: 'Something went wrong while saving the job position.',
              icon: 'error',
              confirmButtonText: 'OK',
            });
            console.warn('Error saving the job position', error);
          },
        );
    } else {
      this.jobPositionForm.markAllAsTouched();
    }
  }

  deleteById(id: number): void {
    this.store.dispatch(JobPositionActions.deleteJobPosition({ id }));
    this.jobPositionDeletedResponseDetails$ = this.store.pipe(
      select(selectJobPositionDeletedResponse),
    );
    this.jobPositionDeletedResponseDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe(
        (initialState) => {
          if (initialState) {
            this.message = initialState.message;
            Swal.fire({
              title: 'Deleted!',
              text: this.message,
              icon: 'success',
            });
            this.resetForm();
          } else {
            this.handleError();
          }
        },
        (error) => {
          Swal.fire({
            title: 'Error!',
            text:
              'Something went wrong while deleting a job position, ' + error,
            icon: 'error',
            confirmButtonText: 'OK',
          });
          console.warn('Error deleting a job position', error);
        },
      );
  }

  handleError(): void {
    this.error$ = this.store.pipe(select(selectJobPositionErrorResponse));
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
      }
    });
  }

  // This method is allowed to change the 'Create' btn name as 'Update.
  onCreateClick(): void {
    const createButton = document.querySelector(
      'input[value="Create"]',
    ) as HTMLInputElement;
    if (createButton) {
      createButton.value = 'Update';
    }
  }

  // This method is allowed to reset the job position form and change the 'Update' btn name as 'Create'.
  resetForm(): void {
    this.jobPositionForm.reset();
    const updateButton = document.querySelector(
      'input[value="Update"]',
    ) as HTMLInputElement;
    if (updateButton) {
      updateButton.value = 'Create';
    }
    this.getAllJobPositions();
    this.getAllActiveIndustries();
  }
}
