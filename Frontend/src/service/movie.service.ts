import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Movie} from '../model/movie.model';
import {Observable} from 'rxjs';
import {MovieResponse} from '../modelDto/response/movie.response';
import {environment} from '../environments/environment';

@Injectable({
  providedIn:'root'
})

export class MovieService{
  private baseUrl = environment.apiUrl + "/movie"

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

  deleteById(id:string){
    return this.http.delete(`${this.baseUrl}/${id}`)
  }

  updateById(id:string, movie:FormData):Observable<any>{
    return this.http.put<any>(`${this.baseUrl}/${id}`, movie)
  }

  getById(movieId:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findById/${movieId}`)
  }

  getByTitle(title:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findByTitle/${title}`)
  }
}

