import { createReducer, on } from '@ngrx/store';
import * as CommonProfileActions from './common-profile.actions';
import { CommonResponse } from 'src/app/model/commonResponse/CommonResponse';

export interface CommonProfileState {
  commonProfileDraftSaveResponse: CommonResponse | null;
  loadCommonProfileDraftResponse: CommonResponse | null;
  error: any;
  loading: boolean;
}

export const initialCommonProfileState: CommonProfileState = {
  commonProfileDraftSaveResponse: null,
  loadCommonProfileDraftResponse: null,
  error: undefined,
  loading: false,
};

// Handles the action to save common profile data
export const commonProfileReducer = createReducer(
  initialCommonProfileState,
  on(CommonProfileActions.saveCommonProfileData, (state) => ({
    ...state,
    commonProfileDraftSaveResponse: null,
    loadCommonProfileDraftResponse: null,
    error: null,
    loading: true,
  })),
  // Handles the action to save common profile data success
  on(
    CommonProfileActions.saveCommonProfileDataSuccess,
    (state, { commonResponse }) => ({
      ...state,
      commonProfileDraftSaveResponse: commonResponse,
      loadCommonProfileDraftResponse: null,
      error: null,
      loading: false,
    }),
  ),
  // Handles the action to save common profile data failure
  on(CommonProfileActions.saveCommonProfileDataFailure, (state, { error }) => ({
    ...state,
    commonProfileDraftSaveResponse: null,
    loadCommonProfileDraftResponse: null,
    error: error,
    loading: false,
  })),
  // Handles the action to find common profile data by candidate id
  on(CommonProfileActions.findCommonProfileByCandidateId, (state) => ({
    ...state,
    commonProfileDraftSaveResponse: null,
    loadCommonProfileDraftResponse: null,
    error: null,
    loading: true,
  })),
  // Handles the action to find common profile data by candidate id success
  on(
    CommonProfileActions.findCommonProfileByCandidateIdSuccess,
    (state, { commonResponse }) => ({
      ...state,
      commonProfileDraftSaveResponse: null,
      loadCommonProfileDraftResponse: commonResponse,
      error: null,
      loading: false,
    }),
  ),
  // Handles the action to find common profile data by candidate id failure
  on(
    CommonProfileActions.findCommonProfileByCandidateIdFailure,
    (state, { error }) => ({
      ...state,
      commonProfileDraftSaveResponse: null,
      loadCommonProfileDraftResponse: null,
      error: error,
      loading: false,
    }),
  ),
);
