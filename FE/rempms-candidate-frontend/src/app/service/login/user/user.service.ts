import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { UserRegisterDTO } from '../../../model/user/register/UserRegisterDTO';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private userApi = `${environment.loginUrl}` + '/user/v1';

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

  register(userRegister: UserRegisterDTO): Observable<any> {
    let registerAuthApi = this.userApi + '/non-ad/create';
    console.info('Calling iam service to register the non-ad user...');
    return this.http.post<CommonResponse>(registerAuthApi, userRegister).pipe(
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
