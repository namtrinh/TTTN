import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginRequest} from '../modelDto/LoginRequest';


@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http: HttpClient) {
  }

  private baseUrl = "http://localhost:8080/auth";

  login(email: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/login`, {email, password})
  }

  register(email: string, password: string) {
    return this.http.post<any>(`${this.baseUrl}/register`, {email, password})
  }

  verifyCode(email:string, authCode:number){
    return this.http.post<any>(`${this.baseUrl}/verify_code`, {email, authCode})
  }

  forgotPassword(email: string) {
    const params = new HttpParams()
      .set('email', email);
    return this.http.post(`${this.baseUrl}/forgot-password`, {}, { params });
  }


  resetPassword(email:string, password:string, seret_key: string) {
    const params = new HttpParams()
      .set("reset_key", seret_key);
    return this.http.post(`${this.baseUrl}/reset-password`, {email, password}, {params})
  }

  logout(token: string) {
    return this.http.post(`${this.baseUrl}/logout`, {token})
  }

  refreshToken(token: string) {
    return this.http.post(`${this.baseUrl}/refresh`, {token})
  }

  removeToken() {
    localStorage.removeItem('auth_token')
  }

  getToken() {
    return localStorage.getItem('auth_token')
  }


}
