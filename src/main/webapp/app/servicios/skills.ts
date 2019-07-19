// import { Injectable } from '@angular/core';
// import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { Observable, throwError } from 'rxjs';
// import { retry, catchError } from 'rxjs/operators';

// @Injectable({
//   providedIn: 'root'
// })

// export class RestApiService {
//   // Define API
//   apiURL = 'http://localhost:9000/api';
//   // Http Options
//   httpOptions = {
//     headers: new HttpHeaders({
//       'Content-Type': 'application/json',
//       'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU2NTQ1ODk0NH0.XcZuPd5gm2kk_m6gn9KKxE89HgERurMEgwnJEPoJbBAEZBZEUn1HRCHRHmqziaWOgg55r3mRSuPkth6_LSsXxQ'
//     })
//   };

//   constructor(private http: HttpClient) { }

//   // HttpClient API get() method => Fetch employees list
//   getskills(): Observable<Requerimiento> {
//     return this.http.get<Requerimiento>(this.apiURL + '/skills')
//     .pipe(
//       retry(1),
//       catchError(this.handleError)
//     );
//   }

//   // HttpClient API get() method => Fetch employee
//   getSkill(id: number): Observable<Requerimiento> {
//     return this.http.get<Requerimiento>(this.apiURL + '/skills/' + id, this.httpOptions)
//     .pipe(
//       retry(1),
//       catchError(this.handleError)
//     );
//   }

//   // HttpClient API post() method => Create employee
//   crearRequerimiento(Requerimiento): Observable<Requerimiento> {
//     return this.http.post<Requerimiento>(this.apiURL + '/skills', JSON.stringify(Requerimiento), this.httpOptions)
//     .pipe(
//       retry(1),
//       catchError(this.handleError)
//     );
//   }

//   // HttpClient API put() method => Update employee
//   actualizarSkill(id: string, employee: any): Observable<Requerimiento> {
//     return this.http.put<Requerimiento>(this.apiURL + '/skills/' + id, JSON.stringify(Requerimiento), this.httpOptions)
//     .pipe(
//       retry(1),
//       catchError(this.handleError)
//     );
//   }

//   // HttpClient API delete() method => Delete employee
//   eliminarSkill(id: string) {
//     return this.http.delete<Requerimiento>(this.apiURL + '/skills/' + id, this.httpOptions)
//     .pipe(
//       retry(1),
//       catchError(this.handleError)
//     );
//   }

//   // Error handling
//   handleError(error: { error: { message: string; }; status: any; message: any; }) {
//      let errorMessage = '';
//      if (error.error instanceof ErrorEvent) {
//        // Get client-side error
//        errorMessage = error.error.message;
//      } else {
//        // Get server-side error
//        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
//      }
//      window.alert(errorMessage);
//      return throwError(errorMessage);
//   }
// }
