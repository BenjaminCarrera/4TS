import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface ITipoPeriodo {
  id?: number;
  periodo?: string;
  requerimientos?: IRequerimiento[];
}

export class TipoPeriodo implements ITipoPeriodo {
  constructor(public id?: number, public periodo?: string, public requerimientos?: IRequerimiento[]) {}
}
