import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoIngreso, TipoIngreso } from 'app/shared/model/tipo-ingreso.model';
import { TipoIngresoService } from './tipo-ingreso.service';

@Component({
  selector: 'jhi-tipo-ingreso-update',
  templateUrl: './tipo-ingreso-update.component.html'
})
export class TipoIngresoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.maxLength(100)]]
  });

  constructor(protected tipoIngresoService: TipoIngresoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoIngreso }) => {
      this.updateForm(tipoIngreso);
    });
  }

  updateForm(tipoIngreso: ITipoIngreso) {
    this.editForm.patchValue({
      id: tipoIngreso.id,
      tipo: tipoIngreso.tipo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoIngreso = this.createFromForm();
    if (tipoIngreso.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoIngresoService.update(tipoIngreso));
    } else {
      this.subscribeToSaveResponse(this.tipoIngresoService.create(tipoIngreso));
    }
  }

  private createFromForm(): ITipoIngreso {
    return {
      ...new TipoIngreso(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoIngreso>>) {
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
