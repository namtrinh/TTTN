import { TestBed } from '@angular/core/testing';

import {AuthInterceptor} from './auth.interceptor';
import {HttpClientModule} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';



describe('AuthInterceptor', () => {
  let service: AuthInterceptor;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[HttpClientModule],
      providers:[
        {
          provide:AuthInterceptor
        }
      ]});
    service = TestBed.inject(AuthInterceptor);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
