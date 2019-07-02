import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';

type EntityResponseType = HttpResponse<IEstatusCandidato>;
type EntityArrayResponseType = HttpResponse<IEstatusCandidato[]>;

@Injectable({ providedIn: 'root' })
export class EstatusCandidatoService {
  public resourceUrl = SERVER_API_URL + 'api/estatus-candidatoes';

  constructor(protected http: HttpClient) {}

  create(estatusCandidato: IEstatusCandidato): Observable<EntityResponseType> {
    return this.http.post<IEstatusCandidato>(this.resourceUrl, estatusCandidato, { observe: 'response' });
  }

  update(estatusCandidato: IEstatusCandidato): Observable<EntityResponseType> {
    return this.http.put<IEstatusCandidato>(this.resourceUrl, estatusCandidato, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatusCandidato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatusCandidato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
