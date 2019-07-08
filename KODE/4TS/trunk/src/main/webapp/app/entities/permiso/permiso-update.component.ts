import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPermiso, Permiso } from 'app/shared/model/permiso.model';
import { PermisoService } from './permiso.service';

@Component({
  selector: 'jhi-permiso-update',
  templateUrl: './permiso-update.component.html'
})
export class PermisoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    activated: [],
    deleted: [],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: []
  });

  constructor(protected permisoService: PermisoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ permiso }) => {
      this.updateForm(permiso);
    });
  }

  updateForm(permiso: IPermiso) {
    this.editForm.patchValue({
      id: permiso.id,
      nombre: permiso.nombre,
      descripcion: permiso.descripcion,
      activated: permiso.activated,
      deleted: permiso.deleted,
      createdBy: permiso.createdBy,
      createdDate: permiso.createdDate != null ? permiso.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: permiso.lastModifiedBy,
      lastModifiedDate: permiso.lastModifiedDate != null ? permiso.lastModifiedDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const permiso = this.createFromForm();
    if (permiso.id !== undefined) {
      this.subscribeToSaveResponse(this.permisoService.update(permiso));
    } else {
      this.subscribeToSaveResponse(this.permisoService.create(permiso));
    }
  }

  private createFromForm(): IPermiso {
    return {
      ...new Permiso(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      activated: this.editForm.get(['activated']).value,
      deleted: this.editForm.get(['deleted']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      lastModifiedDate:
        this.editForm.get(['lastModifiedDate']).value != null
          ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
          : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermiso>>) {
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
