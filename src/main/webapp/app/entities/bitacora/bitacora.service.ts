import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBitacora } from 'app/shared/model/bitacora.model';

type EntityResponseType = HttpResponse<IBitacora>;
type EntityArrayResponseType = HttpResponse<IBitacora[]>;

@Injectable({ providedIn: 'root' })
export class BitacoraService {
  public resourceUrl = SERVER_API_URL + 'api/bitacoras';

  constructor(protected http: HttpClient) {}

  create(bitacora: IBitacora): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitacora);
    return this.http
      .post<IBitacora>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bitacora: IBitacora): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bitacora);
    return this.http
      .put<IBitacora>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBitacora>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBitacora[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bitacora: IBitacora): IBitacora {
    const copy: IBitacora = Object.assign({}, bitacora, {
      fecha: bitacora.fecha != null && bitacora.fecha.isValid() ? bitacora.fecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bitacora: IBitacora) => {
        bitacora.fecha = bitacora.fecha != null ? moment(bitacora.fecha) : null;
      });
    }
    return res;
  }
}
