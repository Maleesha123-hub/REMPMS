import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class ProvinceService {

  private urlProvinceApi = `${environment.baseUrl}` + "/v1/location/province";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateProvince(provinceFormValues: any): Observable<any> {
    const url = this.urlProvinceApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, provinceFormValues, httpOptions);
  }

  getAllActiveProvinces(): Observable<any> {
    const url = this.urlProvinceApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveProvinceById(idProvince: string): Observable<any> {
    const url = this.urlProvinceApi + '/getById/' + idProvince;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteProvinceById(idProvince: string): Observable<any> {
    const url = this.urlProvinceApi + '/deleteById/' + idProvince;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getProvincesByCountryId(idCountry:string):Observable<any>{
    const url = this.urlProvinceApi + '/getByCountryId/' + idCountry;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
