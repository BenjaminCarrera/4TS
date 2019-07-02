import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusAcademico } from 'app/shared/model/estatus-academico.model';

type EntityResponseType = HttpResponse<IEstatusAcademico>;
type EntityArrayResponseType = HttpResponse<IEstatusAcademico[]>;

@Injectable({ providedIn: 'root' })
export class EstatusAcademicoService {
  public resourceUrl = SERVER_API_URL + 'api/estatus-academicos';

  constructor(protected http: HttpClient) {}

  create(estatusAcademico: IEstatusAcademico): Observable<EntityResponseType> {
    return this.http.post<IEstatusAcademico>(this.resourceUrl, estatusAcademico, { observe: 'response' });
  }

  update(estatusAcademico: IEstatusAcademico): Observable<EntityResponseType> {
    return this.http.put<IEstatusAcademico>(this.resourceUrl, estatusAcademico, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatusAcademico>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatusAcademico[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
