import { ICandidato } from 'app/shared/model/candidato.model';

export interface IFormacionAcademica {
  id?: number;
  candidatoes?: ICandidato[];
}

export class FormacionAcademica implements IFormacionAcademica {
  constructor(public id?: number, public candidatoes?: ICandidato[]) {}
}
