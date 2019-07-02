import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoSolicitud, TipoSolicitud } from 'app/shared/model/tipo-solicitud.model';
import { TipoSolicitudService } from './tipo-solicitud.service';

@Component({
  selector: 'jhi-tipo-solicitud-update',
  templateUrl: './tipo-solicitud-update.component.html'
})
export class TipoSolicitudUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    solicitud: [null, [Validators.maxLength(100)]]
  });

  constructor(protected tipoSolicitudService: TipoSolicitudService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoSolicitud }) => {
      this.updateForm(tipoSolicitud);
    });
  }

  updateForm(tipoSolicitud: ITipoSolicitud) {
    this.editForm.patchValue({
      id: tipoSolicitud.id,
      solicitud: tipoSolicitud.solicitud
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoSolicitud = this.createFromForm();
    if (tipoSolicitud.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoSolicitudService.update(tipoSolicitud));
    } else {
      this.subscribeToSaveResponse(this.tipoSolicitudService.create(tipoSolicitud));
    }
  }

  private createFromForm(): ITipoSolicitud {
    return {
      ...new TipoSolicitud(),
      id: this.editForm.get(['id']).value,
      solicitud: this.editForm.get(['solicitud']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoSolicitud>>) {
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
