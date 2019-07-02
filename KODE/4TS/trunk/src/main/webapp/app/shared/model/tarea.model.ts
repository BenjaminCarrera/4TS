import { IBitacora } from 'app/shared/model/bitacora.model';

export interface ITarea {
  id?: number;
  descripcion?: string;
  titulo?: string;
  bitacoras?: IBitacora[];
  usuarioCreadorId?: number;
  usuarioEjecutorId?: number;
  requerimientoId?: number;
  candidatoId?: number;
  estatusTareaId?: number;
  tipoTareaId?: number;
}

export class Tarea implements ITarea {
  constructor(
    public id?: number,
    public descripcion?: string,
    public titulo?: string,
    public bitacoras?: IBitacora[],
    public usuarioCreadorId?: number,
    public usuarioEjecutorId?: number,
    public requerimientoId?: number,
    public candidatoId?: number,
    public estatusTareaId?: number,
    public tipoTareaId?: number
  ) {}
}
