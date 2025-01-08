import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Movie} from '../model/movie.model';
import {Observable} from 'rxjs';
import {MovieResponse} from '../modelDto/response/movie.response';

@Injectable({
  providedIn:'root'
})

export class MovieService{
  private baseUrl = "http://localhost:8080/movie"

  constructor(private http: HttpClient){}

  getTop4():Observable<MovieResponse[]>{
    return this.http.get<MovieResponse[]>(`${this.baseUrl}`)
  }

  getAll(page:number, size:number):Observable<Movie[]>{
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
    return this.http.get<Movie[]>(`${this.baseUrl}/manage/movie`, { params})
  }

  createMovie(movie: FormData): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, movie); // Truyền trực tiếp FormData
  }


  getById(movieId:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findById/${movieId}`)
  }

  getByTitle(title:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findByTitle/${title}`)
  }
}

