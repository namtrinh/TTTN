import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Movie} from '../model/movie.model';
import {Observable} from 'rxjs';
import {MovieResponse} from '../modelDto/response/movie.response';

@Injectable({
  providedIn:'root'
})

export class MovieService{
  private baseUrl = "http://localhost:8080/movie"

  constructor(private http: HttpClient){}

  getAll():Observable<MovieResponse[]>{
    return this.http.get<MovieResponse[]>(`${this.baseUrl}`)
  }

  getById(movieId:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findById/${movieId}`)
  }

  getByTitle(title:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findByTitle/${title}`)
  }
}

