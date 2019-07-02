import { Moment } from 'moment';
import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';
import { ITarea } from 'app/shared/model/tarea.model';
import { IBitacora } from 'app/shared/model/bitacora.model';

export interface IRequerimiento {
  id?: number;
  fechaAlda?: Moment;
  fechaResolucion?: Moment;
  remplazoDe?: string;
  vacantesSolicitadas?: number;
  proyecto?: string;
  nombreContacto?: string;
  tarifaSueldoNet?: number;
  prestaciones?: string;
  duracionAsignacion?: number;
  lugarTrabajo?: string;
  coorLat?: number;
  coorLong?: number;
  horario?: string;
  informacionExamen?: string;
  informacionAdicional?: string;
  skillRequerimientos?: ISkillRequerimiento[];
  tareas?: ITarea[];
  bitacoras?: IBitacora[];
  cuentaId?: number;
  subCuentaId?: number;
  usuarioCreadorId?: number;
  usuarioAsignadoId?: number;
  estatusRequerimientoId?: number;
  prioridadId?: number;
  tipoSolicitudId?: number;
  tipoIngresoId?: number;
  baseTarifaId?: number;
  esquemaContratacionId?: number;
  perfilId?: number;
  nivelPerfilId?: number;
  estatusReqCanId?: number;
  tipoPeriodoId?: number;
}

export class Requerimiento implements IRequerimiento {
  constructor(
    public id?: number,
    public fechaAlda?: Moment,
    public fechaResolucion?: Moment,
    public remplazoDe?: string,
    public vacantesSolicitadas?: number,
    public proyecto?: string,
    public nombreContacto?: string,
    public tarifaSueldoNet?: number,
    public prestaciones?: string,
    public duracionAsignacion?: number,
    public lugarTrabajo?: string,
    public coorLat?: number,
    public coorLong?: number,
    public horario?: string,
    public informacionExamen?: string,
    public informacionAdicional?: string,
    public skillRequerimientos?: ISkillRequerimiento[],
    public tareas?: ITarea[],
    public bitacoras?: IBitacora[],
    public cuentaId?: number,
    public subCuentaId?: number,
    public usuarioCreadorId?: number,
    public usuarioAsignadoId?: number,
    public estatusRequerimientoId?: number,
    public prioridadId?: number,
    public tipoSolicitudId?: number,
    public tipoIngresoId?: number,
    public baseTarifaId?: number,
    public esquemaContratacionId?: number,
    public perfilId?: number,
    public nivelPerfilId?: number,
    public estatusReqCanId?: number,
    public tipoPeriodoId?: number
  ) {}
}
