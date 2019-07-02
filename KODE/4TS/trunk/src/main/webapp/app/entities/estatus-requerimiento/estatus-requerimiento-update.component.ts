import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatusRequerimiento, EstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from './estatus-requerimiento.service';

@Component({
  selector: 'jhi-estatus-requerimiento-update',
  templateUrl: './estatus-requerimiento-update.component.html'
})
export class EstatusRequerimientoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estatus: [null, [Validators.maxLength(100)]]
  });

  constructor(
    protected estatusRequerimientoService: EstatusRequerimientoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusRequerimiento }) => {
      this.updateForm(estatusRequerimiento);
    });
  }

  updateForm(estatusRequerimiento: IEstatusRequerimiento) {
    this.editForm.patchValue({
      id: estatusRequerimiento.id,
      estatus: estatusRequerimiento.estatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusRequerimiento = this.createFromForm();
    if (estatusRequerimiento.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusRequerimientoService.update(estatusRequerimiento));
    } else {
      this.subscribeToSaveResponse(this.estatusRequerimientoService.create(estatusRequerimiento));
    }
  }

  private createFromForm(): IEstatusRequerimiento {
    return {
      ...new EstatusRequerimiento(),
      id: this.editForm.get(['id']).value,
      estatus: this.editForm.get(['estatus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusRequerimiento>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
