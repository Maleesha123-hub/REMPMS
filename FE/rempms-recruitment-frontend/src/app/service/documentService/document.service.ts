import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Downloader } from '../../model/document/Downloader';

@Injectable({
  providedIn: 'root',
})
export class DocumentService {
  private apiUrl = environment.baseUrl + '/document/v1';

  constructor(private http: HttpClient) {}

  downloadImage(contentType: string, documentPaths: string[]) {
    let downloaderApi = this.apiUrl + '/downloader';

    const formdata = new FormData();

    const requests: Downloader[] = [];
    documentPaths.forEach((documentPath) => {
      const request: Downloader = {
        contentType: contentType,
        documentPath: documentPath,
      };
      requests.push(request);
    });

    const documentRequest = new Blob([JSON.stringify(requests)], {
      type: 'application/json',
    });

    // Append the JSON blob to FormData
    formdata.append('requests', documentRequest, 'requests.json');

    console.info(
      'DownloadImage method is calling for download poster image...',
    );

    return this.http.post(downloaderApi, formdata, { responseType: 'blob' });
  }

  /**
   * Handles HTTP request errors.
   * @param {any} error - The error object received from the HTTP request.
   * @returns {Observable<never>} - An observable that throws an error.
   * @author @maleeshasa
   */
  handleError(error: any): Observable<never> {
    // Return an observable that throws a new error with a message.
    return throwError(() => error);
  }
}
