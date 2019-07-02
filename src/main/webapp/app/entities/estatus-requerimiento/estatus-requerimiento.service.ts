import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';

type EntityResponseType = HttpResponse<IEstatusRequerimiento>;
type EntityArrayResponseType = HttpResponse<IEstatusRequerimiento[]>;

@Injectable({ providedIn: 'root' })
export class EstatusRequerimientoService {
  public resourceUrl = SERVER_API_URL + 'api/estatus-requerimientos';

  constructor(protected http: HttpClient) {}

  create(estatusRequerimiento: IEstatusRequerimiento): Observable<EntityResponseType> {
    return this.http.post<IEstatusRequerimiento>(this.resourceUrl, estatusRequerimiento, { observe: 'response' });
  }

  update(estatusRequerimiento: IEstatusRequerimiento): Observable<EntityResponseType> {
    return this.http.put<IEstatusRequerimiento>(this.resourceUrl, estatusRequerimiento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatusRequerimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatusRequerimiento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
