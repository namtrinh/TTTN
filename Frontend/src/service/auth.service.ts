import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {UserLogin} from '../modelDto/userLogin';

@Injectable({
  providedIn:'root'
  })

export class AuthService{

  constructor(private http:HttpClient) {
  }

  private baseUrl = "htt:localhost:8080/";

  login(username:string, password:string):Observable<UserLogin>{
    return this.http.post<UserLogin>(`${this.baseUrl}`,{username, password})
  }
}
