import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

type EntityResponseType = HttpResponse<IRequerimiento>;
type EntityArrayResponseType = HttpResponse<IRequerimiento[]>;

@Injectable({ providedIn: 'root' })
export class RequerimientoService {
  public resourceUrl = SERVER_API_URL + 'api/requerimientos';

  constructor(protected http: HttpClient) { }

  create(requerimiento: IRequerimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimiento);
    return this.http
      .post<IRequerimiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(requerimiento: IRequerimiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(requerimiento);
    return this.http
      .put<IRequerimiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRequerimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRequerimiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(requerimiento: IRequerimiento): IRequerimiento {
    const copy: IRequerimiento = Object.assign({}, requerimiento, {
      // fechaAlda: requerimiento.fechaAlda != null && requerimiento.fechaAlda.isValid() ? requerimiento.fechaAlda.toJSON() : null,
      // fechaResolucion:
      //   requerimiento.fechaResolucion != null && requerimiento.fechaResolucion.isValid() ? requerimiento.fechaResolucion.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlda = res.body.fechaAlda != null ? moment(res.body.fechaAlda) : null;
      res.body.fechaResolucion = res.body.fechaResolucion != null ? moment(res.body.fechaResolucion) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((requerimiento: IRequerimiento) => {
        requerimiento.fechaAlda = requerimiento.fechaAlda != null ? moment(requerimiento.fechaAlda) : null;
        requerimiento.fechaResolucion = requerimiento.fechaResolucion != null ? moment(requerimiento.fechaResolucion) : null;
      });
    }
    return res;
  }
}
