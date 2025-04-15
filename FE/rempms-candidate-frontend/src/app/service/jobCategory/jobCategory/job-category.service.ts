import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';

@Injectable({
  providedIn: 'root',
})
export class JobCategoryService {
  private urlJobCategoryApi =
    `${environment.baseUrl}` + '/candidate/v1/job-category';

  constructor(private httpClient: HttpClient) {}

  getAllActiveJobCategories(): Observable<any> {
    const url = this.urlJobCategoryApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
