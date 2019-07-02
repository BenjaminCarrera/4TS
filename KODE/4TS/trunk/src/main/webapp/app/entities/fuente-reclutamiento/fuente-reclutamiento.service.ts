import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';

type EntityResponseType = HttpResponse<IFuenteReclutamiento>;
type EntityArrayResponseType = HttpResponse<IFuenteReclutamiento[]>;

@Injectable({ providedIn: 'root' })
export class FuenteReclutamientoService {
  public resourceUrl = SERVER_API_URL + 'api/fuente-reclutamientos';

  constructor(protected http: HttpClient) {}

  create(fuenteReclutamiento: IFuenteReclutamiento): Observable<EntityResponseType> {
    return this.http.post<IFuenteReclutamiento>(this.resourceUrl, fuenteReclutamiento, { observe: 'response' });
  }

  update(fuenteReclutamiento: IFuenteReclutamiento): Observable<EntityResponseType> {
    return this.http.put<IFuenteReclutamiento>(this.resourceUrl, fuenteReclutamiento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuenteReclutamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuenteReclutamiento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
