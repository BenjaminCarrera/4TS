import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatusTarea, EstatusTarea } from 'app/shared/model/estatus-tarea.model';
import { EstatusTareaService } from './estatus-tarea.service';

@Component({
  selector: 'jhi-estatus-tarea-update',
  templateUrl: './estatus-tarea-update.component.html'
})
export class EstatusTareaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estatus: [null, [Validators.maxLength(100)]]
  });

  constructor(protected estatusTareaService: EstatusTareaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusTarea }) => {
      this.updateForm(estatusTarea);
    });
  }

  updateForm(estatusTarea: IEstatusTarea) {
    this.editForm.patchValue({
      id: estatusTarea.id,
      estatus: estatusTarea.estatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusTarea = this.createFromForm();
    if (estatusTarea.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusTareaService.update(estatusTarea));
    } else {
      this.subscribeToSaveResponse(this.estatusTareaService.create(estatusTarea));
    }
  }

  private createFromForm(): IEstatusTarea {
    return {
      ...new EstatusTarea(),
      id: this.editForm.get(['id']).value,
      estatus: this.editForm.get(['estatus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusTarea>>) {
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
