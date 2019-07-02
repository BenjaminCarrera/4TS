import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDominioSkill } from 'app/shared/model/dominio-skill.model';

type EntityResponseType = HttpResponse<IDominioSkill>;
type EntityArrayResponseType = HttpResponse<IDominioSkill[]>;

@Injectable({ providedIn: 'root' })
export class DominioSkillService {
  public resourceUrl = SERVER_API_URL + 'api/dominio-skills';

  constructor(protected http: HttpClient) {}

  create(dominioSkill: IDominioSkill): Observable<EntityResponseType> {
    return this.http.post<IDominioSkill>(this.resourceUrl, dominioSkill, { observe: 'response' });
  }

  update(dominioSkill: IDominioSkill): Observable<EntityResponseType> {
    return this.http.put<IDominioSkill>(this.resourceUrl, dominioSkill, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDominioSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDominioSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
