import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';
import { CommonProfileDraft } from '../../model/candidate/commonProfileDraft/CommonProfileDraft';

@Injectable({
  providedIn: 'root',
})
export class CommonProfileDraftService {
  private urlDraftApi =
    `${environment.baseUrl}` + '/draft/v2/candidate-common-profile';

  constructor(private httpClient: HttpClient) {}

  createOrModifyCommonProfileDraft(
    commonProfileDraft: CommonProfileDraft,
    documents: File[],
  ): Observable<any> {
    const url = this.urlDraftApi + '/createOrModify';

    const formdata = new FormData();

    const commonProfileDetails = new Blob(
      [JSON.stringify(commonProfileDraft)],
      {
        type: 'application/json',
      },
    );

    if (documents.length > 0) {
      documents.forEach((document) => {
        formdata.append('documents', document, document.name);
      });
    }

    formdata.append('commonProfileDetails', commonProfileDetails);

    return this.httpClient.post<CommonResponse>(url, formdata);
  }

  findDraftByIdCandidate(idCandidate: string) {
    const url = this.urlDraftApi + '/findByIdCandidate';

    // Constructing request parameters
    const params = new HttpParams().set('idCandidate', idCandidate);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: params,
    };
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
