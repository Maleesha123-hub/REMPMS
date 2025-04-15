import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class IndustryService {
  private urlIndustryApi = `${environment.baseUrl}` + '/candidate/v1/industry';

  constructor(private httpClient: HttpClient) {}

  getAllActiveIndustries(): Observable<any> {
    const url = this.urlIndustryApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
