import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class CandidateService {
  private urlCandidateApi =
    `${environment.baseUrl}` + '/candidate/v1/common-profile';

  constructor(private httpClient: HttpClient) {}

  saveCandidate(idCandidate: number): Observable<any> {
    const url = this.urlCandidateApi + '/saveUpdate';

    // Setting the idCandidate as a query parameter
    const params = new HttpParams().set('idCandidate', idCandidate.toString());

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: params,
    };

    // Sending a POST request with the query parameter
    return this.httpClient.post<CommonResponse>(url, {}, httpOptions);
  }
}
