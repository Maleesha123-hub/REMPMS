import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {FormGroup} from "@angular/forms";
import {District} from "../../../model/location/district/District";
import {Country} from "../../../model/location/country/Country";
import {Province} from "../../../model/location/province/Province";
import {Observable} from "rxjs";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DistrictService {

  private urlDistrictApi = `${environment.baseUrl}` + "/v1/location/district";

  districtForm: FormGroup | any;
  district: District | any;
  districts: District[] | any;
  countries: Country[] | any;
  provinces: Province[] | any;

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateDistrict(districtFormValues: any): Observable<any> {
    const url = this.urlDistrictApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, districtFormValues, httpOptions);
  }

  getActiveDistrictById(idDistrict: string): Observable<any> {
    const url = this.urlDistrictApi + '/getById/' + idDistrict;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getAllActiveDistricts(): Observable<any> {
    const url = this.urlDistrictApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteDistrictById(idDistrict: string): Observable<any> {
    const url = this.urlDistrictApi + '/deleteById/' + idDistrict;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getDistrictsByCountryIdAndProvinceId(idProvince: string, idCountry: string): Observable<any> {
    const url = this.urlDistrictApi + '/getDistrictsByCountryIdAndProvinceId/' + idProvince + '/' + idCountry;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
