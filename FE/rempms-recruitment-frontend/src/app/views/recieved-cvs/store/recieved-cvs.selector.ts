import { createFeatureSelector, createSelector } from '@ngrx/store';
import { RecievedCvsState } from './recieved-cvs.reducer';

export const selectRecievedCvsState =
  createFeatureSelector<RecievedCvsState>('recievedCvsState');

export const selectRecievedCvsDetails = createSelector(
  selectRecievedCvsState,
  (state: RecievedCvsState) => state.recievedCvsResponse,
);

export const selectRecievedCvsErrorResponse = createSelector(
  selectRecievedCvsState,
  (state: RecievedCvsState) => state.error,
);
