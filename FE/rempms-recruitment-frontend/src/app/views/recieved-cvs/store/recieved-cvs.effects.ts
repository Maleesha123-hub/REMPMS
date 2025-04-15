import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { RecievedCvsService } from '../../../service/recievedCvsService/recieved-cvs.service';
import * as RecievdedCvsActions from './recieved-cvs.actions';
import { catchError, map, mergeMap, of } from 'rxjs';

@Injectable()
export class RecievedCvsEffects {
  constructor(
    private actions$: Actions,
    private recievedCvsService: RecievedCvsService,
  ) {}

  getRecievedCvsById$ = createEffect(() =>
    this.actions$.pipe(
      ofType(RecievdedCvsActions.loadRecievedCvsByVacancyId),
      mergeMap((action) =>
        this.recievedCvsService.getRecievedCvsById(action.id).pipe(
          map((commonResponse) =>
            RecievdedCvsActions.loadRecievedCvsByVacancyIdSuccess({
              commonResponse,
            }),
          ),
          catchError((error) =>
            of(
              RecievdedCvsActions.loadRecievedCvsByVacancyIdFailure({ error }),
            ),
          ),
        ),
      ),
    ),
  );
}
