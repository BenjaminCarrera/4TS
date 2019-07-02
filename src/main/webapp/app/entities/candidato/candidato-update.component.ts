import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICandidato, Candidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from './candidato.service';
import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from 'app/entities/tipo-periodo';
import { IUser, UserService } from 'app/core';
import { IDocumento } from 'app/shared/model/documento.model';
import { DocumentoService } from 'app/entities/documento';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from 'app/entities/cuenta';
import { IFuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';
import { FuenteReclutamientoService } from 'app/entities/fuente-reclutamiento';
import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from 'app/entities/estatus-candidato';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from 'app/entities/nivel-perfil';
import { IInstitucionAcademica } from 'app/shared/model/institucion-academica.model';
import { InstitucionAcademicaService } from 'app/entities/institucion-academica';
import { IEstatusAcademico } from 'app/shared/model/estatus-academico.model';
import { EstatusAcademicoService } from 'app/entities/estatus-academico';
import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';
import { EsquemaContratacionKodeService } from 'app/entities/esquema-contratacion-kode';
import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';
import { EstatusLaboralService } from 'app/entities/estatus-laboral';
import { IColonia } from 'app/shared/model/colonia.model';
import { ColoniaService } from 'app/entities/colonia';
import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from 'app/entities/esq-cont-rec';
import { IFormacionAcademica } from 'app/shared/model/formacion-academica.model';
import { FormacionAcademicaService } from 'app/entities/formacion-academica';
import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';
import { EstCanInactivoService } from 'app/entities/est-can-inactivo';

@Component({
  selector: 'jhi-candidato-update',
  templateUrl: './candidato-update.component.html'
})
export class CandidatoUpdateComponent implements OnInit {
  isSaving: boolean;

  tipoperiodos: ITipoPeriodo[];

  users: IUser[];

  documentos: IDocumento[];

  cuentas: ICuenta[];

  fuentereclutamientos: IFuenteReclutamiento[];

  estatuscandidatoes: IEstatusCandidato[];

  perfils: IPerfil[];

  nivelperfils: INivelPerfil[];

  institucionacademicas: IInstitucionAcademica[];

  estatusacademicos: IEstatusAcademico[];

  esquemacontratacionkodes: IEsquemaContratacionKode[];

  estatuslaborals: IEstatusLaboral[];

  colonias: IColonia[];

  esqcontrecs: IEsqContRec[];

  formacionacademicas: IFormacionAcademica[];

  estcaninactivos: IEstCanInactivo[];
  fechaNacimientoDp: any;
  disponibilidadEntrevistaFechaDp: any;
  disponibilidadAsignacionFechaDp: any;

  editForm = this.fb.group({
    id: [],
    anosExperiencia: [],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    apellidoPaterno: [null, [Validators.required, Validators.maxLength(100)]],
    apellidoMaterno: [null, [Validators.maxLength(100)]],
    fechaNacimiento: [],
    edad: [],
    emailPrincipal: [null, [Validators.required, Validators.maxLength(100)]],
    emailAdicional: [null, [Validators.maxLength(100)]],
    emailAsignacion: [null, [Validators.maxLength(100)]],
    emailKode: [null, [Validators.maxLength(100)]],
    web: [null, [Validators.maxLength(100)]],
    telefonoCasa: [null, [Validators.maxLength(20)]],
    telefonoTrabajo: [null, [Validators.maxLength(20)]],
    telefonoCelular: [null, [Validators.maxLength(20)]],
    telefonoAdicional: [null, [Validators.maxLength(20)]],
    coorLat: [],
    coorLong: [],
    dirCodigoPostal: [null, [Validators.maxLength(5)]],
    dirCalle: [null, [Validators.maxLength(100)]],
    noExt: [null, [Validators.maxLength(100)]],
    noInt: [null, [Validators.maxLength(100)]],
    salarioNeto: [],
    costoTotal: [],
    contatoDuracionMinima: [],
    disponibilidadEntrevistaFecha: [],
    disponibilidadEntrevistaPeriodo: [],
    disponibilidadAsignacionFecha: [],
    disponibilidadAsignacionPeriodo: [null, [Validators.maxLength(100)]],
    antecedenteUltimoEmpleador: [null, [Validators.maxLength(100)]],
    antecedenteSalarioNeto: [],
    notas: [null, [Validators.maxLength(1000)]],
    porcentajeIngles: [],
    curp: [null, [Validators.maxLength(50)]],
    rfc: [null, [Validators.maxLength(50)]],
    nss: [null, [Validators.maxLength(50)]],
    sexo: [],
    estadoCivil: [],
    fechaAlta: [],
    fechaUltimoSeguimiento: [],
    foto: [null, [Validators.maxLength(1000)]],
    disponibilidadEntrevistaPeriodoTiempoId: [],
    disponibilidadAsignacionPeriodoTiempoId: [],
    usuarioCreadorId: [],
    usuarioAsignadoId: [],
    documentoId: [],
    cuentaInteres: [],
    cuentaRechazadas: [],
    fuenteReclutamientoId: [],
    estatusCandidatoId: [],
    perfilId: [],
    nivelPerfilId: [],
    institucionAcademicaId: [],
    estatusAcademicoId: [],
    esquemaContratacionKodeId: [],
    estatusLaboralId: [],
    coloniaId: [],
    antecedentesEsquemaContratacionId: [],
    estudiosId: [],
    estCanInactivoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected candidatoService: CandidatoService,
    protected tipoPeriodoService: TipoPeriodoService,
    protected userService: UserService,
    protected documentoService: DocumentoService,
    protected cuentaService: CuentaService,
    protected fuenteReclutamientoService: FuenteReclutamientoService,
    protected estatusCandidatoService: EstatusCandidatoService,
    protected perfilService: PerfilService,
    protected nivelPerfilService: NivelPerfilService,
    protected institucionAcademicaService: InstitucionAcademicaService,
    protected estatusAcademicoService: EstatusAcademicoService,
    protected esquemaContratacionKodeService: EsquemaContratacionKodeService,
    protected estatusLaboralService: EstatusLaboralService,
    protected coloniaService: ColoniaService,
    protected esqContRecService: EsqContRecService,
    protected formacionAcademicaService: FormacionAcademicaService,
    protected estCanInactivoService: EstCanInactivoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ candidato }) => {
      this.updateForm(candidato);
    });
    this.tipoPeriodoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoPeriodo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoPeriodo[]>) => response.body)
      )
      .subscribe((res: ITipoPeriodo[]) => (this.tipoperiodos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDocumento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDocumento[]>) => response.body)
      )
      .subscribe((res: IDocumento[]) => (this.documentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.cuentaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICuenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICuenta[]>) => response.body)
      )
      .subscribe((res: ICuenta[]) => (this.cuentas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.fuenteReclutamientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuenteReclutamiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuenteReclutamiento[]>) => response.body)
      )
      .subscribe((res: IFuenteReclutamiento[]) => (this.fuentereclutamientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusCandidato[]>) => response.body)
      )
      .subscribe((res: IEstatusCandidato[]) => (this.estatuscandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.nivelPerfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INivelPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<INivelPerfil[]>) => response.body)
      )
      .subscribe((res: INivelPerfil[]) => (this.nivelperfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.institucionAcademicaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInstitucionAcademica[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstitucionAcademica[]>) => response.body)
      )
      .subscribe(
        (res: IInstitucionAcademica[]) => (this.institucionacademicas = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.estatusAcademicoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusAcademico[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusAcademico[]>) => response.body)
      )
      .subscribe((res: IEstatusAcademico[]) => (this.estatusacademicos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.esquemaContratacionKodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsquemaContratacionKode[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsquemaContratacionKode[]>) => response.body)
      )
      .subscribe(
        (res: IEsquemaContratacionKode[]) => (this.esquemacontratacionkodes = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.estatusLaboralService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusLaboral[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusLaboral[]>) => response.body)
      )
      .subscribe((res: IEstatusLaboral[]) => (this.estatuslaborals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.coloniaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IColonia[]>) => mayBeOk.ok),
        map((response: HttpResponse<IColonia[]>) => response.body)
      )
      .subscribe((res: IColonia[]) => (this.colonias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.esqContRecService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsqContRec[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsqContRec[]>) => response.body)
      )
      .subscribe((res: IEsqContRec[]) => (this.esqcontrecs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.formacionAcademicaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFormacionAcademica[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFormacionAcademica[]>) => response.body)
      )
      .subscribe((res: IFormacionAcademica[]) => (this.formacionacademicas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estCanInactivoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstCanInactivo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstCanInactivo[]>) => response.body)
      )
      .subscribe((res: IEstCanInactivo[]) => (this.estcaninactivos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(candidato: ICandidato) {
    this.editForm.patchValue({
      id: candidato.id,
      anosExperiencia: candidato.anosExperiencia,
      nombre: candidato.nombre,
      apellidoPaterno: candidato.apellidoPaterno,
      apellidoMaterno: candidato.apellidoMaterno,
      fechaNacimiento: candidato.fechaNacimiento,
      edad: candidato.edad,
      emailPrincipal: candidato.emailPrincipal,
      emailAdicional: candidato.emailAdicional,
      emailAsignacion: candidato.emailAsignacion,
      emailKode: candidato.emailKode,
      web: candidato.web,
      telefonoCasa: candidato.telefonoCasa,
      telefonoTrabajo: candidato.telefonoTrabajo,
      telefonoCelular: candidato.telefonoCelular,
      telefonoAdicional: candidato.telefonoAdicional,
      coorLat: candidato.coorLat,
      coorLong: candidato.coorLong,
      dirCodigoPostal: candidato.dirCodigoPostal,
      dirCalle: candidato.dirCalle,
      noExt: candidato.noExt,
      noInt: candidato.noInt,
      salarioNeto: candidato.salarioNeto,
      costoTotal: candidato.costoTotal,
      contatoDuracionMinima: candidato.contatoDuracionMinima,
      disponibilidadEntrevistaFecha: candidato.disponibilidadEntrevistaFecha,
      disponibilidadEntrevistaPeriodo: candidato.disponibilidadEntrevistaPeriodo,
      disponibilidadAsignacionFecha: candidato.disponibilidadAsignacionFecha,
      disponibilidadAsignacionPeriodo: candidato.disponibilidadAsignacionPeriodo,
      antecedenteUltimoEmpleador: candidato.antecedenteUltimoEmpleador,
      antecedenteSalarioNeto: candidato.antecedenteSalarioNeto,
      notas: candidato.notas,
      porcentajeIngles: candidato.porcentajeIngles,
      curp: candidato.curp,
      rfc: candidato.rfc,
      nss: candidato.nss,
      sexo: candidato.sexo,
      estadoCivil: candidato.estadoCivil,
      fechaAlta: candidato.fechaAlta != null ? candidato.fechaAlta.format(DATE_TIME_FORMAT) : null,
      fechaUltimoSeguimiento: candidato.fechaUltimoSeguimiento != null ? candidato.fechaUltimoSeguimiento.format(DATE_TIME_FORMAT) : null,
      foto: candidato.foto,
      disponibilidadEntrevistaPeriodoTiempoId: candidato.disponibilidadEntrevistaPeriodoTiempoId,
      disponibilidadAsignacionPeriodoTiempoId: candidato.disponibilidadAsignacionPeriodoTiempoId,
      usuarioCreadorId: candidato.usuarioCreadorId,
      usuarioAsignadoId: candidato.usuarioAsignadoId,
      documentoId: candidato.documentoId,
      cuentaInteres: candidato.cuentaInteres,
      cuentaRechazadas: candidato.cuentaRechazadas,
      fuenteReclutamientoId: candidato.fuenteReclutamientoId,
      estatusCandidatoId: candidato.estatusCandidatoId,
      perfilId: candidato.perfilId,
      nivelPerfilId: candidato.nivelPerfilId,
      institucionAcademicaId: candidato.institucionAcademicaId,
      estatusAcademicoId: candidato.estatusAcademicoId,
      esquemaContratacionKodeId: candidato.esquemaContratacionKodeId,
      estatusLaboralId: candidato.estatusLaboralId,
      coloniaId: candidato.coloniaId,
      antecedentesEsquemaContratacionId: candidato.antecedentesEsquemaContratacionId,
      estudiosId: candidato.estudiosId,
      estCanInactivoId: candidato.estCanInactivoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const candidato = this.createFromForm();
    if (candidato.id !== undefined) {
      this.subscribeToSaveResponse(this.candidatoService.update(candidato));
    } else {
      this.subscribeToSaveResponse(this.candidatoService.create(candidato));
    }
  }

  private createFromForm(): ICandidato {
    return {
      ...new Candidato(),
      id: this.editForm.get(['id']).value,
      anosExperiencia: this.editForm.get(['anosExperiencia']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellidoPaterno: this.editForm.get(['apellidoPaterno']).value,
      apellidoMaterno: this.editForm.get(['apellidoMaterno']).value,
      fechaNacimiento: this.editForm.get(['fechaNacimiento']).value,
      edad: this.editForm.get(['edad']).value,
      emailPrincipal: this.editForm.get(['emailPrincipal']).value,
      emailAdicional: this.editForm.get(['emailAdicional']).value,
      emailAsignacion: this.editForm.get(['emailAsignacion']).value,
      emailKode: this.editForm.get(['emailKode']).value,
      web: this.editForm.get(['web']).value,
      telefonoCasa: this.editForm.get(['telefonoCasa']).value,
      telefonoTrabajo: this.editForm.get(['telefonoTrabajo']).value,
      telefonoCelular: this.editForm.get(['telefonoCelular']).value,
      telefonoAdicional: this.editForm.get(['telefonoAdicional']).value,
      coorLat: this.editForm.get(['coorLat']).value,
      coorLong: this.editForm.get(['coorLong']).value,
      dirCodigoPostal: this.editForm.get(['dirCodigoPostal']).value,
      dirCalle: this.editForm.get(['dirCalle']).value,
      noExt: this.editForm.get(['noExt']).value,
      noInt: this.editForm.get(['noInt']).value,
      salarioNeto: this.editForm.get(['salarioNeto']).value,
      costoTotal: this.editForm.get(['costoTotal']).value,
      contatoDuracionMinima: this.editForm.get(['contatoDuracionMinima']).value,
      disponibilidadEntrevistaFecha: this.editForm.get(['disponibilidadEntrevistaFecha']).value,
      disponibilidadEntrevistaPeriodo: this.editForm.get(['disponibilidadEntrevistaPeriodo']).value,
      disponibilidadAsignacionFecha: this.editForm.get(['disponibilidadAsignacionFecha']).value,
      disponibilidadAsignacionPeriodo: this.editForm.get(['disponibilidadAsignacionPeriodo']).value,
      antecedenteUltimoEmpleador: this.editForm.get(['antecedenteUltimoEmpleador']).value,
      antecedenteSalarioNeto: this.editForm.get(['antecedenteSalarioNeto']).value,
      notas: this.editForm.get(['notas']).value,
      porcentajeIngles: this.editForm.get(['porcentajeIngles']).value,
      curp: this.editForm.get(['curp']).value,
      rfc: this.editForm.get(['rfc']).value,
      nss: this.editForm.get(['nss']).value,
      sexo: this.editForm.get(['sexo']).value,
      estadoCivil: this.editForm.get(['estadoCivil']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? moment(this.editForm.get(['fechaAlta']).value, DATE_TIME_FORMAT) : undefined,
      fechaUltimoSeguimiento:
        this.editForm.get(['fechaUltimoSeguimiento']).value != null
          ? moment(this.editForm.get(['fechaUltimoSeguimiento']).value, DATE_TIME_FORMAT)
          : undefined,
      foto: this.editForm.get(['foto']).value,
      disponibilidadEntrevistaPeriodoTiempoId: this.editForm.get(['disponibilidadEntrevistaPeriodoTiempoId']).value,
      disponibilidadAsignacionPeriodoTiempoId: this.editForm.get(['disponibilidadAsignacionPeriodoTiempoId']).value,
      usuarioCreadorId: this.editForm.get(['usuarioCreadorId']).value,
      usuarioAsignadoId: this.editForm.get(['usuarioAsignadoId']).value,
      documentoId: this.editForm.get(['documentoId']).value,
      cuentaInteres: this.editForm.get(['cuentaInteres']).value,
      cuentaRechazadas: this.editForm.get(['cuentaRechazadas']).value,
      fuenteReclutamientoId: this.editForm.get(['fuenteReclutamientoId']).value,
      estatusCandidatoId: this.editForm.get(['estatusCandidatoId']).value,
      perfilId: this.editForm.get(['perfilId']).value,
      nivelPerfilId: this.editForm.get(['nivelPerfilId']).value,
      institucionAcademicaId: this.editForm.get(['institucionAcademicaId']).value,
      estatusAcademicoId: this.editForm.get(['estatusAcademicoId']).value,
      esquemaContratacionKodeId: this.editForm.get(['esquemaContratacionKodeId']).value,
      estatusLaboralId: this.editForm.get(['estatusLaboralId']).value,
      coloniaId: this.editForm.get(['coloniaId']).value,
      antecedentesEsquemaContratacionId: this.editForm.get(['antecedentesEsquemaContratacionId']).value,
      estudiosId: this.editForm.get(['estudiosId']).value,
      estCanInactivoId: this.editForm.get(['estCanInactivoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidato>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTipoPeriodoById(index: number, item: ITipoPeriodo) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackDocumentoById(index: number, item: IDocumento) {
    return item.id;
  }

  trackCuentaById(index: number, item: ICuenta) {
    return item.id;
  }

  trackFuenteReclutamientoById(index: number, item: IFuenteReclutamiento) {
    return item.id;
  }

  trackEstatusCandidatoById(index: number, item: IEstatusCandidato) {
    return item.id;
  }

  trackPerfilById(index: number, item: IPerfil) {
    return item.id;
  }

  trackNivelPerfilById(index: number, item: INivelPerfil) {
    return item.id;
  }

  trackInstitucionAcademicaById(index: number, item: IInstitucionAcademica) {
    return item.id;
  }

  trackEstatusAcademicoById(index: number, item: IEstatusAcademico) {
    return item.id;
  }

  trackEsquemaContratacionKodeById(index: number, item: IEsquemaContratacionKode) {
    return item.id;
  }

  trackEstatusLaboralById(index: number, item: IEstatusLaboral) {
    return item.id;
  }

  trackColoniaById(index: number, item: IColonia) {
    return item.id;
  }

  trackEsqContRecById(index: number, item: IEsqContRec) {
    return item.id;
  }

  trackFormacionAcademicaById(index: number, item: IFormacionAcademica) {
    return item.id;
  }

  trackEstCanInactivoById(index: number, item: IEstCanInactivo) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
