import { createAction, props } from '@ngrx/store';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { JobPositionRequest } from '../../../model/jobPosition/JobPositionRequest';

// Actions for save or modify job position
export const saveOrModifyJobPosition = createAction(
  '[Job Position] Save or Modify Job Position',
  props<{ jobPosition: JobPositionRequest }>()
);

export const saveOrModifyJobPositionSuccess = createAction(
  '[Job Position] Save or Modify Job Position Success',
  props<{ commonResponse: CommonResponse }>()
);

export const saveOrModifyJobPositionFailure = createAction(
  '[Job Position] Save or Modify Job Position Failure',
  props<{ error: any }>()
);

// Actions for delete job position
export const deleteJobPosition = createAction(
  '[Job Position] Delete Job Position',
  props<{ id: number }>()
);

export const deleteJobPositionSuccess = createAction(
  '[Job Position] Delete Job Position Success',
  props<{ commonResponse: CommonResponse }>()
);

export const deleteJobPositionFailure = createAction(
  '[Job Position] Delete Job Position Failure',
  props<{ error: any }>()
);

// Actions for get all jog positions
export const getAllJobPositions = createAction(
  '[Job Position] Get All Job Positions'
);

export const getAllJobPositionSuccess = createAction(
  '[Job Position] Get All Job Positions Success',
  props<{ response: CommonResponse }>()
);

export const getAllJobPositionFailure = createAction(
  '[Job Position] Get All Job Positions Failure',
  props<{ error: any }>()
);

// Actions for get active industries
export const getAllActiveIndustries = createAction(
  '[Industry] Get All Active Industries'
);

export const getAllActiveIndustriesSuccess = createAction(
  '[Industry] Get All Active Industries Success',
  props<{ response: CommonResponse }>()
);

export const getActiveIndustriesFailure = createAction(
  '[Industry] Get All Active Industries Failure',
  props<{ error: any }>()
);
