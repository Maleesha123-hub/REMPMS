import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import { EmployerService } from '../../../service/employerService/employer.service';
import * as EmployersActions from './employer.actions';

/**
 * @author @maleeshasa
 * @Date 2024/05/27
 */
@Injectable()
export class EmployerEffects {
  constructor(
    private employerService: EmployerService,
    private actions$: Actions
  ) {}

  /**
   * This method is allowed to handle get all employers
   * Listens for the getAllEmployers action and triggers the service call.
   * On success, dispatches getEmployersSuccess action with the retrieved employers data.
   * On failure, dispatches getEmployerFailiure action with the error.
   * @returns {Observable<Action>} - An observable of actions dispatched based on the outcome of the HTTP request.
   * @author @maleeshasa
   */
  loadEmployers$ = createEffect(() =>
    this.actions$.pipe(
      ofType(EmployersActions.loadEmployers),
      mergeMap(() =>
        this.employerService.getAll().pipe(
          map((response) =>
            EmployersActions.loadEmployersSuccess({ response })
          ),
          catchError((error) =>
            of(EmployersActions.loadEmployersFailure({ error }))
          )
        )
      )
    )
  );

  saveOrModify$ = createEffect(() =>
    this.actions$.pipe(
      ofType(EmployersActions.saveOrModifyEmployer),
      mergeMap((action) =>
        this.employerService.saveOrModify(action.employer).pipe(
          map((response) =>
            EmployersActions.saveOrModifyEmployerSuccess({
              response,
            })
          ),
          catchError((error) =>
            of(EmployersActions.saveOrModifyEmployerFailure({ error }))
          )
        )
      )
    )
  );

  deleteById$ = createEffect(() =>
    this.actions$.pipe(
      ofType(EmployersActions.deleteEmployer),
      mergeMap((action) =>
        this.employerService.deleteById(action.id).pipe(
          map((response) =>
            EmployersActions.deleteEmployerSuccess({ response })
          ),
          catchError((error) =>
            of(EmployersActions.deleteEmployerFailure({ error }))
          )
        )
      )
    )
  );
}
