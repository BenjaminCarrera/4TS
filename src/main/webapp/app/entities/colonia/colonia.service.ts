import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IColonia } from 'app/shared/model/colonia.model';

type EntityResponseType = HttpResponse<IColonia>;
type EntityArrayResponseType = HttpResponse<IColonia[]>;

@Injectable({ providedIn: 'root' })
export class ColoniaService {
  public resourceUrl = SERVER_API_URL + 'api/colonias';

  constructor(protected http: HttpClient) {}

  create(colonia: IColonia): Observable<EntityResponseType> {
    return this.http.post<IColonia>(this.resourceUrl, colonia, { observe: 'response' });
  }

  update(colonia: IColonia): Observable<EntityResponseType> {
    return this.http.put<IColonia>(this.resourceUrl, colonia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IColonia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IColonia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
