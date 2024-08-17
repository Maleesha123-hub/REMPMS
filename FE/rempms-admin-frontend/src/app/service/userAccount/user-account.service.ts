import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {CommonResponse} from "../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class UserAccountService {

  private urlUserAccountApi = `${environment.baseUrl}` + "/v1/admin/user-account";
  private urlDocumentUploadApi = `${environment.baseUrl}` + "/v1/admin/document";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateUserAccount(userAccountFormValues: any): Observable<any> {
    const url = this.urlUserAccountApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, userAccountFormValues, httpOptions);
  }

  getAllActiveUserAccounts(): Observable<any> {
    const url = this.urlUserAccountApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveUserAccountById(idUserAccount: string): Observable<any> {
    const url = this.urlUserAccountApi + '/getById/' + idUserAccount;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteUserAccountById(idUserAccount: string): Observable<any> {
    const url = this.urlUserAccountApi + '/deleteById/' + idUserAccount;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  uploadUserImage(formData: FormData) {
    const url = this.urlDocumentUploadApi + '/upload';
    return this.httpClient.post<CommonResponse>(url, formData);
  }

  setUserImage(idUserAccount: string, imageName: string) {
    const url = this.urlDocumentUploadApi + '/downloader/' + idUserAccount + '/' + imageName + '/' + 'USER_IMG';
    return this.httpClient.get(url, {responseType: 'blob'});
  }
}
