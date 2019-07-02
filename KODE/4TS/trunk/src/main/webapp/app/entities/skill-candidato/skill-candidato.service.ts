import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';

type EntityResponseType = HttpResponse<ISkillCandidato>;
type EntityArrayResponseType = HttpResponse<ISkillCandidato[]>;

@Injectable({ providedIn: 'root' })
export class SkillCandidatoService {
  public resourceUrl = SERVER_API_URL + 'api/skill-candidatoes';

  constructor(protected http: HttpClient) {}

  create(skillCandidato: ISkillCandidato): Observable<EntityResponseType> {
    return this.http.post<ISkillCandidato>(this.resourceUrl, skillCandidato, { observe: 'response' });
  }

  update(skillCandidato: ISkillCandidato): Observable<EntityResponseType> {
    return this.http.put<ISkillCandidato>(this.resourceUrl, skillCandidato, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkillCandidato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkillCandidato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
