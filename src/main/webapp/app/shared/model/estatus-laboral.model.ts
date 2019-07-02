import { ICandidato } from 'app/shared/model/candidato.model';

export interface IEstatusLaboral {
  id?: number;
  estatus?: string;
  candidatoes?: ICandidato[];
}

export class EstatusLaboral implements IEstatusLaboral {
  constructor(public id?: number, public estatus?: string, public candidatoes?: ICandidato[]) {}
}
