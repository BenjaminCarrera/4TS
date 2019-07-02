import { ICandidato } from 'app/shared/model/candidato.model';

export interface IEstatusAcademico {
  id?: number;
  estatus?: string;
  candidatoes?: ICandidato[];
}

export class EstatusAcademico implements IEstatusAcademico {
  constructor(public id?: number, public estatus?: string, public candidatoes?: ICandidato[]) {}
}
