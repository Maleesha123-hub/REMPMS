import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommonResponse} from "../../model/commonResponse/CommonResponse";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class JobCategoryService {

  private urlJobCategoryApi = `${environment.baseUrl}` + "/v1/admin/job-category";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateJobCategory(jobCategoryFormValues: any): Observable<any> {
    const url = this.urlJobCategoryApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, jobCategoryFormValues, httpOptions);
  }

  getAllActiveJobCategories(): Observable<any> {
    const url = this.urlJobCategoryApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveJobCategoryById(idJobCategory: string): Observable<any> {
    const url = this.urlJobCategoryApi + '/getById/' + idJobCategory;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteJobCategoryById(idJobCategory: string): Observable<any> {
    const url = this.urlJobCategoryApi + '/deleteById/' + idJobCategory;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
