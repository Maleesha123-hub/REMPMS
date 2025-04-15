import { createFeatureSelector, createSelector } from '@ngrx/store';
import { EmployerState } from './employer.reducer';

/**
 * Selects the entire employers state from the store.
 *
 * @returns {MemoizedSelector<object, EmployerState>} - A selector function that returns the
 * `EmployerState` from the store.
 * @author @maleeshasa
 */
export const selectEmployerState =
  createFeatureSelector<EmployerState>('employerState');

/**
 * Selects the employer details from the employer state.
 *
 * @returns {MemoizedSelector<object, EmployerState>} - A selector function that returns the
 * employer details from the `EmployerState`.
 * @author @maleeshasa
 */
export const selectEmployersData = createSelector(
  selectEmployerState,
  (state: EmployerState) => state.employersResponse,
);

export const selectEmployerSavedOrModifiedResponseData = createSelector(
  selectEmployerState,
  (state: EmployerState) => state.employerSavedOrModifiedResponse,
);

export const selectEmployerDeletedResponseData = createSelector(
  selectEmployerState,
  (state: EmployerState) => state.employerDeletedResponse,
);

export const selectEmployerErrorResponseData = createSelector(
  selectEmployerState,
  (state: EmployerState) => state.error,
);
