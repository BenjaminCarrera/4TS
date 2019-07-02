import { Moment } from 'moment';

export interface IBitacora {
  id?: number;
  fecha?: Moment;
  comentario?: string;
  usuarioId?: number;
  requerimientoId?: number;
  candidatoId?: number;
  tareaId?: number;
}

export class Bitacora implements IBitacora {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public comentario?: string,
    public usuarioId?: number,
    public requerimientoId?: number,
    public candidatoId?: number,
    public tareaId?: number
  ) {}
}
