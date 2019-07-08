import { Moment } from 'moment';

export interface IBitacora {
  id?: number;
  fecha?: Moment;
  comentario?: string;
  usuarioFirstName?: string;
  usuarioId?: number;
  requerimientoProyecto?: string;
  requerimientoId?: number;
  candidatoNombre?: string;
  candidatoId?: number;
  tareaTitulo?: string;
  tareaId?: number;
}

export class Bitacora implements IBitacora {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public comentario?: string,
    public usuarioFirstName?: string,
    public usuarioId?: number,
    public requerimientoProyecto?: string,
    public requerimientoId?: number,
    public candidatoNombre?: string,
    public candidatoId?: number,
    public tareaTitulo?: string,
    public tareaId?: number
  ) {}
}
