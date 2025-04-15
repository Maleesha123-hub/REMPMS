import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class MembershipTypeService {
  private urlMembershipTypeApi =
    `${environment.baseUrl}` + '/candidate/v1/membership-type';

  constructor(private httpClient: HttpClient) {}

  getAllActive(): Observable<any> {
    const url = this.urlMembershipTypeApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
