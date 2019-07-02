import { ICandidato } from 'app/shared/model/candidato.model';
import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';

export interface IEstatusCandidato {
  id?: number;
  estatus?: string;
  candidatoes?: ICandidato[];
  estCanInactivos?: IEstCanInactivo[];
}

export class EstatusCandidato implements IEstatusCandidato {
  constructor(public id?: number, public estatus?: string, public candidatoes?: ICandidato[], public estCanInactivos?: IEstCanInactivo[]) {}
}
