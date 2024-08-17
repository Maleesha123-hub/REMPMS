import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private urlCountryApi = `${environment.baseUrl}` + "/v1/location/country";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateCountry(countryFormValues: any): Observable<any> {
    const url = this.urlCountryApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, countryFormValues, httpOptions);
  }

  getAllActiveCountries(): Observable<any> {
    const url = this.urlCountryApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveCountryById(idCountry: string): Observable<any> {
    const url = this.urlCountryApi + '/getById/' + idCountry;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteCountryById(idCountry: string): Observable<any> {
    const url = this.urlCountryApi + '/deleteById/' + idCountry;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
