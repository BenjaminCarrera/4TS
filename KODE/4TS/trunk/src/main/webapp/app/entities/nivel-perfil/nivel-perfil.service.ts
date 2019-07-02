import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';

type EntityResponseType = HttpResponse<INivelPerfil>;
type EntityArrayResponseType = HttpResponse<INivelPerfil[]>;

@Injectable({ providedIn: 'root' })
export class NivelPerfilService {
  public resourceUrl = SERVER_API_URL + 'api/nivel-perfils';

  constructor(protected http: HttpClient) {}

  create(nivelPerfil: INivelPerfil): Observable<EntityResponseType> {
    return this.http.post<INivelPerfil>(this.resourceUrl, nivelPerfil, { observe: 'response' });
  }

  update(nivelPerfil: INivelPerfil): Observable<EntityResponseType> {
    return this.http.put<INivelPerfil>(this.resourceUrl, nivelPerfil, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INivelPerfil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INivelPerfil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
