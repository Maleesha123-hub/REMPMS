import { createReducer, on } from '@ngrx/store';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import * as JobPositionActions from './job-position.actions';
import { state } from '@angular/animations';

export interface JobPositionState {
  jobPositionsResponse: CommonResponse | null;
  deleteJobPositionResponse: CommonResponse | null;
  savedJobPositionResponse: CommonResponse | null;
  activeIndustriesResponse: CommonResponse | null;
  error: any;
  loading: boolean;
}

export const initialJobPositionState: JobPositionState = {
  jobPositionsResponse: null,
  deleteJobPositionResponse: null,
  savedJobPositionResponse: null,
  activeIndustriesResponse: null,
  error: null,
  loading: false,
};

export const jobPositionReducer = createReducer(
  initialJobPositionState,
  // Handle the action when save or modifying job position.
  on(JobPositionActions.saveOrModifyJobPosition, (state) => ({
    ...state,
    loading: true,
    savedJobPositionResponse: null,
    error: null,
  })),
  // Handle the action when save or modifying job position success.
  on(
    JobPositionActions.saveOrModifyJobPositionSuccess,
    (state, { commonResponse }) => ({
      ...state,
      loading: false,
      savedJobPositionResponse: commonResponse,
      error: null,
    })
  ),
  // Handle the action when save or modifying job position failure.
  on(JobPositionActions.saveOrModifyJobPositionFailure, (state, { error }) => ({
    ...state,
    loading: false,
    savedJobPositionResponse: null,
    error: error,
  })),
  // Handle the action when delete a job position
  on(JobPositionActions.deleteJobPosition, (state, { id }) => ({
    ...state,
    loading: true,
    deleteJobPositionResponse: null,
    error: null,
  })),
  // Handle the action when delete a job position success
  on(
    JobPositionActions.deleteJobPositionSuccess,
    (state, { commonResponse }) => ({
      ...state,
      loading: false,
      deleteJobPositionResponse: commonResponse,
      error: null,
    })
  ),
  // Handle the action when delete a job position failure
  on(JobPositionActions.deleteJobPositionFailure, (state, { error }) => ({
    ...state,
    loading: false,
    deleteJobPositionResponse: null,
    error: error,
  })),
  // Handle the action when loading job positions
  on(JobPositionActions.getAllJobPositions, (state) => ({
    ...state,
    loading: true,
    jobPositionsResponse: null,
    error: null,
  })),
  // Handle the action when loading job positions success
  on(JobPositionActions.getAllJobPositionSuccess, (state, { response }) => ({
    ...state,
    loading: false,
    jobPositionsResponse: response,
    error: null,
  })),
  // Handle the action when loading job positions failure
  on(JobPositionActions.getAllJobPositionFailure, (state, { error }) => ({
    ...state,
    loading: false,
    jobPositionsResponse: null,
    error: error,
  })),
  // Handle the action when loading active industries
  on(JobPositionActions.getAllActiveIndustries, (state) => ({
    ...state,
    loading: true,
    activeIndustriesResponse: null,
    error: null,
  })),
  // Handle the action when loading active industries success
  on(
    JobPositionActions.getAllActiveIndustriesSuccess,
    (state, { response }) => ({
      ...state,
      loading: false,
      activeIndustriesResponse: response,
      error: null,
    })
  ),
  // Handle the action when loading active industries failure
  on(JobPositionActions.getActiveIndustriesFailure, (state, { error }) => ({
    ...state,
    loading: false,
    activeIndustriesResponse: null,
    error: error,
  }))
);
