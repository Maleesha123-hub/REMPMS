import { createAction, props } from '@ngrx/store';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { JobVacancyRequest } from 'src/app/model/jobVacancy/JobVacancyRequest';

// Load job vacancies
export const loadJobVacancies = createAction(
  '[Job Vacancy] Load Job Vacancies',
);

export const loadJobVacanciesSuccess = createAction(
  '[Job Vacancy] Load Job Vacancies Success',
  props<{ commonResponse: CommonResponse }>(),
);

export const loadJobVacanciesFailure = createAction(
  '[Job Vacancy] Load Job Vacancies Failure',
  props<{ error: any }>(),
);

// Delete job vacancy
export const deleteJobVacancy = createAction(
  '[Job Vacancy] Delete Job Vacancy',
);

export const deleteJobVacancySuccess = createAction(
  '[Job Vacancy] Delete Job Vacancy Success',
  props<{ commonResponse: CommonResponse }>(),
);

export const deleteJobVacancyFailure = createAction(
  '[Job Vacancy] Delete Job Vacancy Failure',
  props<{ error: any }>(),
);

// Save or modify job vacancy
export const saveOrModifyJobVacancy = createAction(
  '[Job Vacancy] Save or Modify Job Vacancy',
  props<{ jobVacancy: JobVacancyRequest; file: File }>(),
);

export const saveOrModifyJobVacancySuccess = createAction(
  '[Job Vacancy] Save or Modify Job Vacancy Success',
  props<{ commonResponse: CommonResponse }>(),
);

export const saveOrModifyJobVacancyFailure = createAction(
  '[Job Vacancy] Save or Modify Job Vacancy Failure',
  props<{ error: any }>(),
);
