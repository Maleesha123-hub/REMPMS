import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';
import { Employer } from '../../model/employer/Employer';

/**
 * @author @maleeshasa
 * @Date 2024/05/27
 */
@Injectable({
  providedIn: 'root',
})
export class EmployerService {
  private apiUrl = environment.baseUrl + '/recruitment/v1/employer';

  constructor(private http: HttpClient) {}

  /**
   * This method is allowed to fetch all employers
   * @returns {Observable<Employer[]>} - An observable containing the response with employers
   * @author @maleeshasa
   */
  getAll() {
    let getAllEmployerApiUrl = this.apiUrl + '/getAll';
    console.info('Get all employers method calling...');
    return this.http.get<CommonResponse>(getAllEmployerApiUrl).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  /**
   *
   * @param id This method is allowed to delete en employer
   * @author @maleeshasa
   */
  deleteById(id: number) {
    let deleteEmployerByIdApiUrl = this.apiUrl + '/delete/' + id;
    console.info('Delete amployer by id method calling...');
    return this.http.delete<CommonResponse>(deleteEmployerByIdApiUrl).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      })
    );
  }

  /**
   * This method is allowed to save or modify an employer
   * @returns - save or update message response
   * @author @maleeshasa
   */
  saveOrModify(employer: Employer) {
    let saveOrModifyApi = this.apiUrl + '/saveOrModify';
    console.info('Save or modify method calling...');
    return this.http.post<CommonResponse>(saveOrModifyApi, employer).pipe(
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
