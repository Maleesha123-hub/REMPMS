import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { CommonResponse } from '../../../model/commonResponse/CommonResponse';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CityService {
  private urlCityApi = `${environment.baseUrl}` + '/location/v1/city';

  constructor(private httpClient: HttpClient) {}

  getAllActiveCitiesByCountryProvinceAndDistrict(
    selectedCountryId: any,
    selectedProvinceId: any,
    selectedDistrictId: any,
  ) {
    const url =
      this.urlCityApi +
      '/getByIdCountryProvinceAndDistrict/' +
      selectedCountryId +
      '/' +
      selectedProvinceId +
      '/' +
      selectedDistrictId;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveCityById(idCity: string): Observable<any> {
    const url = this.urlCityApi + '/getById/' + idCity;

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
