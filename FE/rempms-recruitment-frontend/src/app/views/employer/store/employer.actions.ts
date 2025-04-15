import { createAction, props } from '@ngrx/store';
import { Employer } from '../../../model/employer/Employer';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';

// Actions for get all employers.
/**
 * This actions is to initiate the process to get all employers.
 * @returns {Action} - An action with the type and payload containing employers.
 * @author @Maleesha99
 */
export const loadEmployers = createAction('[Employer] Load Employers');

/**
 * This action is to dispatched when the employer details are successfully retrieved.
 * @returns {Action} - An action with the type and payload containing employers.
 * @author @Maleesha99
 */
export const loadEmployersSuccess = createAction(
  '[Employer] Load Employers Success',
  props<{ response: CommonResponse }>(),
);

/**
 * This action is to dispatched when there is a failiur in retriving employers.
 * @returns {Action} - An action with the type and payload containing error.
 * @author @Maleesha99
 */
export const loadEmployersFailure = createAction(
  '[Employer] Load Employers Failure',
  props<{ error: any }>(),
);

// Actions for delete an employer
export const deleteEmployer = createAction(
  '[Employer] Delete an Employer',
  props<{ id: number }>(),
);

export const deleteEmployerSuccess = createAction(
  '[Employer] Delete an Employer Success',
  props<{ response: CommonResponse }>(),
);

export const deleteEmployerFailure = createAction(
  'Employer Delete an Employer Failure',
  props<{ error: any }>(),
);

// Actions for save or modify an employer
export const saveOrModifyEmployer = createAction(
  '[Employer] Save or Modify an Employer',
  props<{ employer: Employer }>(),
);

export const saveOrModifyEmployerSuccess = createAction(
  '[Employer] Save or Modify an Employer Success',
  props<{ response: CommonResponse }>(),
);

export const saveOrModifyEmployerFailure = createAction(
  '[Employer] Save or Modify an Employer Failure',
  props<{ error: any }>(),
);
