import { createAction, props } from '@ngrx/store';
import { CommonResponse } from '../../../../model/commonResponse/CommonResponse';
import { CommonProfileDraft } from '../../.././../model/candidate/commonProfileDraft/CommonProfileDraft';

// Save common profile data
export const saveCommonProfileData = createAction(
  '[Common Profile] Save Common Profile Data',
  props<{ commonProfileDraft: CommonProfileDraft; documents: File[] }>(),
);

// Save common profile data Success
export const saveCommonProfileDataSuccess = createAction(
  '[Common Profile] Save Common Profile Data Success',
  props<{ commonResponse: CommonResponse }>(),
);

// Save common profile data Failure
export const saveCommonProfileDataFailure = createAction(
  '[Common Profile] Save Common Profile Data Failure',
  props<{ error: any }>(),
);

// Find common profile data by id
export const findCommonProfileByCandidateId = createAction(
  '[Common Profile] Get Common Profile Data By Id',
  props<{ idCandidate: string }>(),
);

// Find common profile data by id Success
export const findCommonProfileByCandidateIdSuccess = createAction(
  '[Common Profile] Get Common Profile Data By Id Success',
  props<{ commonResponse: CommonResponse }>(),
);

// Find common profile data by id Failure
export const findCommonProfileByCandidateIdFailure = createAction(
  '[Common Profile] Get Common Profile Data By Id Failure',
  props<{ error: any }>(),
);
