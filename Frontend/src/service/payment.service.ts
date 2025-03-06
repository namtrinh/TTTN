import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root',
})

export class MomoPaymentService{

  constructor(private http:HttpClient) {
  }
  private baseUrl:string = "http://localhost:8080/api/v1"

  createOrder(body: { amount: number; extraData: string; orderInfo: string }):Observable<any>{
     return this.http.post<any>(`${this.baseUrl}/momo-payment`, body)
  }
}
