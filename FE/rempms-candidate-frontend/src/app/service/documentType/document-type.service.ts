import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class DocumentTypeService {
  private urlDocumentTypeApi =
    `${environment.baseUrl}` + '/candidate/v1/document-type';

  constructor(private httpClient: HttpClient) {}

  getAllActiveDocumentTypes(): Observable<any> {
    const url = this.urlDocumentTypeApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
