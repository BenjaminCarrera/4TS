import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';

type EntityResponseType = HttpResponse<IEsqContRec>;
type EntityArrayResponseType = HttpResponse<IEsqContRec[]>;

@Injectable({ providedIn: 'root' })
export class EsqContRecService {
  public resourceUrl = SERVER_API_URL + 'api/esq-cont-recs';

  constructor(protected http: HttpClient) {}

  create(esqContRec: IEsqContRec): Observable<EntityResponseType> {
    return this.http.post<IEsqContRec>(this.resourceUrl, esqContRec, { observe: 'response' });
  }

  update(esqContRec: IEsqContRec): Observable<EntityResponseType> {
    return this.http.put<IEsqContRec>(this.resourceUrl, esqContRec, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEsqContRec>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEsqContRec[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
