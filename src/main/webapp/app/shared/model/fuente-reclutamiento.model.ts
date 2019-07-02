import { ICandidato } from 'app/shared/model/candidato.model';

export interface IFuenteReclutamiento {
  id?: number;
  fuente?: string;
  candidatoes?: ICandidato[];
}

export class FuenteReclutamiento implements IFuenteReclutamiento {
  constructor(public id?: number, public fuente?: string, public candidatoes?: ICandidato[]) {}
}
