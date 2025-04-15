import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class HigherEduQualificationService {
  private urlEduQuaApi =
    `${environment.baseUrl}` + '/candidate/v1/higher-edu-qualification';

  constructor(private httpClient: HttpClient) {}

  getAllActiveHigherEduQualification(): Observable<any> {
    const url = this.urlEduQuaApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getById(id: string) {
    const url = this.urlEduQuaApi + '/getById';

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
