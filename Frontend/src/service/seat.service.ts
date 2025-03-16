import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Showtime} from '../model/showtime.model';
import {Movie} from '../model/movie.model';
import {Room, RoomType} from '../model/room.model';
import {Seat} from '../model/seat.model';
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class SeatService {
  private baseUrl = environment.apiUrl +"/seat"


  constructor(private http: HttpClient) {
  }

  getAll(showtimeId:string): Observable<Seat[]> {
    const params = new HttpParams()
      .set('showtimeId', showtimeId)
    return this.http.get<Seat[]>(`${this.baseUrl}`, {params})
  }

  updateStatusSeat(seatId:string, body:Seat):Observable<Seat>{
    return this.http.put<Seat>(`${this.baseUrl}/${seatId}`, body)
  }

}


