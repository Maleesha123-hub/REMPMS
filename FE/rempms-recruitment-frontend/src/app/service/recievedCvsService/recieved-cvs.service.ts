import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable, throwError } from 'rxjs';
import { CommonResponse } from 'src/app/model/commonResponse/CommonResponse';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RecievedCvsService {
  private apiUrl =
    environment.baseUrl + '/recruitment/v1/vacancy-has-candidates';

  constructor(private http: HttpClient) {}

  /**
   *
   * @param id This method is allowed to get recived cvs by job vacancy id
   * @author @maleeshasa
   */
  getRecievedCvsById(id: number) {
    let getRecievedCvsByIdApiUrl = this.apiUrl + '/getById';
    // Constructing request parameters
    const params = new HttpParams().set('id', id);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: params,
    };

    console.info('Get recieved cvs by id method calling...');
    return this.http
      .get<CommonResponse>(getRecievedCvsByIdApiUrl, httpOptions)
      .pipe(
        map((response) => {
          console.log(response.data);
          return response;
        }),
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
