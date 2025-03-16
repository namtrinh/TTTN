import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable} from 'rxjs';
import {Room} from '../model/room.model';
import {environment} from '../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class RoomService {
  private baseUrl = environment.apiUrl + "/room"

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Room[]> {
    return this.http.get<Room[]>(`${this.baseUrl}`)
  }

  createRoom(room: Room): Observable<Room> {
    return this.http.post<Room>(`${this.baseUrl}`, room)
  }

  deleteById(id: string) {
    return this.http.delete(`${this.baseUrl}/${id}`)
  }

  updateById(id: string, body: Room): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, body)
  }

  getById(movieId: string): Observable<Room> {
    return this.http.get<Room>(`${this.baseUrl}/${movieId}`)
  }
}


