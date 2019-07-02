import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';

type EntityResponseType = HttpResponse<IEsquemaContratacionKode>;
type EntityArrayResponseType = HttpResponse<IEsquemaContratacionKode[]>;

@Injectable({ providedIn: 'root' })
export class EsquemaContratacionKodeService {
  public resourceUrl = SERVER_API_URL + 'api/esquema-contratacion-kodes';

  constructor(protected http: HttpClient) {}

  create(esquemaContratacionKode: IEsquemaContratacionKode): Observable<EntityResponseType> {
    return this.http.post<IEsquemaContratacionKode>(this.resourceUrl, esquemaContratacionKode, { observe: 'response' });
  }

  update(esquemaContratacionKode: IEsquemaContratacionKode): Observable<EntityResponseType> {
    return this.http.put<IEsquemaContratacionKode>(this.resourceUrl, esquemaContratacionKode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEsquemaContratacionKode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEsquemaContratacionKode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
