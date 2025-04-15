import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SchoolEducationService {
  private urlSchemeApi = `${environment.baseUrl}` + '/candidate/v1/scheme';

  constructor(private httpClient: HttpClient) {}

  getBySchoolEduQualification(schoolEduQualification: string) {
    const url = this.urlSchemeApi + '/getBySchoolEduQualification';

    // Constructing request parameters
    const params = new HttpParams().set(
      'schoolEduQualification',
      schoolEduQualification,
    );

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: params,
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
