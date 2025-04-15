import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { UserRequestDTO } from '../../../model/user/UserRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApi = `${environment.loginUrl}` + '/v1/auth/user';

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    const userRequest: UserRequestDTO = {
      userName: username,
      password: password,
      uuid: environment.uuid,
    };
    let loginAuthApi = this.authApi + '/token';
    console.info(
      'Calling iam service to get access and refresh tokens for the user...',
    );
    return this.http.post<CommonResponse>(loginAuthApi, userRequest).pipe(
      map((response) => response),
      catchError((error) => {
        return this.handleError(error);
      }),
    );
  }

  isAuthenticated(): string | any {
    return sessionStorage.getItem('authStatus');
  }

  setAuthenticationStatus(authStatus: string) {
    sessionStorage.setItem('authStatus', authStatus);
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
