import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFormacionAcademica } from 'app/shared/model/formacion-academica.model';

type EntityResponseType = HttpResponse<IFormacionAcademica>;
type EntityArrayResponseType = HttpResponse<IFormacionAcademica[]>;

@Injectable({ providedIn: 'root' })
export class FormacionAcademicaService {
  public resourceUrl = SERVER_API_URL + 'api/formacion-academicas';

  constructor(protected http: HttpClient) {}

  create(formacionAcademica: IFormacionAcademica): Observable<EntityResponseType> {
    return this.http.post<IFormacionAcademica>(this.resourceUrl, formacionAcademica, { observe: 'response' });
  }

  update(formacionAcademica: IFormacionAcademica): Observable<EntityResponseType> {
    return this.http.put<IFormacionAcademica>(this.resourceUrl, formacionAcademica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormacionAcademica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormacionAcademica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
