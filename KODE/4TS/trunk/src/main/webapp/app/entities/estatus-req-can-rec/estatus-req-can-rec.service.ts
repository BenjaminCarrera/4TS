import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';

type EntityResponseType = HttpResponse<IEstatusReqCanRec>;
type EntityArrayResponseType = HttpResponse<IEstatusReqCanRec[]>;

@Injectable({ providedIn: 'root' })
export class EstatusReqCanRecService {
  public resourceUrl = SERVER_API_URL + 'api/estatus-req-can-recs';

  constructor(protected http: HttpClient) {}

  create(estatusReqCanRec: IEstatusReqCanRec): Observable<EntityResponseType> {
    return this.http.post<IEstatusReqCanRec>(this.resourceUrl, estatusReqCanRec, { observe: 'response' });
  }

  update(estatusReqCanRec: IEstatusReqCanRec): Observable<EntityResponseType> {
    return this.http.put<IEstatusReqCanRec>(this.resourceUrl, estatusReqCanRec, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatusReqCanRec>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatusReqCanRec[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
