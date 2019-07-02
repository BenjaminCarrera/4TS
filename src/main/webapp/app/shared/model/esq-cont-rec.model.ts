import { ICandidato } from 'app/shared/model/candidato.model';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface IEsqContRec {
  id?: number;
  esquema?: string;
  candidatoes?: ICandidato[];
  requerimientos?: IRequerimiento[];
}

export class EsqContRec implements IEsqContRec {
  constructor(public id?: number, public esquema?: string, public candidatoes?: ICandidato[], public requerimientos?: IRequerimiento[]) {}
}
