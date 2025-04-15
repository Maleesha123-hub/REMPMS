import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { CommonResponse } from '../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class SubjectService {
  private urlSubjectApi = `${environment.baseUrl}` + '/candidate/v1/subject';

  constructor(private httpClient: HttpClient) {}

  getBySchoolEduQualification(schoolEduQualification: string) {
    const url = this.urlSubjectApi + '/getBySchoolEduQualification';

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

  getBySchoolEduQualificationAndScheme(
    schoolEduQualification: string,
    schemeId: string,
  ) {
    const url = this.urlSubjectApi + '/getBySchoolEduQualificationAndScheme';

    // Constructing request parameters
    let params = new HttpParams();
    params = params.set('schemeId', schemeId);
    params = params.set('schoolEduQualification', schoolEduQualification);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      params: params,
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
