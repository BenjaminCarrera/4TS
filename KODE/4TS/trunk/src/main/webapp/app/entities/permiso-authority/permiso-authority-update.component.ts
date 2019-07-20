import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPermisoAuthority, PermisoAuthority } from 'app/shared/model/permiso-authority.model';
import { PermisoAuthorityService } from './permiso-authority.service';
import { IPermiso } from 'app/shared/model/permiso.model';
import { PermisoService } from 'app/entities/permiso';

@Component({
  selector: 'jhi-permiso-authority-update',
  templateUrl: './permiso-authority-update.component.html'
})
export class PermisoAuthorityUpdateComponent implements OnInit {
  isSaving: boolean;

  permisos: IPermiso[];

  editForm = this.fb.group({
    id: [],
    authority: [],
    activated: [],
    deleted: [],
    createdBy: [null, [Validators.maxLength(50)]],
    createdDate: [],
    lastModifiedBy: [null, [Validators.maxLength(50)]],
    lastModifiedDate: [],
    permisoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected permisoAuthorityService: PermisoAuthorityService,
    protected permisoService: PermisoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ permisoAuthority }) => {
      this.updateForm(permisoAuthority);
    });
    this.permisoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPermiso[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPermiso[]>) => response.body)
      )
      .subscribe((res: IPermiso[]) => (this.permisos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(permisoAuthority: IPermisoAuthority) {
    this.editForm.patchValue({
      id: permisoAuthority.id,
      authority: permisoAuthority.authority,
      activated: permisoAuthority.activated,
      deleted: permisoAuthority.deleted,
      createdBy: permisoAuthority.createdBy,
      createdDate: permisoAuthority.createdDate != null ? permisoAuthority.createdDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: permisoAuthority.lastModifiedBy,
      lastModifiedDate: permisoAuthority.lastModifiedDate != null ? permisoAuthority.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      permisoId: permisoAuthority.permisoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const permisoAuthority = this.createFromForm();
    if (permisoAuthority.id !== undefined) {
      this.subscribeToSaveResponse(this.permisoAuthorityService.update(permisoAuthority));
    } else {
      this.subscribeToSaveResponse(this.permisoAuthorityService.create(permisoAuthority));
    }
  }

  private createFromForm(): IPermisoAuthority {
    return {
      ...new PermisoAuthority(),
      id: this.editForm.get(['id']).value,
      authority: this.editForm.get(['authority']).value,
      activated: this.editForm.get(['activated']).value,
      deleted: this.editForm.get(['deleted']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy']).value,
      lastModifiedDate:
        this.editForm.get(['lastModifiedDate']).value != null
          ? moment(this.editForm.get(['lastModifiedDate']).value, DATE_TIME_FORMAT)
          : undefined,
      permisoId: this.editForm.get(['permisoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermisoAuthority>>) {
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

  trackPermisoById(index: number, item: IPermiso) {
    return item.id;
  }
}
