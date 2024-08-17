import { createAction, props } from '@ngrx/store';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';

// Load Recieved Cvs
export const loadRecievedCvsByVacancyId = createAction(
    '[Recieved Cvs] Load Recieved Cvs',
    props<{ id: number }>()
);

export const loadRecievedCvsByVacancyIdSuccess = createAction(
    '[Recieved Cvs] Load Recieved Cvs Success',
    props<{ commonResponse: CommonResponse }>()
);

export const loadRecievedCvsByVacancyIdFailure = createAction(
    '[Recieved Cvs] Load Recieved Cvs Failure',
    props<{ error: any }>()
);