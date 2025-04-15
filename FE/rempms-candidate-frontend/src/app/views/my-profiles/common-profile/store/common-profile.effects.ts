import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { CommonProfileDraftService } from '../../../../service/commonProfileDraft/common-profile-draft.service';
import * as CommonProfileActions from './common-profile.actions';
import { catchError, map, mergeMap, of } from 'rxjs';

@Injectable()
export class CommonProfileEffects {
  constructor(
    private actions$: Actions,
    private commonProfileService: CommonProfileDraftService,
  ) {}

  saveOrModify$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CommonProfileActions.saveCommonProfileData),
      mergeMap((action) =>
        this.commonProfileService
          .createOrModifyCommonProfileDraft(
            action.commonProfileDraft,
            action.documents,
          )
          .pipe(
            map((commonResponse) =>
              CommonProfileActions.saveCommonProfileDataSuccess({
                commonResponse,
              }),
            ),
            catchError((error) =>
              of(CommonProfileActions.saveCommonProfileDataFailure({ error })),
            ),
          ),
      ),
    ),
  );

  findCommonProfileByCandidateId$ = createEffect(() =>
    this.actions$.pipe(
      ofType(CommonProfileActions.findCommonProfileByCandidateId),
      mergeMap((action) =>
        this.commonProfileService
          .findDraftByIdCandidate(action.idCandidate)
          .pipe(
            map((commonResponse) =>
              CommonProfileActions.findCommonProfileByCandidateIdSuccess({
                commonResponse,
              }),
            ),
            catchError((error) =>
              of(
                CommonProfileActions.findCommonProfileByCandidateIdFailure({
                  error,
                }),
              ),
            ),
          ),
      ),
    ),
  );
}
