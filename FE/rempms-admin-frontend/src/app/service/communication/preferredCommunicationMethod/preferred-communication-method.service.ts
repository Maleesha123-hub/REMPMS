import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class PreferredCommunicationMethodService {

  private urlPrefCommApi = `${environment.baseUrl}` + "/v1/communication/preferredCommunication";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdatePreferredCommunication(prefCommFormValues: any): Observable<any> {
    const url = this.urlPrefCommApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, prefCommFormValues, httpOptions);
  }

  getAllActivePreferredCommunications(): Observable<any> {
    const url = this.urlPrefCommApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActivePreferredCommunicationById(idPreferredCommunication: string): Observable<any> {
    const url = this.urlPrefCommApi + '/getById/' + idPreferredCommunication;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deletePreferredCommunicationById(idPreferredCommunication: string): Observable<any> {
    const url = this.urlPrefCommApi + '/deleteById/' + idPreferredCommunication;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
