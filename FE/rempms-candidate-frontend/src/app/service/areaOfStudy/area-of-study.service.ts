import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class AreaOfStudyService {
  private urlAreaOfStudyApi =
    `${environment.baseUrl}` + '/candidate/v1/area-of-study';

  constructor(private httpClient: HttpClient) {}

  getAllActiveAreOfStudies(): Observable<any> {
    const url = this.urlAreaOfStudyApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
