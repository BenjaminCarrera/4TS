import { ICandidato } from 'app/shared/model/candidato.model';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface IPerfil {
  id?: number;
  candidatoes?: ICandidato[];
  requerimientos?: IRequerimiento[];
}

export class Perfil implements IPerfil {
  constructor(public id?: number, public candidatoes?: ICandidato[], public requerimientos?: IRequerimiento[]) {}
}
