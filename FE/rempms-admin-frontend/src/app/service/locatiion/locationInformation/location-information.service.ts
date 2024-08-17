import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";
import {environment} from "../../../../environments/environment";
import Swal from "sweetalert2";

@Injectable({
  providedIn: 'root'
})
export class LocationInformationService {

  private urlLocationInfoApi = `${environment.baseUrl}` + "/v1/location/locationInformation";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateLocationInfo(locationInfoFormValues: any): Observable<any> {
    const url = this.urlLocationInfoApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, locationInfoFormValues, httpOptions);
  }

  getAllActiveLocationInformation(): Observable<any> {
    const url = this.urlLocationInfoApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveLocationInformationById(idLocationInformation: string) {
    const url = this.urlLocationInfoApi + '/getById/' + idLocationInformation;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteLocationInformationById(idLocationInformation: string): Observable<any> {
    const url = this.urlLocationInfoApi + '/deleteById/' + idLocationInformation;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
