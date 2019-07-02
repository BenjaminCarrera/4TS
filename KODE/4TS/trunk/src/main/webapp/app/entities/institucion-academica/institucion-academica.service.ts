import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInstitucionAcademica } from 'app/shared/model/institucion-academica.model';

type EntityResponseType = HttpResponse<IInstitucionAcademica>;
type EntityArrayResponseType = HttpResponse<IInstitucionAcademica[]>;

@Injectable({ providedIn: 'root' })
export class InstitucionAcademicaService {
  public resourceUrl = SERVER_API_URL + 'api/institucion-academicas';

  constructor(protected http: HttpClient) {}

  create(institucionAcademica: IInstitucionAcademica): Observable<EntityResponseType> {
    return this.http.post<IInstitucionAcademica>(this.resourceUrl, institucionAcademica, { observe: 'response' });
  }

  update(institucionAcademica: IInstitucionAcademica): Observable<EntityResponseType> {
    return this.http.put<IInstitucionAcademica>(this.resourceUrl, institucionAcademica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInstitucionAcademica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInstitucionAcademica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
