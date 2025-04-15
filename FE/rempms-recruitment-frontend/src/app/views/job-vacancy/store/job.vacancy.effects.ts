import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { catchError, map, mergeMap, of } from 'rxjs';
import * as JobVacancyActions from './job.vacancy.actions';
import { JobVacancyService } from '../../../service/jobVacancyService/job-vacancy.service';
import { DocumentService } from '../../../service/documentService/document.service';

@Injectable()
export class JobVacancyEffects {
  constructor(
    private actions$: Actions,
    private jobVacancyService: JobVacancyService,
    private documentService: DocumentService,
  ) {}

  saveOrModify$ = createEffect(() =>
    this.actions$.pipe(
      ofType(JobVacancyActions.saveOrModifyJobVacancy),
      mergeMap((action) =>
        this.jobVacancyService
          .saveOrModify(action.jobVacancy, action.file)
          .pipe(
            map((commonResponse) =>
              JobVacancyActions.saveOrModifyJobVacancySuccess({
                commonResponse,
              }),
            ),
            catchError((error) =>
              of(JobVacancyActions.saveOrModifyJobVacancyFailure({ error })),
            ),
          ),
      ),
    ),
  );

  getAll$ = createEffect(() =>
    this.actions$.pipe(
      ofType(JobVacancyActions.loadJobVacancies),
      mergeMap(() =>
        this.jobVacancyService.getAll().pipe(
          map((commonResponse) =>
            JobVacancyActions.loadJobVacanciesSuccess({
              commonResponse,
            }),
          ),
          catchError((error) =>
            of(JobVacancyActions.loadJobVacanciesFailure({ error })),
          ),
        ),
      ),
    ),
  );
}
