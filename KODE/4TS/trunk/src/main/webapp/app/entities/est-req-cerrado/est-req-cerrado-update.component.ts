import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstReqCerrado, EstReqCerrado } from 'app/shared/model/est-req-cerrado.model';
import { EstReqCerradoService } from './est-req-cerrado.service';
import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from 'app/entities/estatus-requerimiento';

@Component({
  selector: 'jhi-est-req-cerrado-update',
  templateUrl: './est-req-cerrado-update.component.html'
})
export class EstReqCerradoUpdateComponent implements OnInit {
  estReqCerrado: IEstReqCerrado;
  isSaving: boolean;

  estatusrequerimientos: IEstatusRequerimiento[];

  editForm = this.fb.group({
    id: [],
    motivo: [null, [Validators.maxLength(100)]],
    estatusRequerimientoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected estReqCerradoService: EstReqCerradoService,
    protected estatusRequerimientoService: EstatusRequerimientoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estReqCerrado }) => {
      this.updateForm(estReqCerrado);
      this.estReqCerrado = estReqCerrado;
    });
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
  }

  updateForm(estReqCerrado: IEstReqCerrado) {
    this.editForm.patchValue({
      id: estReqCerrado.id,
      motivo: estReqCerrado.motivo,
      estatusRequerimientoId: estReqCerrado.estatusRequerimientoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estReqCerrado = this.createFromForm();
    if (estReqCerrado.id !== undefined) {
      this.subscribeToSaveResponse(this.estReqCerradoService.update(estReqCerrado));
    } else {
      this.subscribeToSaveResponse(this.estReqCerradoService.create(estReqCerrado));
    }
  }

  private createFromForm(): IEstReqCerrado {
    const entity = {
      ...new EstReqCerrado(),
      id: this.editForm.get(['id']).value,
      motivo: this.editForm.get(['motivo']).value,
      estatusRequerimientoId: this.editForm.get(['estatusRequerimientoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstReqCerrado>>) {
    result.subscribe((res: HttpResponse<IEstReqCerrado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEstatusRequerimientoById(index: number, item: IEstatusRequerimiento) {
    return item.id;
  }
}
