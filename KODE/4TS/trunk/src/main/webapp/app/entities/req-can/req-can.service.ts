import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReqCan } from 'app/shared/model/req-can.model';

type EntityResponseType = HttpResponse<IReqCan>;
type EntityArrayResponseType = HttpResponse<IReqCan[]>;

@Injectable({ providedIn: 'root' })
export class ReqCanService {
  public resourceUrl = SERVER_API_URL + 'api/req-cans';

  constructor(protected http: HttpClient) {}

  create(reqCan: IReqCan): Observable<EntityResponseType> {
    return this.http.post<IReqCan>(this.resourceUrl, reqCan, { observe: 'response' });
  }

  update(reqCan: IReqCan): Observable<EntityResponseType> {
    return this.http.put<IReqCan>(this.resourceUrl, reqCan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReqCan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReqCan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
