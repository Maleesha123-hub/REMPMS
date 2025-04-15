import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, catchError, throwError } from 'rxjs';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userApi = `${environment.loginUrl}` + '/v1/user';

  constructor(private http: HttpClient) {}

  getUserPermissionList(): Observable<any> {
    let userDetailsApi = this.userApi + '/get-user-details';
    const data = {
      uuid: environment.uuid,
    };

    console.info(
      'Calling iam service to get user permissions list by token and uuid...',
    );
    return this.http.post<CommonResponse>(userDetailsApi, data).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      }),
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
