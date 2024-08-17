import { createFeatureSelector, createSelector } from '@ngrx/store';
import { JobPositionState } from './job-position.reducer';

export const selectJobPositionState =
  createFeatureSelector<JobPositionState>('jobPositionState');

export const selectJobPositionsDetails = createSelector(
  selectJobPositionState,
  (state: JobPositionState) => state.jobPositionsResponse
);

export const selectJobPositionDeletedResponse = createSelector(
  selectJobPositionState,
  (state: JobPositionState) => state.deleteJobPositionResponse
);

export const selectJobPositionSavedResponse = createSelector(
  selectJobPositionState,
  (state: JobPositionState) => state.savedJobPositionResponse
);

export const selectActiveIndustries = createSelector(
  selectJobPositionState,
  (state: JobPositionState) => state.activeIndustriesResponse
);

export const selectJobPositionErrorResponse = createSelector(
  selectJobPositionState,
  (state: JobPositionState) => state.error
);