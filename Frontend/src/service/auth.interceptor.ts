import {Injectable} from '@angular/core';
import {HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError, BehaviorSubject} from 'rxjs';
import {catchError, switchMap, filter, take} from 'rxjs/operators';
import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private authService: AuthService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (
      request.url.includes('/auth/login') ||
      request.url.includes('/auth/refresh') ||
      request.url.includes('/auth/token') ||
      request.url.includes('/auth/logout') ||
      request.url.includes('/auth/verify_code') ||
      request.url.includes('/auth/forgot-password') ||
      request.url.includes('/auth/reset-password')) {
      return next.handle(request);
    }

    const token = this.authService.getToken();

    if (token) {
      request = this.addToken(request, token);
    }

    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          if (!this.isRefreshing && token != null) {
            this.isRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.refreshToken(token).pipe(
              switchMap((refreshResponse: any) => {
                this.isRefreshing = false;
                this.refreshTokenSubject.next(refreshResponse.result.token);
                return next.handle(this.addToken(request, refreshResponse.result.token));
              }),

              catchError((refreshError) => {
                this.isRefreshing = false;
                this.authService.removeToken();
                console.error('Failed to refresh token', refreshError);
                return throwError(refreshError);
              })
            );
          } else {
            return this.refreshTokenSubject.pipe(
              filter(token => token !== null),
              take(1),
              switchMap(token => {
                return next.handle(this.addToken(request, token));
              })
            );
          }
        } else {
          return throwError(error);
        }
      })
    );
  }

  private addToken(request: HttpRequest<any>, token: string): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
}
