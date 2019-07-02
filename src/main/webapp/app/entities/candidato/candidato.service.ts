import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICandidato } from 'app/shared/model/candidato.model';

type EntityResponseType = HttpResponse<ICandidato>;
type EntityArrayResponseType = HttpResponse<ICandidato[]>;

@Injectable({ providedIn: 'root' })
export class CandidatoService {
  public resourceUrl = SERVER_API_URL + 'api/candidatoes';

  constructor(protected http: HttpClient) {}

  create(candidato: ICandidato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidato);
    return this.http
      .post<ICandidato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(candidato: ICandidato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(candidato);
    return this.http
      .put<ICandidato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICandidato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICandidato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(candidato: ICandidato): ICandidato {
    const copy: ICandidato = Object.assign({}, candidato, {
      fechaNacimiento:
        candidato.fechaNacimiento != null && candidato.fechaNacimiento.isValid() ? candidato.fechaNacimiento.format(DATE_FORMAT) : null,
      disponibilidadEntrevistaFecha:
        candidato.disponibilidadEntrevistaFecha != null && candidato.disponibilidadEntrevistaFecha.isValid()
          ? candidato.disponibilidadEntrevistaFecha.format(DATE_FORMAT)
          : null,
      disponibilidadAsignacionFecha:
        candidato.disponibilidadAsignacionFecha != null && candidato.disponibilidadAsignacionFecha.isValid()
          ? candidato.disponibilidadAsignacionFecha.format(DATE_FORMAT)
          : null,
      fechaAlta: candidato.fechaAlta != null && candidato.fechaAlta.isValid() ? candidato.fechaAlta.toJSON() : null,
      fechaUltimoSeguimiento:
        candidato.fechaUltimoSeguimiento != null && candidato.fechaUltimoSeguimiento.isValid()
          ? candidato.fechaUltimoSeguimiento.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaNacimiento = res.body.fechaNacimiento != null ? moment(res.body.fechaNacimiento) : null;
      res.body.disponibilidadEntrevistaFecha =
        res.body.disponibilidadEntrevistaFecha != null ? moment(res.body.disponibilidadEntrevistaFecha) : null;
      res.body.disponibilidadAsignacionFecha =
        res.body.disponibilidadAsignacionFecha != null ? moment(res.body.disponibilidadAsignacionFecha) : null;
      res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
      res.body.fechaUltimoSeguimiento = res.body.fechaUltimoSeguimiento != null ? moment(res.body.fechaUltimoSeguimiento) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((candidato: ICandidato) => {
        candidato.fechaNacimiento = candidato.fechaNacimiento != null ? moment(candidato.fechaNacimiento) : null;
        candidato.disponibilidadEntrevistaFecha =
          candidato.disponibilidadEntrevistaFecha != null ? moment(candidato.disponibilidadEntrevistaFecha) : null;
        candidato.disponibilidadAsignacionFecha =
          candidato.disponibilidadAsignacionFecha != null ? moment(candidato.disponibilidadAsignacionFecha) : null;
        candidato.fechaAlta = candidato.fechaAlta != null ? moment(candidato.fechaAlta) : null;
        candidato.fechaUltimoSeguimiento = candidato.fechaUltimoSeguimiento != null ? moment(candidato.fechaUltimoSeguimiento) : null;
      });
    }
    return res;
  }
}
