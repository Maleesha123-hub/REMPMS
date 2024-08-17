import { Injectable } from '@angular/core';
import { JobPositionRequest } from '../../model/jobPosition/JobPositionRequest';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { CommonResponse } from 'src/app/model/commonResponse/CommonResponse';
import { Observable, catchError, map, throwError } from 'rxjs';

/**
 * @author @maleeshasa
 * @Date 2024/06/16
 */
@Injectable({
  providedIn: 'root',
})
export class JobPositionService {
  private apiUrl = environment.baseUrl + '/recruitment/v1/job-position';
  private apiUrlForCandidate = environment.baseUrl + '/candidate/v1/industry';

  constructor(private http: HttpClient) {}

  /**
   * This method is allowed to fetch all active job positions
   * @returns {Observable<Employer[]>} - An observable containing the response with job positions
   * @author @maleeshasa
   */
  getAll() {
    let getAllJobPositionsApiUrl = this.apiUrl + '/getAll';
    console.info('Get all job positions method calling...');
    return this.http.get<CommonResponse>(getAllJobPositionsApiUrl).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  /**
   *
   * @param id This method is allowed to delete a job position
   * @author @maleeshasa
   */
  deleteById(id: number) {
    let deleteJobPositionByIdApiUrl = this.apiUrl + '/delete/' + id;
    console.info('Delete a job position by id method calling...');
    return this.http.delete<CommonResponse>(deleteJobPositionByIdApiUrl).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  /**
   * This method is allowed to save or modify a job position
   * @returns - save or update message response
   * @author @maleeshasa
   */
  saveOrModify(jobPosition: JobPositionRequest) {
    let saveOrModifyApi = this.apiUrl + '/saveOrModify';
    console.info('Save or modify job position method calling...');
    return this.http.post<CommonResponse>(saveOrModifyApi, jobPosition).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  getAllActiveIndustries() {
    let getAllIndustriesApiUrl = this.apiUrlForCandidate + '/getAllActive';
    console.info('Get all job positions method calling...');
    return this.http.get<CommonResponse>(getAllIndustriesApiUrl).pipe(
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
