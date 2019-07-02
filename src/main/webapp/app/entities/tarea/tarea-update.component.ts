import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITarea, Tarea } from 'app/shared/model/tarea.model';
import { TareaService } from './tarea.service';
import { IUser, UserService } from 'app/core';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from 'app/entities/requerimiento';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';
import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';
import { EstatusTareaService } from 'app/entities/estatus-tarea';
import { ITipoTarea } from 'app/shared/model/tipo-tarea.model';
import { TipoTareaService } from 'app/entities/tipo-tarea';

@Component({
  selector: 'jhi-tarea-update',
  templateUrl: './tarea-update.component.html'
})
export class TareaUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  requerimientos: IRequerimiento[];

  candidatoes: ICandidato[];

  estatustareas: IEstatusTarea[];

  tipotareas: ITipoTarea[];

  editForm = this.fb.group({
    id: [],
    descripcion: [null, [Validators.maxLength(300)]],
    titulo: [null, [Validators.maxLength(100)]],
    usuarioCreadorId: [],
    usuarioEjecutorId: [],
    requerimientoId: [],
    candidatoId: [],
    estatusTareaId: [],
    tipoTareaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected tareaService: TareaService,
    protected userService: UserService,
    protected requerimientoService: RequerimientoService,
    protected candidatoService: CandidatoService,
    protected estatusTareaService: EstatusTareaService,
    protected tipoTareaService: TipoTareaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tarea }) => {
      this.updateForm(tarea);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.requerimientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRequerimiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRequerimiento[]>) => response.body)
      )
      .subscribe((res: IRequerimiento[]) => (this.requerimientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.candidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICandidato[]>) => response.body)
      )
      .subscribe((res: ICandidato[]) => (this.candidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusTareaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusTarea[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusTarea[]>) => response.body)
      )
      .subscribe((res: IEstatusTarea[]) => (this.estatustareas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoTareaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoTarea[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoTarea[]>) => response.body)
      )
      .subscribe((res: ITipoTarea[]) => (this.tipotareas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tarea: ITarea) {
    this.editForm.patchValue({
      id: tarea.id,
      descripcion: tarea.descripcion,
      titulo: tarea.titulo,
      usuarioCreadorId: tarea.usuarioCreadorId,
      usuarioEjecutorId: tarea.usuarioEjecutorId,
      requerimientoId: tarea.requerimientoId,
      candidatoId: tarea.candidatoId,
      estatusTareaId: tarea.estatusTareaId,
      tipoTareaId: tarea.tipoTareaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tarea = this.createFromForm();
    if (tarea.id !== undefined) {
      this.subscribeToSaveResponse(this.tareaService.update(tarea));
    } else {
      this.subscribeToSaveResponse(this.tareaService.create(tarea));
    }
  }

  private createFromForm(): ITarea {
    return {
      ...new Tarea(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      titulo: this.editForm.get(['titulo']).value,
      usuarioCreadorId: this.editForm.get(['usuarioCreadorId']).value,
      usuarioEjecutorId: this.editForm.get(['usuarioEjecutorId']).value,
      requerimientoId: this.editForm.get(['requerimientoId']).value,
      candidatoId: this.editForm.get(['candidatoId']).value,
      estatusTareaId: this.editForm.get(['estatusTareaId']).value,
      tipoTareaId: this.editForm.get(['tipoTareaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITarea>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackRequerimientoById(index: number, item: IRequerimiento) {
    return item.id;
  }

  trackCandidatoById(index: number, item: ICandidato) {
    return item.id;
  }

  trackEstatusTareaById(index: number, item: IEstatusTarea) {
    return item.id;
  }

  trackTipoTareaById(index: number, item: ITipoTarea) {
    return item.id;
  }
}
