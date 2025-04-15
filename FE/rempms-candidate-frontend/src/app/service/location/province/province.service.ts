import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProvinceService {
  private urlProvinceApi = `${environment.baseUrl}` + '/location/v1/province';

  constructor(private httpClient: HttpClient) {}

  getAllActiveProvincesByCountry(idCountry: string): Observable<any> {
    const url = this.urlProvinceApi + '/getByCountryId/' + idCountry;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveProvinceById(idProvince: string): Observable<any> {
    {
      const url = this.urlProvinceApi + '/getById/' + idProvince;

      const httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json',
        }),
      };

      return this.httpClient.get<CommonResponse>(url, httpOptions);
    }
  }
}
