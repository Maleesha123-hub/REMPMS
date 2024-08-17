import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {CommonResponse} from "../../../model/commonResponse/CommonResponse";

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  private urlLanguageApi = `${environment.baseUrl}` + "/v1/communication/language";

  constructor(private httpClient: HttpClient) {
  }

  saveUpdateLanguage(languageFormValues: any): Observable<any> {
    const url = this.urlLanguageApi + '/saveUpdate';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.post<CommonResponse>(url, languageFormValues, httpOptions);
  }

  getActiveLanguageList(): Observable<any> {
    const url = this.urlLanguageApi + '/getAllActive';
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  getActiveLanguageById(idLanguage: string): Observable<any> {
    const url = this.urlLanguageApi + '/getById/' + idLanguage;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }

  deleteLanguageById(idLanguage: string): Observable<any> {
    const url = this.urlLanguageApi + '/deleteById/' + idLanguage;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }
    return this.httpClient.get<CommonResponse>(url, httpOptions);
  }
}
