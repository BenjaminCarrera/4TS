import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPermiso } from 'app/shared/model/permiso.model';

type EntityResponseType = HttpResponse<IPermiso>;
type EntityArrayResponseType = HttpResponse<IPermiso[]>;

@Injectable({ providedIn: 'root' })
export class PermisoService {
  public resourceUrl = SERVER_API_URL + 'api/permisos';

  constructor(protected http: HttpClient) {}

  create(permiso: IPermiso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(permiso);
    return this.http
      .post<IPermiso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(permiso: IPermiso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(permiso);
    return this.http
      .put<IPermiso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPermiso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPermiso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(permiso: IPermiso): IPermiso {
    const copy: IPermiso = Object.assign({}, permiso, {
      createdDate: permiso.createdDate != null && permiso.createdDate.isValid() ? permiso.createdDate.toJSON() : null,
      lastModifiedDate: permiso.lastModifiedDate != null && permiso.lastModifiedDate.isValid() ? permiso.lastModifiedDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((permiso: IPermiso) => {
        permiso.createdDate = permiso.createdDate != null ? moment(permiso.createdDate) : null;
        permiso.lastModifiedDate = permiso.lastModifiedDate != null ? moment(permiso.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
