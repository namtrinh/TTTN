import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from 'rxjs';
import {Ticket} from '../model/ticket.model';


@Injectable({
  providedIn: 'root'
})

export class TicketService {
  private baseUrl = "http://localhost:8080/ticket"

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.baseUrl}`)
  }

  createTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(`${this.baseUrl}`, ticket)
  }

  updateById(id:string, body:Ticket): Observable<Ticket>{
    return this.http.put<Ticket>(`${this.baseUrl}/${id}`, body)
  }
}


