import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import Swal from 'sweetalert2';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  private jwtHelperService = new JwtHelperService();

  constructor(private router: Router) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler,
  ): Observable<HttpEvent<any>> {
    if (this.isTokenEndpoint(request) || this.isNonAdUserEndpoint(request)) {
      return next.handle(request);
    }

    return this.handleRequestWithToken(request, next);
  }

  private handleRequestWithToken(
    request: HttpRequest<any>,
    next: HttpHandler,
  ): Observable<HttpEvent<any>> {
    const token = sessionStorage.getItem('accessToken');
    const authRequest = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });

    return this.handleAccess(authRequest, next);
  }

  private handleAccess(authRequest: HttpRequest<any>, next: HttpHandler) {
    const accessToken = sessionStorage.getItem('accessToken');
    if (accessToken !== null) {
      const tokenExpiredTime =
        this.jwtHelperService.getTokenExpirationDate(accessToken);
      const dateNow = new Date();
      const timeDifferenceMinutes =
        tokenExpiredTime !== null
          ? Math.floor(
              (tokenExpiredTime.getTime() - dateNow.getTime()) / (1000 * 60),
            )
          : 0;

      if (timeDifferenceMinutes <= 5) {
        Swal.fire({
          title: 'Timeout',
          html: 'Your Session Has Timed Out. <br/> Please LogIn. ',
          icon: 'warning',
          showCancelButton: false,
          confirmButtonText: 'LogIn',
        }).then((result) => {
          if (result.isConfirmed) {
            sessionStorage.clear();
            setTimeout(() => {
              this.router.navigate(['/login']).then(() => {
                window.location.reload();
              });
            }, 2000);
          }
        });
      }
    }

    // Log the headers of the request

    console.log('Request Headers:', authRequest.headers.keys());
    console.log(authRequest.headers.get('Authorization'));

    return next.handle(authRequest);
  }

  private isTokenEndpoint(request: HttpRequest<any>): boolean {
    return request.url.includes('/v1/auth/user/token');
  }

  private isNonAdUserEndpoint(request: HttpRequest<any>): boolean {
    return request.url.includes('/v1/user/non-ad/create');
  }
}
