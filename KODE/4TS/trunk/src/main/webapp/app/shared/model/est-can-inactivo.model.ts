import { ICandidato } from 'app/shared/model/candidato.model';

export interface IEstCanInactivo {
  id?: number;
  motivo?: string;
  candidatoes?: ICandidato[];
  estatusCandidatoId?: number;
}

export class EstCanInactivo implements IEstCanInactivo {
  constructor(public id?: number, public motivo?: string, public candidatoes?: ICandidato[], public estatusCandidatoId?: number) {}
}
