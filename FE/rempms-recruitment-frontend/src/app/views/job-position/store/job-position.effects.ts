import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { JobPositionService } from '../../../service/jobPositionService/job-position.service';
import * as JobPositionActions from './job-position.actions';
import { catchError, map, mergeMap, of } from 'rxjs';

@Injectable()
export class JobPositionEffects {
  constructor(
    private jobPositionService: JobPositionService,
    private actions$: Actions
  ) {}

  saveOrModify$ = createEffect(() =>
    this.actions$.pipe(
      ofType(JobPositionActions.saveOrModifyJobPosition),
      mergeMap((action) =>
        this.jobPositionService.saveOrModify(action.jobPosition).pipe(
          map((commonResponse) =>
            JobPositionActions.saveOrModifyJobPositionSuccess({
              commonResponse,
            })
          ),
          catchError((error) =>
            of(JobPositionActions.saveOrModifyJobPositionFailure({ error }))
          )
        )
      )
    )
  );

  deleteById$ = createEffect(() =>
    this.actions$.pipe(
      ofType(JobPositionActions.deleteJobPosition),
      mergeMap((action) =>
        this.jobPositionService.deleteById(action.id).pipe(
          map((commonResponse) =>
            JobPositionActions.deleteJobPositionSuccess({ commonResponse })
          ),
          catchError((error) =>
            of(JobPositionActions.deleteJobPositionFailure({ error }))
          )
        )
      )
    )
  );

  getAll$ = createEffect(() =>
    this.actions$.pipe(
      ofType(JobPositionActions.getAllJobPositions),
      mergeMap(() =>
        this.jobPositionService.getAll().pipe(
          map((response) =>
            JobPositionActions.getAllJobPositionSuccess({ response })
          ),
          catchError((error) =>
            of(JobPositionActions.getAllJobPositionFailure({ error }))
          )
        )
      )
    )
  );

  getAllIndustries$ = createEffect(() =>
    this.actions$.pipe(
      ofType(JobPositionActions.getAllActiveIndustries),
      mergeMap(() =>
        this.jobPositionService.getAllActiveIndustries().pipe(
          map((response) =>
            JobPositionActions.getAllActiveIndustriesSuccess({ response })
          ),
          catchError((error) =>
            of(JobPositionActions.getAllJobPositionFailure({ error }))
          )
        )
      )
    )
  );
}
