import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';

type EntityResponseType = HttpResponse<IEstReqCerrado>;
type EntityArrayResponseType = HttpResponse<IEstReqCerrado[]>;

@Injectable({ providedIn: 'root' })
export class EstReqCerradoService {
  public resourceUrl = SERVER_API_URL + 'api/est-req-cerrados';

  constructor(protected http: HttpClient) {}

  create(estReqCerrado: IEstReqCerrado): Observable<EntityResponseType> {
    return this.http.post<IEstReqCerrado>(this.resourceUrl, estReqCerrado, { observe: 'response' });
  }

  update(estReqCerrado: IEstReqCerrado): Observable<EntityResponseType> {
    return this.http.put<IEstReqCerrado>(this.resourceUrl, estReqCerrado, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstReqCerrado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstReqCerrado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
