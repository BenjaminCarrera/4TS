import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';

type EntityResponseType = HttpResponse<IEstatusLaboral>;
type EntityArrayResponseType = HttpResponse<IEstatusLaboral[]>;

@Injectable({ providedIn: 'root' })
export class EstatusLaboralService {
  public resourceUrl = SERVER_API_URL + 'api/estatus-laborals';

  constructor(protected http: HttpClient) {}

  create(estatusLaboral: IEstatusLaboral): Observable<EntityResponseType> {
    return this.http.post<IEstatusLaboral>(this.resourceUrl, estatusLaboral, { observe: 'response' });
  }

  update(estatusLaboral: IEstatusLaboral): Observable<EntityResponseType> {
    return this.http.put<IEstatusLaboral>(this.resourceUrl, estatusLaboral, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatusLaboral>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatusLaboral[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
