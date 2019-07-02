import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface IPrioridadReq {
  id?: number;
  prioridad?: string;
  requerimientos?: IRequerimiento[];
}

export class PrioridadReq implements IPrioridadReq {
  constructor(public id?: number, public prioridad?: string, public requerimientos?: IRequerimiento[]) {}
}
