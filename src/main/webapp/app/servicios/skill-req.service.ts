import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { SkillReq } from '../clases/skill-req';
import { SERVER_API_URL } from 'app/app.constants';
@Injectable({
  providedIn: 'root'
})
export class SkillReqService {
  // Define API
  apiURL = SERVER_API_URL + '/api';
  // Http Options
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU2NTQ1ODk0NH0.XcZuPd5gm2kk_m6gn9KKxE89HgERurMEgwnJEPoJbBAEZBZEUn1HRCHRHmqziaWOgg55r3mRSuPkth6_LSsXxQ'
    })
  };

  constructor(private http: HttpClient) { }

  // HttpClient API get() method => Fetch employees list
  getSkillReq(): Observable<SkillReq> {
    return this.http.get<SkillReq>(this.apiURL + '/skill-requerimientos', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API get() method => Fetch employee
  getSkillReqs(id: number): Observable<SkillReq> {
    return this.http.get<SkillReq>(this.apiURL + '/skill-requerimientos/' + id, this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API post() method => Create employee
  crearSkillReq(SkillReqValue: any): Observable<SkillReq> {
    return this.http.post<SkillReq>(this.apiURL + '/skill-requerimientos', JSON.stringify(SkillReqValue), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API put() method => Update employee
  actualizarSkillReq(id: string, Skillreq: any): Observable<SkillReq> {
    return this.http.put<SkillReq>(this.apiURL + '/skill-requerimientos/' + id, JSON.stringify(Skillreq), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API delete() method => Delete employee
  eliminarSkillReq(id: string) {
    return this.http.delete<SkillReq>(this.apiURL + '/skill-requerimientos/' + id, this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // Error handling
  handleError(error: { error: { message: string; }; status: any; message: any; }) {
     let errorMessage = '';
     if (error.error instanceof ErrorEvent) {
       // Get client-side error
       errorMessage = error.error.message;
     } else {
       // Get server-side error
       errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
     }
     window.alert(errorMessage);
     return throwError(errorMessage);
  }
}
