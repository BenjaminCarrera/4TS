import { ICandidato } from 'app/shared/model/candidato.model';

export interface IEsquemaContratacionKode {
  id?: number;
  esquema?: string;
  candidatoes?: ICandidato[];
}

export class EsquemaContratacionKode implements IEsquemaContratacionKode {
  constructor(public id?: number, public esquema?: string, public candidatoes?: ICandidato[]) {}
}
