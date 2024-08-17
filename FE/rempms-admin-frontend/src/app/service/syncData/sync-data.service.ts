import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommonResponse} from "../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class SyncDataService {

  private urlSyncDataApi = `${environment.baseUrl}` + "/v1/admin/sync-data";

  constructor(private httpClient: HttpClient) {
  }

  SyncUserData() {
    const url = this.urlSyncDataApi + '/syncUser';
    return this.httpClient.post<CommonResponse>(url, null);
  }
}
