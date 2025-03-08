import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn:'root'
})

export class EmailService{

  private baseUrl:string = "http://localhost:8080/email"
  constructor(private http:HttpClient) {
  }

  sendEmail(body: {to: string, subject: string, body: string, file: File}): Observable<any> {
    const formData = new FormData();
    formData.append('to', body.to);
    formData.append('subject', body.subject);
    formData.append('body', body.body);
    formData.append('file', body.file);

    return this.http.post<any>(`${this.baseUrl}/send-with-attachment`, formData);
  }
}
