import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';

type EntityResponseType = HttpResponse<IEstCanInactivo>;
type EntityArrayResponseType = HttpResponse<IEstCanInactivo[]>;

@Injectable({ providedIn: 'root' })
export class EstCanInactivoService {
  public resourceUrl = SERVER_API_URL + 'api/est-can-inactivos';

  constructor(protected http: HttpClient) {}

  create(estCanInactivo: IEstCanInactivo): Observable<EntityResponseType> {
    return this.http.post<IEstCanInactivo>(this.resourceUrl, estCanInactivo, { observe: 'response' });
  }

  update(estCanInactivo: IEstCanInactivo): Observable<EntityResponseType> {
    return this.http.put<IEstCanInactivo>(this.resourceUrl, estCanInactivo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstCanInactivo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstCanInactivo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
