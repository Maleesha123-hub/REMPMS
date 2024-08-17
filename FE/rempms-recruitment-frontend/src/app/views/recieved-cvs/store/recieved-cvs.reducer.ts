import { createReducer, on } from "@ngrx/store";
import { CommonResponse } from "../../../model/commonResponse/CommonResponse";
import * as RecievdedCvsActions from './recieved-cvs.actions';

export interface RecievedCvsState {
    recievedCvsResponse: CommonResponse | null;
    error: any;
    loading: boolean;
}

export const initialRecievedCvsState: RecievedCvsState = {
    recievedCvsResponse: null,
    error: null,
    loading: false,
};

export const recievedCvsReducer = createReducer(
    initialRecievedCvsState,
    // Handles the action to get recived cv details.
    on(RecievdedCvsActions.loadRecievedCvsByVacancyId, (state) => ({
        ...state,
        recievedCvsResponse: null,
        error: null,
        loading: true,
    })),
    // Handles the action when get recived cv details success.
    on(
        RecievdedCvsActions.loadRecievedCvsByVacancyIdSuccess,
        (state, { commonResponse }) => ({
            ...state,
            recievedCvsResponse: commonResponse,
            error: null,
            loading: false,
        })
    ),
    // Handles the action when get recived cv details fails.
    on(RecievdedCvsActions.loadRecievedCvsByVacancyIdFailure, (state, { error }) => ({
        ...state,
        recievedCvsResponse: null,
        error: error,
        loading: false,
    }))
);

