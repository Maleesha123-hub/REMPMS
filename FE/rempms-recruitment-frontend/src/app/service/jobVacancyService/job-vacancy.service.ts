import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { CommonResponse } from 'src/app/model/commonResponse/CommonResponse';
import { JobVacancyRequest } from 'src/app/model/jobVacancy/JobVacancyRequest';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class JobVacancyService {
  private apiUrl = environment.baseUrl + '/recruitment/v1/job-vacancy';

  constructor(private http: HttpClient) {}

  saveOrModify(jobVacancy: JobVacancyRequest, file: File) {
    let saveOrModifyApi = this.apiUrl + '/saveOrModify';
    const formData = new FormData();
    const jobVacancyDetails = new Blob([JSON.stringify(jobVacancy)], {
      type: 'application/json',
    });
    formData.append('jobVacancyDetails', jobVacancyDetails);
    if (file !== null) {
      formData.append('document', file, file.name);
    }
    console.info('Save or modify method calling for job vacancy...');
    return this.http.post<CommonResponse>(saveOrModifyApi, formData).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  getAll() {
    let getAllApi = this.apiUrl + '/getAll';
    console.info('Get all method calling for job vacancies...');
    return this.http.get<CommonResponse>(getAllApi).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  /**
   * Handles HTTP request errors.
   * @param {any} error - The error object received from the HTTP request.
   * @returns {Observable<never>} - An observable that throws an error.
   * @author @maleeshasa
   */
  handleError(error: any): Observable<never> {
    // Return an observable that throws a new error with a message.
    return throwError(() => error);
  }
}
