import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPermisoAuthority } from 'app/shared/model/permiso-authority.model';

type EntityResponseType = HttpResponse<IPermisoAuthority>;
type EntityArrayResponseType = HttpResponse<IPermisoAuthority[]>;

@Injectable({ providedIn: 'root' })
export class PermisoAuthorityService {
  public resourceUrl = SERVER_API_URL + 'api/permiso-authorities';

  constructor(protected http: HttpClient) {}

  create(permisoAuthority: IPermisoAuthority): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(permisoAuthority);
    return this.http
      .post<IPermisoAuthority>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(permisoAuthority: IPermisoAuthority): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(permisoAuthority);
    return this.http
      .put<IPermisoAuthority>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPermisoAuthority>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPermisoAuthority[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(permisoAuthority: IPermisoAuthority): IPermisoAuthority {
    const copy: IPermisoAuthority = Object.assign({}, permisoAuthority, {
      createdDate:
        permisoAuthority.createdDate != null && permisoAuthority.createdDate.isValid() ? permisoAuthority.createdDate.toJSON() : null,
      lastModifiedDate:
        permisoAuthority.lastModifiedDate != null && permisoAuthority.lastModifiedDate.isValid()
          ? permisoAuthority.lastModifiedDate.toJSON()
          : null
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
      res.body.forEach((permisoAuthority: IPermisoAuthority) => {
        permisoAuthority.createdDate = permisoAuthority.createdDate != null ? moment(permisoAuthority.createdDate) : null;
        permisoAuthority.lastModifiedDate = permisoAuthority.lastModifiedDate != null ? moment(permisoAuthority.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
