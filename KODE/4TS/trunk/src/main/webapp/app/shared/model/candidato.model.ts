import { Moment } from 'moment';
import { IReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';
import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';
import { ITarea } from 'app/shared/model/tarea.model';
import { IBitacora } from 'app/shared/model/bitacora.model';
import { ICuenta } from 'app/shared/model/cuenta.model';

export const enum Sexo {
  MASCULINO = 'MASCULINO',
  FEMENINO = 'FEMENINO'
}

export const enum EstadoCivil {
  SOLTERO = 'SOLTERO',
  CASADO = 'CASADO'
}

export interface ICandidato {
  id?: number;
  anosExperiencia?: number;
  nombre?: string;
  apellidoPaterno?: string;
  apellidoMaterno?: string;
  fechaNacimiento?: Moment;
  edad?: number;
  emailPrincipal?: string;
  emailAdicional?: string;
  emailAsignacion?: string;
  emailKode?: string;
  web?: string;
  telefonoCasa?: string;
  telefonoTrabajo?: string;
  telefonoCelular?: string;
  telefonoAdicional?: string;
  coorLat?: number;
  coorLong?: number;
  dirCodigoPostal?: string;
  dirCalle?: string;
  noExt?: string;
  noInt?: string;
  salarioNeto?: number;
  costoTotal?: number;
  contatoDuracionMinima?: number;
  disponibilidadEntrevistaFecha?: Moment;
  disponibilidadEntrevistaPeriodo?: number;
  disponibilidadAsignacionFecha?: Moment;
  disponibilidadAsignacionPeriodo?: string;
  antecedenteUltimoEmpleador?: string;
  antecedenteSalarioNeto?: number;
  notas?: string;
  porcentajeIngles?: number;
  curp?: string;
  rfc?: string;
  nss?: string;
  sexo?: Sexo;
  estadoCivil?: EstadoCivil;
  fechaAlta?: Moment;
  fechaUltimoSeguimiento?: Moment;
  foto?: string;
  referenciasLaborales?: IReferenciasLaborales[];
  skillCandidatoes?: ISkillCandidato[];
  tareas?: ITarea[];
  bitacoras?: IBitacora[];
  disponibilidadEntrevistaPeriodoTiempoId?: number;
  disponibilidadAsignacionPeriodoTiempoId?: number;
  usuarioCreadorId?: number;
  usuarioAsignadoId?: number;
  documentoId?: number;
  cuentaInteres?: ICuenta[];
  cuentaRechazadas?: ICuenta[];
  fuenteReclutamientoId?: number;
  estatusCandidatoId?: number;
  perfilId?: number;
  nivelPerfilId?: number;
  institucionAcademicaId?: number;
  estatusAcademicoId?: number;
  esquemaContratacionKodeId?: number;
  estatusLaboralId?: number;
  coloniaId?: number;
  antecedentesEsquemaContratacionId?: number;
  estudiosId?: number;
  estCanInactivoId?: number;
}

export class Candidato implements ICandidato {
  constructor(
    public id?: number,
    public anosExperiencia?: number,
    public nombre?: string,
    public apellidoPaterno?: string,
    public apellidoMaterno?: string,
    public fechaNacimiento?: Moment,
    public edad?: number,
    public emailPrincipal?: string,
    public emailAdicional?: string,
    public emailAsignacion?: string,
    public emailKode?: string,
    public web?: string,
    public telefonoCasa?: string,
    public telefonoTrabajo?: string,
    public telefonoCelular?: string,
    public telefonoAdicional?: string,
    public coorLat?: number,
    public coorLong?: number,
    public dirCodigoPostal?: string,
    public dirCalle?: string,
    public noExt?: string,
    public noInt?: string,
    public salarioNeto?: number,
    public costoTotal?: number,
    public contatoDuracionMinima?: number,
    public disponibilidadEntrevistaFecha?: Moment,
    public disponibilidadEntrevistaPeriodo?: number,
    public disponibilidadAsignacionFecha?: Moment,
    public disponibilidadAsignacionPeriodo?: string,
    public antecedenteUltimoEmpleador?: string,
    public antecedenteSalarioNeto?: number,
    public notas?: string,
    public porcentajeIngles?: number,
    public curp?: string,
    public rfc?: string,
    public nss?: string,
    public sexo?: Sexo,
    public estadoCivil?: EstadoCivil,
    public fechaAlta?: Moment,
    public fechaUltimoSeguimiento?: Moment,
    public foto?: string,
    public referenciasLaborales?: IReferenciasLaborales[],
    public skillCandidatoes?: ISkillCandidato[],
    public tareas?: ITarea[],
    public bitacoras?: IBitacora[],
    public disponibilidadEntrevistaPeriodoTiempoId?: number,
    public disponibilidadAsignacionPeriodoTiempoId?: number,
    public usuarioCreadorId?: number,
    public usuarioAsignadoId?: number,
    public documentoId?: number,
    public cuentaInteres?: ICuenta[],
    public cuentaRechazadas?: ICuenta[],
    public fuenteReclutamientoId?: number,
    public estatusCandidatoId?: number,
    public perfilId?: number,
    public nivelPerfilId?: number,
    public institucionAcademicaId?: number,
    public estatusAcademicoId?: number,
    public esquemaContratacionKodeId?: number,
    public estatusLaboralId?: number,
    public coloniaId?: number,
    public antecedentesEsquemaContratacionId?: number,
    public estudiosId?: number,
    public estCanInactivoId?: number
  ) {}
}
