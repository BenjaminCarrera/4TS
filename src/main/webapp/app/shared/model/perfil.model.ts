import { ICandidato } from 'app/shared/model/candidato.model';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface IPerfil {
  id?: number;
  perfil?: string;
  candidatoes?: ICandidato[];
  requerimientos?: IRequerimiento[];
}

export class Perfil implements IPerfil {
  constructor(public id?: number, public perfil?: string, public candidatoes?: ICandidato[], public requerimientos?: IRequerimiento[]) {}
}
