import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LanguageService {
  private urlLanguageApi =
    `${environment.baseUrl}` + '/communication/v1/language';

  constructor(private httpClient: HttpClient) {}

  getAllActiveLanguages(): Observable<any> {
    const url = this.urlLanguageApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
