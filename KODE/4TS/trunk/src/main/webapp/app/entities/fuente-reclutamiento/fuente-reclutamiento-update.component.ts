import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFuenteReclutamiento, FuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';
import { FuenteReclutamientoService } from './fuente-reclutamiento.service';

@Component({
  selector: 'jhi-fuente-reclutamiento-update',
  templateUrl: './fuente-reclutamiento-update.component.html'
})
export class FuenteReclutamientoUpdateComponent implements OnInit {
  fuenteReclutamiento: IFuenteReclutamiento;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    fuente: [null, [Validators.maxLength(100)]]
  });

  constructor(
    protected fuenteReclutamientoService: FuenteReclutamientoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fuenteReclutamiento }) => {
      this.updateForm(fuenteReclutamiento);
      this.fuenteReclutamiento = fuenteReclutamiento;
    });
  }

  updateForm(fuenteReclutamiento: IFuenteReclutamiento) {
    this.editForm.patchValue({
      id: fuenteReclutamiento.id,
      fuente: fuenteReclutamiento.fuente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const fuenteReclutamiento = this.createFromForm();
    if (fuenteReclutamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.fuenteReclutamientoService.update(fuenteReclutamiento));
    } else {
      this.subscribeToSaveResponse(this.fuenteReclutamientoService.create(fuenteReclutamiento));
    }
  }

  private createFromForm(): IFuenteReclutamiento {
    const entity = {
      ...new FuenteReclutamiento(),
      id: this.editForm.get(['id']).value,
      fuente: this.editForm.get(['fuente']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuenteReclutamiento>>) {
    result.subscribe((res: HttpResponse<IFuenteReclutamiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
