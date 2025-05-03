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
    return this.http.get<MovieResponse[]>(`${this.baseUrl}`, {withCredentials: true})
  }

  getAll(page:number, size:number):Observable<Movie[]>{
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
    return this.http.get<Movie[]>(`${this.baseUrl}/manage/movie`, { params, withCredentials: true})
  }

  createMovie(movie: FormData): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, movie, {withCredentials: true}); // Truyền trực tiếp FormData
  }

  deleteById(id:string){
    return this.http.delete(`${this.baseUrl}/${id}`,{withCredentials: true})
  }

  updateById(id:string, movie:FormData):Observable<any>{
    return this.http.put<any>(`${this.baseUrl}/${id}`, movie,{withCredentials: true})
  }

  getById(movieId:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findById/${movieId}`,{withCredentials: true})
  }

  getByTitle(title:string):Observable<Movie>{
    return this.http.get<Movie>(`${this.baseUrl}/findByTitle/${title}`,{withCredentials: true})
  }
}

