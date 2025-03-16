import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from 'rxjs';
import {Order} from '../model/order.model';
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class OrderService {
  private baseUrl = environment.apiUrl + "/order"

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.baseUrl}`)
  }

  createOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.baseUrl}`, order)
  }
}


