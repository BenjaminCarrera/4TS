import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

type EntityResponseType = HttpResponse<ISkillRequerimiento>;
type EntityArrayResponseType = HttpResponse<any[]>;

@Injectable({ providedIn: 'root' })
export class SkillRequerimientoService {
  public resourceUrl = SERVER_API_URL + 'api/skill-requerimientos';

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(protected http: HttpClient) {}

  create(skillRequerimiento: ISkillRequerimiento): Observable<EntityResponseType> {
    return this.http.post<ISkillRequerimiento>(this.resourceUrl, skillRequerimiento, { observe: 'response' });
  }

  update(skillRequerimiento: ISkillRequerimiento): Observable<EntityResponseType> {
    return this.http.put<ISkillRequerimiento>(this.resourceUrl, skillRequerimiento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISkillRequerimiento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISkillRequerimiento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  patch(req: any): Observable<EntityArrayResponseType> {
    return this.http.patch<any>(this.resourceUrl, JSON.stringify(req), this.httpOptions);
  }
}
