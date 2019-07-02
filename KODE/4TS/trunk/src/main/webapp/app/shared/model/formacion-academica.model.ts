import { ICandidato } from 'app/shared/model/candidato.model';

export interface IFormacionAcademica {
  id?: number;
  formacionAcademica?: string;
  candidatoes?: ICandidato[];
}

export class FormacionAcademica implements IFormacionAcademica {
  constructor(public id?: number, public formacionAcademica?: string, public candidatoes?: ICandidato[]) {}
}
