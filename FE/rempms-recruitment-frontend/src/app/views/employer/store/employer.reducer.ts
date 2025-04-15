import { createReducer, on } from '@ngrx/store';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import * as EmployersActions from './employer.actions';

/**
 * This is the interface representing the state structure for employers.
 * @author @maleeshasa
 */
export interface EmployerState {
  employersResponse: CommonResponse | null;
  employerSavedOrModifiedResponse: CommonResponse | null;
  employerDeletedResponse: CommonResponse | null;
  error: any;
  loading: boolean;
}

/**
 * Initial state for the employers interface
 * @author @maleeshasa
 */
export const initialEmployerState: EmployerState = {
  employersResponse: null,
  employerSavedOrModifiedResponse: null,
  employerDeletedResponse: null,
  error: null,
  loading: false,
};

/**
 * This is the reducer function to managing the state of employer.
 * It handles actions related to employer details, updating the state accordingly.
 * @author @maleeshasa
 */
export const employerReducer = createReducer(
  initialEmployerState,
  // Handles the action to get employer details
  on(EmployersActions.loadEmployers, (state) => ({
    ...state,
    loading: true,
    employersResponse: null,
    error: null,
  })),
  // Handles the action when fetching employer details is successful.
  on(EmployersActions.loadEmployersSuccess, (state, { response }) => ({
    ...state,
    loading: false,
    employersResponse: response,
    error: null,
  })),
  // Handles the action when fetching employer details fails.
  on(EmployersActions.loadEmployersFailure, (state, { error }) => ({
    ...state,
    loading: false,
    employersResponse: null,
    error: error,
  })),
  // Handle the action when save or modifying an employer.
  on(EmployersActions.saveOrModifyEmployer, (state) => ({
    ...state,
    loading: true,
    employerSavedOrModifiedResponse: null,
    error: null,
  })),
  // Handle the action when save or modifying an employer is successful.
  on(EmployersActions.saveOrModifyEmployerSuccess, (state, { response }) => ({
    ...state,
    loading: false,
    employerSavedOrModifiedResponse: response,
    error: null,
  })),
  // Handle the action when save or modifying an employer is fail.
  on(EmployersActions.saveOrModifyEmployerFailure, (state, { error }) => ({
    ...state,
    loading: false,
    employerSavedOrModifiedResponse: null,
    error: error,
  })),
  // Handle the action when delete an employer
  on(EmployersActions.deleteEmployer, (state, { id }) => ({
    ...state,
    loading: true,
    employerDeletedResponse: null,
    error: null,
  })),
  // Handle the action when delete an employer success
  on(EmployersActions.deleteEmployerSuccess, (state, { response }) => ({
    ...state,
    loading: false,
    employerDeletedResponse: response,
    error: null,
  })),
  // Handle the action when delete an employer failure
  on(EmployersActions.deleteEmployerFailure, (state, { error }) => ({
    ...state,
    loading: false,
    employerDeletedResponse: null,
    error: error,
  })),
);
