import {ApplicationConfig, EnvironmentProviders, InjectionToken, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, HttpInterceptor, provideHttpClient} from '@angular/common/http';
import {AuthInterceptor} from '../service/auth.interceptor';
import {BrowserDynamicTestingModule} from '@angular/platform-browser-dynamic/testing';

export const appConfig: {
  providers: (EnvironmentProviders | BrowserDynamicTestingModule | {
    useClass: AuthInterceptor;
    provide: InjectionToken<readonly HttpInterceptor[]>;
    multi: boolean
  })[]
} = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    BrowserDynamicTestingModule,
    {
      provide:HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    }
  ]
};
