import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoTarea, TipoTarea } from 'app/shared/model/tipo-tarea.model';
import { TipoTareaService } from './tipo-tarea.service';

@Component({
  selector: 'jhi-tipo-tarea-update',
  templateUrl: './tipo-tarea-update.component.html'
})
export class TipoTareaUpdateComponent implements OnInit {
  tipoTarea: ITipoTarea;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    tipo: [null, [Validators.maxLength(100)]]
  });

  constructor(protected tipoTareaService: TipoTareaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoTarea }) => {
      this.updateForm(tipoTarea);
      this.tipoTarea = tipoTarea;
    });
  }

  updateForm(tipoTarea: ITipoTarea) {
    this.editForm.patchValue({
      id: tipoTarea.id,
      tipo: tipoTarea.tipo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoTarea = this.createFromForm();
    if (tipoTarea.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoTareaService.update(tipoTarea));
    } else {
      this.subscribeToSaveResponse(this.tipoTareaService.create(tipoTarea));
    }
  }

  private createFromForm(): ITipoTarea {
    const entity = {
      ...new TipoTarea(),
      id: this.editForm.get(['id']).value,
      tipo: this.editForm.get(['tipo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoTarea>>) {
    result.subscribe((res: HttpResponse<ITipoTarea>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
