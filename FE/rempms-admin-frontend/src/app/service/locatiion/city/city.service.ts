import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";

@Injectable({
    providedIn: 'root'
})
export class CityService {

    private urlCityApi = `${environment.baseUrl}` + "/v1/location/city";

    constructor(private httpClient: HttpClient) {
    }

    saveUpdateCity(cityFormValues: any): Observable<any> {
        const url = this.urlCityApi + '/saveUpdate';
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            })
        }
        return this.httpClient.post<CommonResponse>(url, cityFormValues, httpOptions);
    }

    getAllActiveCities(): Observable<any> {
        const url = this.urlCityApi + '/getAllActive';
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            })
        }
        return this.httpClient.get<CommonResponse>(url, httpOptions);
    }

    getActiveCityById(idCity: string): Observable<any> {
        const url = this.urlCityApi + '/getById/' + idCity;
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            })
        }
        return this.httpClient.get<CommonResponse>(url, httpOptions);
    }

    deleteCityById(idCity: string): Observable<any> {
        const url = this.urlCityApi + '/deleteById/' + idCity;
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            })
        }
        return this.httpClient.get<CommonResponse>(url, httpOptions);
    }

    getByIdCountryProvinceAndDistrict(selectedCountryId: string, selectedProvinceId: string, selectedDistrictId: string): Observable<any> {
        const url = this.urlCityApi + '/getByIdCountryProvinceAndDistrict/' + selectedCountryId + '/' + selectedProvinceId + '/' + selectedDistrictId;
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json',
            })
        }
        return this.httpClient.get<CommonResponse>(url, httpOptions);
    }
}
