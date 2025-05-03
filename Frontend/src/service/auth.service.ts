import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginRequest} from '../modelDto/LoginRequest';
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http: HttpClient) {
  }

  private baseUrl = environment.apiUrl + "/auth";

  login(email: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/login`, {email, password})
  }

  register(email: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/register`, {email, password})
  }

  verifyCode(email:string, authCode:number){
    return this.http.post<any>(`${this.baseUrl}/verify_code`, {email, authCode},{withCredentials: true})
  }

  forgotPassword(email: string) {
    const params = new HttpParams()
      .set('email', email);
    return this.http.post(`${this.baseUrl}/forgot-password`, {withCredentials:true}, { params } );
  }


  resetPassword(email:string, password:string, seret_key: string) {
    const params = new HttpParams()
      .set("reset_key", seret_key);
    return this.http.post(`${this.baseUrl}/reset-password`, {email, password}, {params, withCredentials:true}, )
  }
}
