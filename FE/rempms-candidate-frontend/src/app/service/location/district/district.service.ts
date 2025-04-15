import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DistrictService {
  private urlDistrictApi = `${environment.baseUrl}` + '/location/v1/district';

  constructor(private httpClient: HttpClient) {}

  getAllActiveDistrictsByCountryAndProvince(
    idProvince: string,
    idCountry: any,
  ) {
    const url =
      this.urlDistrictApi +
      '/getDistrictsByCountryIdAndProvinceId/' +
      idProvince +
      '/' +
      idCountry;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveDistrictById(idDistrict: string): Observable<any> {
    const url = this.urlDistrictApi + '/getById/' + idDistrict;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
