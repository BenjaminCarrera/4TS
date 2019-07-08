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
  cuentaClave?: string;
  cuentaId?: number;
  subCuentaClave?: string;
  subCuentaId?: number;
  usuarioCreadorFirstName?: string;
  usuarioCreadorId?: number;
  usuarioAsignadoFirstName?: string;
  usuarioAsignadoId?: number;
  estatusRequerimientoEstatus?: string;
  estatusRequerimientoId?: number;
  prioridadPrioridad?: string;
  prioridadId?: number;
  tipoSolicitudSolicitud?: string;
  tipoSolicitudId?: number;
  tipoIngresoTipo?: string;
  tipoIngresoId?: number;
  baseTarifaBase?: string;
  baseTarifaId?: number;
  esquemaContratacionEsquema?: string;
  esquemaContratacionId?: number;
  perfilPerfil?: string;
  perfilId?: number;
  nivelPerfilNivel?: string;
  nivelPerfilId?: number;
  estatusReqCanMotivo?: string;
  estatusReqCanId?: number;
  tipoPeriodoPeriodo?: string;
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
    public cuentaClave?: string,
    public cuentaId?: number,
    public subCuentaClave?: string,
    public subCuentaId?: number,
    public usuarioCreadorFirstName?: string,
    public usuarioCreadorId?: number,
    public usuarioAsignadoFirstName?: string,
    public usuarioAsignadoId?: number,
    public estatusRequerimientoEstatus?: string,
    public estatusRequerimientoId?: number,
    public prioridadPrioridad?: string,
    public prioridadId?: number,
    public tipoSolicitudSolicitud?: string,
    public tipoSolicitudId?: number,
    public tipoIngresoTipo?: string,
    public tipoIngresoId?: number,
    public baseTarifaBase?: string,
    public baseTarifaId?: number,
    public esquemaContratacionEsquema?: string,
    public esquemaContratacionId?: number,
    public perfilPerfil?: string,
    public perfilId?: number,
    public nivelPerfilNivel?: string,
    public nivelPerfilId?: number,
    public estatusReqCanMotivo?: string,
    public estatusReqCanId?: number,
    public tipoPeriodoPeriodo?: string,
    public tipoPeriodoId?: number
  ) {}
}
