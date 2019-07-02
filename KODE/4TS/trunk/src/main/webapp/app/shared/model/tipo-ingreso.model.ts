import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface ITipoIngreso {
  id?: number;
  tipo?: string;
  requerimientos?: IRequerimiento[];
}

export class TipoIngreso implements ITipoIngreso {
  constructor(public id?: number, public tipo?: string, public requerimientos?: IRequerimiento[]) {}
}
