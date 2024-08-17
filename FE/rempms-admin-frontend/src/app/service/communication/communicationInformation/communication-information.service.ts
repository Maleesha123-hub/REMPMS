import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class CommunicationInformationService {

  private urlCommInfoApi = `${environment.baseUrl}` + "/v1/communication/communicationInformation";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateCommunicationInformation(commInfoFormValues: any): Observable<any> {
    const url = this.urlCommInfoApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, commInfoFormValues, httpOptions);
  }

  getAllActiveCommunicationInformation(): Observable<any> {
    const url = this.urlCommInfoApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveCommunicationInformationById(idCommunicationInformation: string): Observable<any> {
    const url = this.urlCommInfoApi + '/getById/' + idCommunicationInformation;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
