import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipoTarea } from 'app/shared/model/tipo-tarea.model';

type EntityResponseType = HttpResponse<ITipoTarea>;
type EntityArrayResponseType = HttpResponse<ITipoTarea[]>;

@Injectable({ providedIn: 'root' })
export class TipoTareaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-tareas';

  constructor(protected http: HttpClient) {}

  create(tipoTarea: ITipoTarea): Observable<EntityResponseType> {
    return this.http.post<ITipoTarea>(this.resourceUrl, tipoTarea, { observe: 'response' });
  }

  update(tipoTarea: ITipoTarea): Observable<EntityResponseType> {
    return this.http.put<ITipoTarea>(this.resourceUrl, tipoTarea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoTarea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoTarea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
