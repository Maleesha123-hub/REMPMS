import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobVacancyResponse } from 'src/app/model/jobVacancy/JobVacancyResponse';
import { RecievedCvsResponse } from '../../model/recievedCvs/RecievedCvsResponse';
import { HandleErrors } from 'src/app/model/errors/handle-errors';
// import * as RecievdedCvsActions from './store/recieved-cvs.actions';
import {
  selectJobVacanciesDetails,
  selectJobVacancyErrorResponse,
} from '../job-vacancy/store/job.vacancy.selector';
import {
  selectRecievedCvsDetails,
  selectRecievedCvsErrorResponse,
} from './store/recieved-cvs.selector';
import * as JobVacancyActions from '../job-vacancy/store/job.vacancy.actions';
import * as RecievdedCvsActions from '../recieved-cvs/store/recieved-cvs.actions';
import * as RecievdedCvsSelectors from '../recieved-cvs/store/recieved-cvs.selector';
import { Observable, Subject, takeUntil } from 'rxjs';
import { JobVacancyState } from '../job-vacancy/store/job.vacancy.reducer';
import { Store, select } from '@ngrx/store';
import Swal from 'sweetalert2';
import { RecievedCvsState } from './store/recieved-cvs.reducer';

@Component({
  selector: 'app-recieved-cvs',
  templateUrl: './recieved-cvs.component.html',
  styleUrls: ['./recieved-cvs.component.scss'],
})
export class RecievedCvsComponent {
  receivedCvsForm: FormGroup | any;
  public jobVacancies: JobVacancyResponse[] = [];
  private unsubscribe$ = new Subject<void>();
  private error$!: Observable<any>;
  // Observables for job vacancy.
  private selectJobVacanciesDetails$!: Observable<any>;

  constructor(
    private formBuilder: FormBuilder,
    private jobVacancyStore: Store<{ jobVacancy: JobVacancyState }>,
    private recivedCvsStore: Store<{ recievedCvsState: RecievedCvsState }>
  ) {}

  ngOnInit(): void {
    this.receivedCvsForm = this.formBuilder.group({
      jobRefNo: [''],
      position: ['-1'],
      employer: ['-1'],
    });
    this.getAllJobVacancies();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  getRecievedCvsById(id: number) {
    this.recivedCvsStore.dispatch(
      RecievdedCvsActions.loadRecievedCvsByVacancyId({ id })
    );
  }

  getAllJobVacancies() {
    this.jobVacancyStore.dispatch(JobVacancyActions.loadJobVacancies());
    this.selectJobVacanciesDetails$ = this.jobVacancyStore.select(
      selectJobVacanciesDetails
    );
    this.selectJobVacanciesDetails$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          this.jobVacancies = state.data;
          //this.resetForm();
        } else {
          this.handleErrorOfJobVacancy();
        }
      });
  }

  handleErrorOfJobVacancy(): void {
    this.error$ = this.jobVacancyStore.pipe(
      select(selectJobVacancyErrorResponse)
    );
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

  handleErrorOfRecievedCvs(): void {
    this.error$ = this.recivedCvsStore.pipe(
      select(selectRecievedCvsErrorResponse)
    );
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
    this.receivedCvsForm.reset();
    const updateButton = document.querySelector(
      'input[value="Update"]'
    ) as HTMLInputElement;
    if (updateButton) {
      updateButton.value = 'Create';
    }
    this.getAllJobVacancies();
  }
}
