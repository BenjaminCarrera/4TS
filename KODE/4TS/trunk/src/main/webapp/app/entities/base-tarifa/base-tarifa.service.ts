import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBaseTarifa } from 'app/shared/model/base-tarifa.model';

type EntityResponseType = HttpResponse<IBaseTarifa>;
type EntityArrayResponseType = HttpResponse<IBaseTarifa[]>;

@Injectable({ providedIn: 'root' })
export class BaseTarifaService {
  public resourceUrl = SERVER_API_URL + 'api/base-tarifas';

  constructor(protected http: HttpClient) {}

  create(baseTarifa: IBaseTarifa): Observable<EntityResponseType> {
    return this.http.post<IBaseTarifa>(this.resourceUrl, baseTarifa, { observe: 'response' });
  }

  update(baseTarifa: IBaseTarifa): Observable<EntityResponseType> {
    return this.http.put<IBaseTarifa>(this.resourceUrl, baseTarifa, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBaseTarifa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBaseTarifa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
