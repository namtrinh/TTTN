import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from 'rxjs';
import {Ticket} from '../model/ticket.model';
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class TicketService {
  private baseUrl =environment.apiUrl + "/ticket"

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Ticket[]> {
    return this.http.get<Ticket[]>(`${this.baseUrl}`)
  }

  findById(id: string | null):Observable<Ticket>{
    return this.http.get<Ticket>(`${this.baseUrl}/${id}`)
  }
  createTicket(ticket: Ticket): Observable<Ticket> {
    return this.http.post<Ticket>(`${this.baseUrl}`, ticket)
  }

  updateById(id: string | null, body: Ticket): Observable<Ticket>{
    return this.http.put<Ticket>(`${this.baseUrl}/${id}`, body)
  }
}


