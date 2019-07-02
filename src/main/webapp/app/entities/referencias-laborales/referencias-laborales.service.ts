import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';

type EntityResponseType = HttpResponse<IReferenciasLaborales>;
type EntityArrayResponseType = HttpResponse<IReferenciasLaborales[]>;

@Injectable({ providedIn: 'root' })
export class ReferenciasLaboralesService {
  public resourceUrl = SERVER_API_URL + 'api/referencias-laborales';

  constructor(protected http: HttpClient) {}

  create(referenciasLaborales: IReferenciasLaborales): Observable<EntityResponseType> {
    return this.http.post<IReferenciasLaborales>(this.resourceUrl, referenciasLaborales, { observe: 'response' });
  }

  update(referenciasLaborales: IReferenciasLaborales): Observable<EntityResponseType> {
    return this.http.put<IReferenciasLaborales>(this.resourceUrl, referenciasLaborales, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferenciasLaborales>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferenciasLaborales[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
