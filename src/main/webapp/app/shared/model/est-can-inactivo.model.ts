import { ICandidato } from 'app/shared/model/candidato.model';

export interface IEstCanInactivo {
  id?: number;
  motivo?: string;
  candidatoes?: ICandidato[];
  estatusCandidatoEstatus?: string;
  estatusCandidatoId?: number;
}

export class EstCanInactivo implements IEstCanInactivo {
  constructor(
    public id?: number,
    public motivo?: string,
    public candidatoes?: ICandidato[],
    public estatusCandidatoEstatus?: string,
    public estatusCandidatoId?: number
  ) {}
}
