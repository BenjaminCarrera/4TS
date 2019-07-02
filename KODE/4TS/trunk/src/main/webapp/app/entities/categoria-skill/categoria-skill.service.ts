import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoriaSkill } from 'app/shared/model/categoria-skill.model';

type EntityResponseType = HttpResponse<ICategoriaSkill>;
type EntityArrayResponseType = HttpResponse<ICategoriaSkill[]>;

@Injectable({ providedIn: 'root' })
export class CategoriaSkillService {
  public resourceUrl = SERVER_API_URL + 'api/categoria-skills';

  constructor(protected http: HttpClient) {}

  create(categoriaSkill: ICategoriaSkill): Observable<EntityResponseType> {
    return this.http.post<ICategoriaSkill>(this.resourceUrl, categoriaSkill, { observe: 'response' });
  }

  update(categoriaSkill: ICategoriaSkill): Observable<EntityResponseType> {
    return this.http.put<ICategoriaSkill>(this.resourceUrl, categoriaSkill, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategoriaSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategoriaSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
