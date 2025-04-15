import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class ResearchAreaService {
  private urlResearchAreaApi =
    `${environment.baseUrl}` + '/candidate/v1/research-area';

  constructor(private httpClient: HttpClient) {}

  getAllActiveResearchAreas(): Observable<any> {
    const url = this.urlResearchAreaApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
