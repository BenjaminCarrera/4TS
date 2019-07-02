import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';

type EntityResponseType = HttpResponse<IEstatusTarea>;
type EntityArrayResponseType = HttpResponse<IEstatusTarea[]>;

@Injectable({ providedIn: 'root' })
export class EstatusTareaService {
  public resourceUrl = SERVER_API_URL + 'api/estatus-tareas';

  constructor(protected http: HttpClient) {}

  create(estatusTarea: IEstatusTarea): Observable<EntityResponseType> {
    return this.http.post<IEstatusTarea>(this.resourceUrl, estatusTarea, { observe: 'response' });
  }

  update(estatusTarea: IEstatusTarea): Observable<EntityResponseType> {
    return this.http.put<IEstatusTarea>(this.resourceUrl, estatusTarea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstatusTarea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstatusTarea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
