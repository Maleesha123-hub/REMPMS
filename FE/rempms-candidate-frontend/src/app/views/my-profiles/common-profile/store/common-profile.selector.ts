import { createFeatureSelector, createSelector } from '@ngrx/store';
import { CommonProfileState } from './common-profile.reducer';

export const selectCommonProfileDraftState =
  createFeatureSelector<CommonProfileState>('commonProfileState');

export const selectCommonProfileDraftSaveResponse = createSelector(
  selectCommonProfileDraftState,
  (state: CommonProfileState) => state.commonProfileDraftSaveResponse,
);

export const selectCommonProfileDetailsByCandidateIdResponse = createSelector(
  selectCommonProfileDraftState,
  (state: CommonProfileState) => state.commonProfileDraftSaveResponse,
);

export const selectCommonProfileDetailsErrorResponse = createSelector(
  selectCommonProfileDraftState,
  (state: CommonProfileState) => state.error,
);
