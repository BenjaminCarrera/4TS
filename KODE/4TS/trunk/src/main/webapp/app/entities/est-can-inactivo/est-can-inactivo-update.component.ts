import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstCanInactivo, EstCanInactivo } from 'app/shared/model/est-can-inactivo.model';
import { EstCanInactivoService } from './est-can-inactivo.service';
import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from 'app/entities/estatus-candidato';

@Component({
  selector: 'jhi-est-can-inactivo-update',
  templateUrl: './est-can-inactivo-update.component.html'
})
export class EstCanInactivoUpdateComponent implements OnInit {
  estCanInactivo: IEstCanInactivo;
  isSaving: boolean;

  estatuscandidatoes: IEstatusCandidato[];

  editForm = this.fb.group({
    id: [],
    motivo: [null, [Validators.maxLength(200)]],
    estatusCandidatoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected estCanInactivoService: EstCanInactivoService,
    protected estatusCandidatoService: EstatusCandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estCanInactivo }) => {
      this.updateForm(estCanInactivo);
      this.estCanInactivo = estCanInactivo;
    });
    this.estatusCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusCandidato[]>) => response.body)
      )
      .subscribe((res: IEstatusCandidato[]) => (this.estatuscandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(estCanInactivo: IEstCanInactivo) {
    this.editForm.patchValue({
      id: estCanInactivo.id,
      motivo: estCanInactivo.motivo,
      estatusCandidatoId: estCanInactivo.estatusCandidatoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estCanInactivo = this.createFromForm();
    if (estCanInactivo.id !== undefined) {
      this.subscribeToSaveResponse(this.estCanInactivoService.update(estCanInactivo));
    } else {
      this.subscribeToSaveResponse(this.estCanInactivoService.create(estCanInactivo));
    }
  }

  private createFromForm(): IEstCanInactivo {
    const entity = {
      ...new EstCanInactivo(),
      id: this.editForm.get(['id']).value,
      motivo: this.editForm.get(['motivo']).value,
      estatusCandidatoId: this.editForm.get(['estatusCandidatoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstCanInactivo>>) {
    result.subscribe((res: HttpResponse<IEstCanInactivo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEstatusCandidatoById(index: number, item: IEstatusCandidato) {
    return item.id;
  }
}
