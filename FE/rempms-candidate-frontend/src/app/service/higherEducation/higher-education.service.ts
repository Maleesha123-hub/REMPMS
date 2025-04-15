import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HigherEducationService {
  private urlHigherEducationApi =
    `${environment.baseUrl}` + '/candidate/v1/getById';

  constructor(private httpClient: HttpClient) {}

  getById(id: string) {
    const url = this.urlHigherEducationApi + '/getById';

    // Constructing request parameters
    const params = new HttpParams().set('id', id);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: params,
    };
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
