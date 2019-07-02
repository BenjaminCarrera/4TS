import { ICandidato } from 'app/shared/model/candidato.model';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface INivelPerfil {
  id?: number;
  nivel?: string;
  candidatoes?: ICandidato[];
  requerimientos?: IRequerimiento[];
}

export class NivelPerfil implements INivelPerfil {
  constructor(public id?: number, public nivel?: string, public candidatoes?: ICandidato[], public requerimientos?: IRequerimiento[]) {}
}
