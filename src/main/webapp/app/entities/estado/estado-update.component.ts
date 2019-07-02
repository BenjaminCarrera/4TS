import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstado, Estado } from 'app/shared/model/estado.model';
import { EstadoService } from './estado.service';

@Component({
  selector: 'jhi-estado-update',
  templateUrl: './estado-update.component.html'
})
export class EstadoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estado: [null, [Validators.maxLength(100)]]
  });

  constructor(protected estadoService: EstadoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estado }) => {
      this.updateForm(estado);
    });
  }

  updateForm(estado: IEstado) {
    this.editForm.patchValue({
      id: estado.id,
      estado: estado.estado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estado = this.createFromForm();
    if (estado.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoService.update(estado));
    } else {
      this.subscribeToSaveResponse(this.estadoService.create(estado));
    }
  }

  private createFromForm(): IEstado {
    return {
      ...new Estado(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstado>>) {
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
