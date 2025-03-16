import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Showtime} from '../model/showtime.model';
import {Movie} from '../model/movie.model';
import {Room, RoomType} from '../model/room.model';
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class ShowtimeService {
  private baseUrl = environment.apiUrl + "/showtime"

  constructor(private http: HttpClient) {
  }


  getAll(time: string, roomId: string, movieId: string): Observable<Showtime[]> {
    const params = new HttpParams()
      .set('showtime', time)
      .set('roomId', roomId)
      .set('movieId', movieId)
    return this.http.get<Showtime[]>(`${this.baseUrl}`, {params})
  }

  createShowtime(data: Showtime): Observable<Showtime> {
    return this.http.post<Showtime>(`${this.baseUrl}`, data)
  }

  deleteById(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`)
  }

  updateById(id: string, data: Showtime): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, data)
  }

  getById(movieId: string): Observable<Showtime> {
    return this.http.get<Showtime>(`${this.baseUrl}/${movieId}`)
  }
}


