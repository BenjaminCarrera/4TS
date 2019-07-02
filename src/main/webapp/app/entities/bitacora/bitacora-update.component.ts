import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IBitacora, Bitacora } from 'app/shared/model/bitacora.model';
import { BitacoraService } from './bitacora.service';
import { IUser, UserService } from 'app/core';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from 'app/entities/requerimiento';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';
import { ITarea } from 'app/shared/model/tarea.model';
import { TareaService } from 'app/entities/tarea';

@Component({
  selector: 'jhi-bitacora-update',
  templateUrl: './bitacora-update.component.html'
})
export class BitacoraUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  requerimientos: IRequerimiento[];

  candidatoes: ICandidato[];

  tareas: ITarea[];

  editForm = this.fb.group({
    id: [],
    fecha: [],
    comentario: [null, [Validators.maxLength(500)]],
    usuarioId: [],
    requerimientoId: [],
    candidatoId: [],
    tareaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bitacoraService: BitacoraService,
    protected userService: UserService,
    protected requerimientoService: RequerimientoService,
    protected candidatoService: CandidatoService,
    protected tareaService: TareaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bitacora }) => {
      this.updateForm(bitacora);
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
    this.tareaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITarea[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITarea[]>) => response.body)
      )
      .subscribe((res: ITarea[]) => (this.tareas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bitacora: IBitacora) {
    this.editForm.patchValue({
      id: bitacora.id,
      fecha: bitacora.fecha != null ? bitacora.fecha.format(DATE_TIME_FORMAT) : null,
      comentario: bitacora.comentario,
      usuarioId: bitacora.usuarioId,
      requerimientoId: bitacora.requerimientoId,
      candidatoId: bitacora.candidatoId,
      tareaId: bitacora.tareaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bitacora = this.createFromForm();
    if (bitacora.id !== undefined) {
      this.subscribeToSaveResponse(this.bitacoraService.update(bitacora));
    } else {
      this.subscribeToSaveResponse(this.bitacoraService.create(bitacora));
    }
  }

  private createFromForm(): IBitacora {
    return {
      ...new Bitacora(),
      id: this.editForm.get(['id']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined,
      comentario: this.editForm.get(['comentario']).value,
      usuarioId: this.editForm.get(['usuarioId']).value,
      requerimientoId: this.editForm.get(['requerimientoId']).value,
      candidatoId: this.editForm.get(['candidatoId']).value,
      tareaId: this.editForm.get(['tareaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBitacora>>) {
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

  trackTareaById(index: number, item: ITarea) {
    return item.id;
  }
}
