import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface IBaseTarifa {
  id?: number;
  base?: string;
  requerimientos?: IRequerimiento[];
}

export class BaseTarifa implements IBaseTarifa {
  constructor(public id?: number, public base?: string, public requerimientos?: IRequerimiento[]) {}
}
