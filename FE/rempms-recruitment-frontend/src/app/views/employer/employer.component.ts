import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, Subject, takeUntil } from 'rxjs';
import { Employer } from '../../model/employer/Employer';
import { Store, select } from '@ngrx/store';
import * as EmployersActions from './store/employer.actions';
import {
  selectEmployerDeletedResponseData,
  selectEmployerErrorResponseData,
  selectEmployerSavedOrModifiedResponseData,
  selectEmployersData,
} from './store/employer.selector';
import { EmployerState } from './store/employer.reducer';
import Swal from 'sweetalert2';
import { EmployerSaveLazyResponse } from '../../model/employer/EmployerSaveLazyResponse';
import { HandleErrors } from '../../model/errors/handle-errors';

/**
 * @author @Maleesha99
 * @Date 2024/06/12
 */
@Component({
  selector: 'app-employer',
  templateUrl: './employer.component.html',
  styleUrls: ['./employer.component.scss'],
})
export class EmployerComponent implements OnInit, OnDestroy {
  employerForm: FormGroup | any;
  public employers: Employer[] = [];
  private unsubscribe$ = new Subject<void>();
  // Observables of employer state data
  private employersResponse$!: Observable<any>;
  private employerSavedOrModifiedResponse$!: Observable<any>;
  private employerDeletedResponse$!: Observable<any>;
  private error$!: Observable<any>;

  // constructor() is the default method, which is initialized components, sub classes, methods when instantiating the class.
  // Called first time before the ngOnInit().
  constructor(
    private formBuilder: FormBuilder,
    private store: Store<{ employer: EmployerState }>,
  ) {}

  // ngOninit() is a life cycle hook in angular.
  // It is not mandatory, but consider as a best practise.
  // Called after the constructor and called  after the first ngOnChanges().
  ngOnInit(): void {
    this.employerForm = this.formBuilder.group({
      employerId: [''],
      employerName: ['', Validators.required],
      employerAddress: ['', Validators.required],
      tpNo: ['', Validators.required],
      mobileNo: ['', Validators.required],
      employerNo: [''],
    });
    this.getAllEmployers();
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  /**
   * This method is allowed to get employers data from initial state.
   * And set it to an array to load the table.
   * @author @Maleesha99
   */
  getAllEmployers(): void {
    this.store.dispatch(EmployersActions.loadEmployers());
    this.employersResponse$ = this.store.pipe(select(selectEmployersData));
    this.employersResponse$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          this.employers = state.data;
        } else {
          this.handleError();
        }
      });
  }

  /**
   * This method is allowed to set employer data to the employer form by id
   * @param id {@link number} - employer id
   * @author @Maleesha99
   */
  getEmployerById(id: number) {
    const emp = this.employers.find((emp) => emp.id === id);
    if (emp) {
      this.employerForm.patchValue({
        employerId: emp.id,
        employerName: emp.employerName,
        employerAddress: emp.address,
        tpNo: emp.telephoneNo,
        mobileNo: emp.mobileNo,
        employerNo: emp.employerNo == null ? '-' : emp.employerNo,
      });
    }
  }

  saveOrModifyEmployer() {
    if (this.employerForm.valid) {
      const formValue = this.employerForm.value;
      const employer: Employer = {
        id: formValue.employerId,
        employerName: formValue.employerName,
        address: formValue.employerAddress,
        telephoneNo: formValue.tpNo,
        mobileNo: formValue.mobileNo,
        employerNo: formValue.employerNo == '-' ? null : formValue.employerNo,
      };
      this.store.dispatch(EmployersActions.saveOrModifyEmployer({ employer }));
      this.employerSavedOrModifiedResponse$ = this.store.pipe(
        select(selectEmployerSavedOrModifiedResponseData),
      );
      this.employerSavedOrModifiedResponse$
        .pipe(takeUntil(this.unsubscribe$))
        .subscribe((state) => {
          if (state) {
            const message: string = state.message;
            const res: EmployerSaveLazyResponse = state.data;
            Swal.fire({
              title: 'Saved!',
              text: message + ' Employer Name: ' + res.employerName,
              icon: 'success',
            });
            this.resetForm();
          } else {
            this.handleError();
          }
        });
    } else {
      this.employerForm.markAllAsTouched();
    }
  }

  deleteById(id: number): void {
    this.store.dispatch(EmployersActions.deleteEmployer({ id }));
    this.employerDeletedResponse$ = this.store.pipe(
      select(selectEmployerDeletedResponseData),
    );
    this.employerDeletedResponse$
      .pipe(takeUntil(this.unsubscribe$))
      .subscribe((state) => {
        if (state) {
          const message: string = state.message;
          Swal.fire({
            title: 'Deleted!',
            text: message,
            icon: 'success',
          });
          this.resetForm();
        } else {
          this.handleError();
        }
      });
  }

  handleError(): void {
    this.error$ = this.store.pipe(select(selectEmployerErrorResponseData));
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

  // This method is allowed to reset the employer form and change the 'Update' btn name as 'Create'.
  resetForm(): void {
    this.employerForm.reset();
    const updateButton = document.querySelector(
      'input[value="Update"]',
    ) as HTMLInputElement;
    if (updateButton) {
      updateButton.value = 'Create';
    }
    this.getAllEmployers();
  }
}
