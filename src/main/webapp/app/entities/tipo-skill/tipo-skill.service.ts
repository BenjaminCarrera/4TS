import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoSkill } from 'app/shared/model/tipo-skill.model';

type EntityResponseType = HttpResponse<ITipoSkill>;
type EntityArrayResponseType = HttpResponse<ITipoSkill[]>;

@Injectable({ providedIn: 'root' })
export class TipoSkillService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-skills';

  constructor(protected http: HttpClient) {}

  create(tipoSkill: ITipoSkill): Observable<EntityResponseType> {
    return this.http.post<ITipoSkill>(this.resourceUrl, tipoSkill, { observe: 'response' });
  }

  update(tipoSkill: ITipoSkill): Observable<EntityResponseType> {
    return this.http.put<ITipoSkill>(this.resourceUrl, tipoSkill, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
