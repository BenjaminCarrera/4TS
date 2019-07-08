import { IBitacora } from 'app/shared/model/bitacora.model';

export interface ITarea {
  id?: number;
  descripcion?: string;
  titulo?: string;
  bitacoras?: IBitacora[];
  usuarioCreadorFirstName?: string;
  usuarioCreadorId?: number;
  usuarioEjecutorFirstName?: string;
  usuarioEjecutorId?: number;
  requerimientoProyecto?: string;
  requerimientoId?: number;
  candidatoNombre?: string;
  candidatoId?: number;
  estatusTareaEstatus?: string;
  estatusTareaId?: number;
  tipoTareaTipo?: string;
  tipoTareaId?: number;
}

export class Tarea implements ITarea {
  constructor(
    public id?: number,
    public descripcion?: string,
    public titulo?: string,
    public bitacoras?: IBitacora[],
    public usuarioCreadorFirstName?: string,
    public usuarioCreadorId?: number,
    public usuarioEjecutorFirstName?: string,
    public usuarioEjecutorId?: number,
    public requerimientoProyecto?: string,
    public requerimientoId?: number,
    public candidatoNombre?: string,
    public candidatoId?: number,
    public estatusTareaEstatus?: string,
    public estatusTareaId?: number,
    public tipoTareaTipo?: string,
    public tipoTareaId?: number
  ) {}
}
