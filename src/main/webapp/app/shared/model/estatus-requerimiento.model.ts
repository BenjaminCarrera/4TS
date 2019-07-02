import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';

export interface IEstatusRequerimiento {
  id?: number;
  estatus?: string;
  requerimientos?: IRequerimiento[];
  estReqCerrados?: IEstReqCerrado[];
}

export class EstatusRequerimiento implements IEstatusRequerimiento {
  constructor(
    public id?: number,
    public estatus?: string,
    public requerimientos?: IRequerimiento[],
    public estReqCerrados?: IEstReqCerrado[]
  ) {}
}
