import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';

type EntityResponseType = HttpResponse<IPrioridadReq>;
type EntityArrayResponseType = HttpResponse<IPrioridadReq[]>;

@Injectable({ providedIn: 'root' })
export class PrioridadReqService {
  public resourceUrl = SERVER_API_URL + 'api/prioridad-reqs';

  constructor(protected http: HttpClient) {}

  create(prioridadReq: IPrioridadReq): Observable<EntityResponseType> {
    return this.http.post<IPrioridadReq>(this.resourceUrl, prioridadReq, { observe: 'response' });
  }

  update(prioridadReq: IPrioridadReq): Observable<EntityResponseType> {
    return this.http.put<IPrioridadReq>(this.resourceUrl, prioridadReq, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrioridadReq>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrioridadReq[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
