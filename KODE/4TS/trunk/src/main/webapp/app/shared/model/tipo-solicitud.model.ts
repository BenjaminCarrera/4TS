import { IRequerimiento } from 'app/shared/model/requerimiento.model';

export interface ITipoSolicitud {
  id?: number;
  solicitud?: string;
  requerimientos?: IRequerimiento[];
}

export class TipoSolicitud implements ITipoSolicitud {
  constructor(public id?: number, public solicitud?: string, public requerimientos?: IRequerimiento[]) {}
}
