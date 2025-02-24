import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable} from 'rxjs';
import {Showtime} from '../model/showtime.model';



@Injectable({
  providedIn: 'root'
})

export class ShowtimeService {
  private baseUrl = "http://localhost:8080/showtime"

  constructor(private http: HttpClient) {
  }


  getAll(time:string): Observable<Showtime[]> {
    const params = new HttpParams()
      .set('showtime', time)
    return this.http.get<Showtime[]>(`${this.baseUrl}`, {params})
  }

  createShowtime(data: Showtime): Observable<Showtime> {
    return this.http.post<Showtime>(`${this.baseUrl}`, data)
  }

  deleteById(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`)
  }

  updateById(id: string, body: Showtime): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, body)
  }

  getById(movieId: string): Observable<Showtime> {
    return this.http.get<Showtime>(`${this.baseUrl}/${movieId}`)
  }
}


