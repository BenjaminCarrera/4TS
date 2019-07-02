import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface IEstReqCerrado {
  id?: number;
  motivo?: string;
  requerimientos?: IRequerimiento[];
  estatusRequerimientoId?: number;
}

export class EstReqCerrado implements IEstReqCerrado {
  constructor(
    public id?: number,
    public motivo?: string,
    public requerimientos?: IRequerimiento[],
    public estatusRequerimientoId?: number
  ) {}
}
