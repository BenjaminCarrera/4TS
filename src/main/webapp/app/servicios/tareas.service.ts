import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { retry, catchError } from 'rxjs/operators';
import { Tarea } from '../clases/Tarea';
import { SERVER_API_URL } from 'app/app.constants';
@Injectable({
  providedIn: 'root'
})
export class TareaApi {
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
  getTareas(): Observable<Tarea> {
    return this.http.get<Tarea>(this.apiURL + '/tareas', this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API get() method => Fetch employee
  getTarea(id: number): Observable<Tarea> {
    return this.http.get<Tarea>(this.apiURL + '/tareas/' + id, this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API post() method => Create employee
  crearTarea(TareaValue: any): Observable<Tarea> {
    return this.http.post<Tarea>(this.apiURL + '/tareas', JSON.stringify(TareaValue), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API put() method => Update employee
  actualizarTarea(id: string, employee: any): Observable<Tarea> {
    return this.http.put<Tarea>(this.apiURL + '/tareas/' + id, JSON.stringify(Tarea), this.httpOptions)
    .pipe(
      retry(1),
      catchError(this.handleError)
    );
  }

  // HttpClient API delete() method => Delete employee
  eliminarTarea(id: string) {
    return this.http.delete<Tarea>(this.apiURL + '/tareas/' + id, this.httpOptions)
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
