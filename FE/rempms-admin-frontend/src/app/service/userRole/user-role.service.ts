import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {CommonResponse} from "../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class UserRoleService {

  private urlUserRoleApi = `${environment.baseUrl}` + "/v1/admin/user-role";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateUserRole(userRoleFormValues: any): Observable<any> {
    const url = this.urlUserRoleApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, userRoleFormValues, httpOptions);
  }

  getAllActiveUserRoles(): Observable<any> {
    const url = this.urlUserRoleApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveUserRoleById(idUserRole: string): Observable<any> {
    const url = this.urlUserRoleApi + '/getById/' + idUserRole;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteUserRoleById(idUserRole: string): Observable<any> {
    const url = this.urlUserRoleApi + '/deleteById/' + idUserRole;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
