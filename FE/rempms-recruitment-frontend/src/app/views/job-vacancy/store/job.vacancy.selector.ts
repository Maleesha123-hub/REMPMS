import { createFeatureSelector, createSelector } from '@ngrx/store';
import { JobVacancyState } from './job.vacancy.reducer';

export const selectJobVacancyState =
  createFeatureSelector<JobVacancyState>('jobVacancyState');

export const selectJobVacanciesDetails = createSelector(
  selectJobVacancyState,
  (state: JobVacancyState) => state.jobVacanciesResponse
);

export const selectJobVacancySavedResponse = createSelector(
  selectJobVacancyState,
  (state: JobVacancyState) => state.jobVacancySavedOrModifiedResponse
);

export const selectJobVacancyDeletedResponse = createSelector(
  selectJobVacancyState,
  (state: JobVacancyState) => state.jobVacancyDeletedResponse
);

export const selectJobVacancyErrorResponse = createSelector(
  selectJobVacancyState,
  (state: JobVacancyState) => state.error
);
