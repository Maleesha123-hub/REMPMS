import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PreferredCommunicationMethodService {
  private urlPrefCommApi =
    `${environment.baseUrl}` + '/communication/v1/preferredCommunication';

  constructor(private httpClient: HttpClient) {}

  getAllActivePreferredCommunications(): Observable<any> {
    const url = this.urlPrefCommApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
