import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICuenta } from 'app/shared/model/cuenta.model';

type EntityResponseType = HttpResponse<ICuenta>;
type EntityArrayResponseType = HttpResponse<ICuenta[]>;

@Injectable({ providedIn: 'root' })
export class CuentaService {
  public resourceUrl = SERVER_API_URL + 'api/cuentas';

  constructor(protected http: HttpClient) {}

  create(cuenta: ICuenta): Observable<EntityResponseType> {
    return this.http.post<ICuenta>(this.resourceUrl, cuenta, { observe: 'response' });
  }

  update(cuenta: ICuenta): Observable<EntityResponseType> {
    return this.http.put<ICuenta>(this.resourceUrl, cuenta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICuenta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICuenta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
