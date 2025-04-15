import { Injectable } from '@angular/core';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CountryService {
  private urlCountryApi = `${environment.baseUrl}` + '/location/v1/country';

  constructor(private httpClient: HttpClient) {}

  getAllActiveCountries(): Observable<any> {
    const url = this.urlCountryApi + '/getAllActive';

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveCountryById(idCountry: string) {
    const url = this.urlCountryApi + '/getById/' + idCountry;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
