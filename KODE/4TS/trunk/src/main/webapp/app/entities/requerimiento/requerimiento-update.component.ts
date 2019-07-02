import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRequerimiento, Requerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from 'app/entities/cuenta';
import { IUser, UserService } from 'app/core';
import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from 'app/entities/estatus-requerimiento';
import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';
import { PrioridadReqService } from 'app/entities/prioridad-req';
import { ITipoSolicitud } from 'app/shared/model/tipo-solicitud.model';
import { TipoSolicitudService } from 'app/entities/tipo-solicitud';
import { ITipoIngreso } from 'app/shared/model/tipo-ingreso.model';
import { TipoIngresoService } from 'app/entities/tipo-ingreso';
import { IBaseTarifa } from 'app/shared/model/base-tarifa.model';
import { BaseTarifaService } from 'app/entities/base-tarifa';
import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from 'app/entities/esq-cont-rec';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from 'app/entities/nivel-perfil';
import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';
import { EstReqCerradoService } from 'app/entities/est-req-cerrado';
import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from 'app/entities/tipo-periodo';

@Component({
  selector: 'jhi-requerimiento-update',
  templateUrl: './requerimiento-update.component.html'
})
export class RequerimientoUpdateComponent implements OnInit {
  requerimiento: IRequerimiento;
  isSaving: boolean;

  cuentas: ICuenta[];

  users: IUser[];

  estatusrequerimientos: IEstatusRequerimiento[];

  prioridadreqs: IPrioridadReq[];

  tiposolicituds: ITipoSolicitud[];

  tipoingresos: ITipoIngreso[];

  basetarifas: IBaseTarifa[];

  esqcontrecs: IEsqContRec[];

  perfils: IPerfil[];

  nivelperfils: INivelPerfil[];

  estreqcerrados: IEstReqCerrado[];

  tipoperiodos: ITipoPeriodo[];

  editForm = this.fb.group({
    id: [],
    fechaAlda: [null, [Validators.required]],
    fechaResolucion: [],
    remplazoDe: [null, [Validators.maxLength(500)]],
    vacantesSolicitadas: [],
    proyecto: [null, [Validators.maxLength(200)]],
    nombreContacto: [null, [Validators.maxLength(100)]],
    tarifaSueldoNet: [],
    prestaciones: [null, [Validators.maxLength(500)]],
    duracionAsignacion: [],
    lugarTrabajo: [null, [Validators.maxLength(500)]],
    coorLat: [],
    coorLong: [],
    horario: [null, [Validators.maxLength(300)]],
    informacionExamen: [null, [Validators.maxLength(500)]],
    informacionAdicional: [null, [Validators.maxLength(1000)]],
    cuentaId: [],
    subCuentaId: [],
    usuarioCreadorId: [],
    usuarioAsignadoId: [],
    estatusRequerimientoId: [],
    prioridadId: [],
    tipoSolicitudId: [],
    tipoIngresoId: [],
    baseTarifaId: [],
    esquemaContratacionId: [],
    perfilId: [],
    nivelPerfilId: [],
    estatusReqCanId: [],
    tipoPeriodoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected requerimientoService: RequerimientoService,
    protected cuentaService: CuentaService,
    protected userService: UserService,
    protected estatusRequerimientoService: EstatusRequerimientoService,
    protected prioridadReqService: PrioridadReqService,
    protected tipoSolicitudService: TipoSolicitudService,
    protected tipoIngresoService: TipoIngresoService,
    protected baseTarifaService: BaseTarifaService,
    protected esqContRecService: EsqContRecService,
    protected perfilService: PerfilService,
    protected nivelPerfilService: NivelPerfilService,
    protected estReqCerradoService: EstReqCerradoService,
    protected tipoPeriodoService: TipoPeriodoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.updateForm(requerimiento);
      this.requerimiento = requerimiento;
    });
    this.cuentaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICuenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICuenta[]>) => response.body)
      )
      .subscribe((res: ICuenta[]) => (this.cuentas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusRequerimientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusRequerimiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusRequerimiento[]>) => response.body)
      )
      .subscribe(
        (res: IEstatusRequerimiento[]) => (this.estatusrequerimientos = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.prioridadReqService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPrioridadReq[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPrioridadReq[]>) => response.body)
      )
      .subscribe((res: IPrioridadReq[]) => (this.prioridadreqs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoSolicitudService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoSolicitud[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoSolicitud[]>) => response.body)
      )
      .subscribe((res: ITipoSolicitud[]) => (this.tiposolicituds = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoIngresoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoIngreso[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoIngreso[]>) => response.body)
      )
      .subscribe((res: ITipoIngreso[]) => (this.tipoingresos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.baseTarifaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBaseTarifa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBaseTarifa[]>) => response.body)
      )
      .subscribe((res: IBaseTarifa[]) => (this.basetarifas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.esqContRecService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsqContRec[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsqContRec[]>) => response.body)
      )
      .subscribe((res: IEsqContRec[]) => (this.esqcontrecs = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    this.estReqCerradoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstReqCerrado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstReqCerrado[]>) => response.body)
      )
      .subscribe((res: IEstReqCerrado[]) => (this.estreqcerrados = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoPeriodoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoPeriodo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoPeriodo[]>) => response.body)
      )
      .subscribe((res: ITipoPeriodo[]) => (this.tipoperiodos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(requerimiento: IRequerimiento) {
    this.editForm.patchValue({
      id: requerimiento.id,
      fechaAlda: requerimiento.fechaAlda != null ? requerimiento.fechaAlda.format(DATE_TIME_FORMAT) : null,
      fechaResolucion: requerimiento.fechaResolucion != null ? requerimiento.fechaResolucion.format(DATE_TIME_FORMAT) : null,
      remplazoDe: requerimiento.remplazoDe,
      vacantesSolicitadas: requerimiento.vacantesSolicitadas,
      proyecto: requerimiento.proyecto,
      nombreContacto: requerimiento.nombreContacto,
      tarifaSueldoNet: requerimiento.tarifaSueldoNet,
      prestaciones: requerimiento.prestaciones,
      duracionAsignacion: requerimiento.duracionAsignacion,
      lugarTrabajo: requerimiento.lugarTrabajo,
      coorLat: requerimiento.coorLat,
      coorLong: requerimiento.coorLong,
      horario: requerimiento.horario,
      informacionExamen: requerimiento.informacionExamen,
      informacionAdicional: requerimiento.informacionAdicional,
      cuentaId: requerimiento.cuentaId,
      subCuentaId: requerimiento.subCuentaId,
      usuarioCreadorId: requerimiento.usuarioCreadorId,
      usuarioAsignadoId: requerimiento.usuarioAsignadoId,
      estatusRequerimientoId: requerimiento.estatusRequerimientoId,
      prioridadId: requerimiento.prioridadId,
      tipoSolicitudId: requerimiento.tipoSolicitudId,
      tipoIngresoId: requerimiento.tipoIngresoId,
      baseTarifaId: requerimiento.baseTarifaId,
      esquemaContratacionId: requerimiento.esquemaContratacionId,
      perfilId: requerimiento.perfilId,
      nivelPerfilId: requerimiento.nivelPerfilId,
      estatusReqCanId: requerimiento.estatusReqCanId,
      tipoPeriodoId: requerimiento.tipoPeriodoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const requerimiento = this.createFromForm();
    if (requerimiento.id !== undefined) {
      this.subscribeToSaveResponse(this.requerimientoService.update(requerimiento));
    } else {
      this.subscribeToSaveResponse(this.requerimientoService.create(requerimiento));
    }
  }

  private createFromForm(): IRequerimiento {
    const entity = {
      ...new Requerimiento(),
      id: this.editForm.get(['id']).value,
      fechaAlda:
        this.editForm.get(['fechaAlda']).value != null ? moment(this.editForm.get(['fechaAlda']).value, DATE_TIME_FORMAT) : undefined,
      fechaResolucion:
        this.editForm.get(['fechaResolucion']).value != null
          ? moment(this.editForm.get(['fechaResolucion']).value, DATE_TIME_FORMAT)
          : undefined,
      remplazoDe: this.editForm.get(['remplazoDe']).value,
      vacantesSolicitadas: this.editForm.get(['vacantesSolicitadas']).value,
      proyecto: this.editForm.get(['proyecto']).value,
      nombreContacto: this.editForm.get(['nombreContacto']).value,
      tarifaSueldoNet: this.editForm.get(['tarifaSueldoNet']).value,
      prestaciones: this.editForm.get(['prestaciones']).value,
      duracionAsignacion: this.editForm.get(['duracionAsignacion']).value,
      lugarTrabajo: this.editForm.get(['lugarTrabajo']).value,
      coorLat: this.editForm.get(['coorLat']).value,
      coorLong: this.editForm.get(['coorLong']).value,
      horario: this.editForm.get(['horario']).value,
      informacionExamen: this.editForm.get(['informacionExamen']).value,
      informacionAdicional: this.editForm.get(['informacionAdicional']).value,
      cuentaId: this.editForm.get(['cuentaId']).value,
      subCuentaId: this.editForm.get(['subCuentaId']).value,
      usuarioCreadorId: this.editForm.get(['usuarioCreadorId']).value,
      usuarioAsignadoId: this.editForm.get(['usuarioAsignadoId']).value,
      estatusRequerimientoId: this.editForm.get(['estatusRequerimientoId']).value,
      prioridadId: this.editForm.get(['prioridadId']).value,
      tipoSolicitudId: this.editForm.get(['tipoSolicitudId']).value,
      tipoIngresoId: this.editForm.get(['tipoIngresoId']).value,
      baseTarifaId: this.editForm.get(['baseTarifaId']).value,
      esquemaContratacionId: this.editForm.get(['esquemaContratacionId']).value,
      perfilId: this.editForm.get(['perfilId']).value,
      nivelPerfilId: this.editForm.get(['nivelPerfilId']).value,
      estatusReqCanId: this.editForm.get(['estatusReqCanId']).value,
      tipoPeriodoId: this.editForm.get(['tipoPeriodoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequerimiento>>) {
    result.subscribe((res: HttpResponse<IRequerimiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCuentaById(index: number, item: ICuenta) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackEstatusRequerimientoById(index: number, item: IEstatusRequerimiento) {
    return item.id;
  }

  trackPrioridadReqById(index: number, item: IPrioridadReq) {
    return item.id;
  }

  trackTipoSolicitudById(index: number, item: ITipoSolicitud) {
    return item.id;
  }

  trackTipoIngresoById(index: number, item: ITipoIngreso) {
    return item.id;
  }

  trackBaseTarifaById(index: number, item: IBaseTarifa) {
    return item.id;
  }

  trackEsqContRecById(index: number, item: IEsqContRec) {
    return item.id;
  }

  trackPerfilById(index: number, item: IPerfil) {
    return item.id;
  }

  trackNivelPerfilById(index: number, item: INivelPerfil) {
    return item.id;
  }

  trackEstReqCerradoById(index: number, item: IEstReqCerrado) {
    return item.id;
  }

  trackTipoPeriodoById(index: number, item: ITipoPeriodo) {
    return item.id;
  }
}
