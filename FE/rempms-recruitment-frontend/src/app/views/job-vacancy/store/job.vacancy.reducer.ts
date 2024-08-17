import { createReducer, on } from '@ngrx/store';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import * as JobVacancyActions from './job.vacancy.actions';

export interface JobVacancyState {
  jobVacanciesResponse: CommonResponse | null;
  jobVacancySavedOrModifiedResponse: CommonResponse | null;
  jobVacancyDeletedResponse: CommonResponse | null;
  error: any;
  loading: boolean;
}

export const initialJobVacancyState: JobVacancyState = {
  jobVacanciesResponse: null,
  jobVacancySavedOrModifiedResponse: null,
  jobVacancyDeletedResponse: null,
  error: null,
  loading: false,
};

export const jobVacancyReducer = createReducer(
  initialJobVacancyState,
  // Handles the action to get job vacancy details.
  on(JobVacancyActions.loadJobVacancies, (state) => ({
    ...state,
    jobVacanciesResponse: null,
    jobVacancySavedOrModifiedResponse: null,
    jobVacancyDeletedResponse: null,
    posterImageResponse: null,
    error: null,
    loading: true,
  })),
  // Handles the action when get job vacancy details success.
  on(
    JobVacancyActions.loadJobVacanciesSuccess,
    (state, { commonResponse }) => ({
      ...state,
      jobVacanciesResponse: commonResponse,
      jobVacancySavedOrModifiedResponse: null,
      jobVacancyDeletedResponse: null,
      posterImageResponse: null,
      error: null,
      loading: false,
    })
  ),
  // Handles the action when get job vacancy details fails.
  on(JobVacancyActions.loadJobVacanciesFailure, (state, { error }) => ({
    ...state,
    jobVacanciesResponse: null,
    jobVacancySavedOrModifiedResponse: null,
    jobVacancyDeletedResponse: null,
    posterImageResponse: null,
    error: error,
    loading: false,
  })),
  // Handles the action to delete job vacancy.
  on(JobVacancyActions.deleteJobVacancy, (state) => ({
    ...state,
    jobVacanciesResponse: null,
    jobVacancySavedOrModifiedResponse: null,
    jobVacancyDeletedResponse: null,
    posterImageResponse: null,
    error: null,
    loading: false,
  })),
  // Handles the action when job vacancy delete success.
  on(
    JobVacancyActions.deleteJobVacancySuccess,
    (state, { commonResponse }) => ({
      ...state,
      jobVacanciesResponse: null,
      jobVacancySavedOrModifiedResponse: null,
      jobVacancyDeletedResponse: commonResponse,
      posterImageResponse: null,
      error: null,
      loading: false,
    })
  ),
  // Handles the action when job vacancy delete fails.
  on(JobVacancyActions.deleteJobVacancyFailure, (state, { error }) => ({
    ...state,
    jobVacanciesResponse: null,
    jobVacancySavedOrModifiedResponse: null,
    jobVacancyDeletedResponse: null,
    posterImageResponse: null,
    error: error,
    loading: false,
  })),
  // Handles the action to save or modify job vacancy.
  on(JobVacancyActions.saveOrModifyJobVacancy, (state) => ({
    ...state,
    jobVacanciesResponse: null,
    jobVacancySavedOrModifiedResponse: null,
    jobVacancyDeletedResponse: null,
    posterImageResponse: null,
    error: null,
    loading: false,
  })),
  // Handles the action when job vacancy save or modify success.
  on(
    JobVacancyActions.saveOrModifyJobVacancySuccess,
    (state, { commonResponse }) => ({
      ...state,
      jobVacanciesResponse: null,
      jobVacancySavedOrModifiedResponse: commonResponse,
      jobVacancyDeletedResponse: null,
      posterImageResponse: null,
      error: null,
      loading: false,
    })
  ),
  // Handles the action when job vacancy save or modify fails.
  on(JobVacancyActions.saveOrModifyJobVacancyFailure, (state, { error }) => ({
    ...state,
    jobVacanciesResponse: null,
    jobVacancySavedOrModifiedResponse: null,
    jobVacancyDeletedResponse: null,
    posterImageResponse: null,
    error: error,
    loading: false,
  }))
);
