import { ICandidato } from 'app/shared/model/candidato.model';

export interface IInstitucionAcademica {
  id?: number;
  institucion?: string;
  candidatoes?: ICandidato[];
}

export class InstitucionAcademica implements IInstitucionAcademica {
  constructor(public id?: number, public institucion?: string, public candidatoes?: ICandidato[]) {}
}
