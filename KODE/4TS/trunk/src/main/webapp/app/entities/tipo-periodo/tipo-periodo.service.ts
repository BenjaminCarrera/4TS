import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';

type EntityResponseType = HttpResponse<ITipoPeriodo>;
type EntityArrayResponseType = HttpResponse<ITipoPeriodo[]>;

@Injectable({ providedIn: 'root' })
export class TipoPeriodoService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-periodos';

  constructor(protected http: HttpClient) {}

  create(tipoPeriodo: ITipoPeriodo): Observable<EntityResponseType> {
    return this.http.post<ITipoPeriodo>(this.resourceUrl, tipoPeriodo, { observe: 'response' });
  }

  update(tipoPeriodo: ITipoPeriodo): Observable<EntityResponseType> {
    return this.http.put<ITipoPeriodo>(this.resourceUrl, tipoPeriodo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoPeriodo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoPeriodo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
