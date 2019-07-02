import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoIngreso } from 'app/shared/model/tipo-ingreso.model';

type EntityResponseType = HttpResponse<ITipoIngreso>;
type EntityArrayResponseType = HttpResponse<ITipoIngreso[]>;

@Injectable({ providedIn: 'root' })
export class TipoIngresoService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-ingresos';

  constructor(protected http: HttpClient) {}

  create(tipoIngreso: ITipoIngreso): Observable<EntityResponseType> {
    return this.http.post<ITipoIngreso>(this.resourceUrl, tipoIngreso, { observe: 'response' });
  }

  update(tipoIngreso: ITipoIngreso): Observable<EntityResponseType> {
    return this.http.put<ITipoIngreso>(this.resourceUrl, tipoIngreso, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoIngreso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoIngreso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
