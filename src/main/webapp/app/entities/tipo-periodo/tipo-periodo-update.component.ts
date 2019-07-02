import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoPeriodo, TipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from './tipo-periodo.service';

@Component({
  selector: 'jhi-tipo-periodo-update',
  templateUrl: './tipo-periodo-update.component.html'
})
export class TipoPeriodoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    periodo: [null, [Validators.maxLength(200)]]
  });

  constructor(protected tipoPeriodoService: TipoPeriodoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoPeriodo }) => {
      this.updateForm(tipoPeriodo);
    });
  }

  updateForm(tipoPeriodo: ITipoPeriodo) {
    this.editForm.patchValue({
      id: tipoPeriodo.id,
      periodo: tipoPeriodo.periodo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoPeriodo = this.createFromForm();
    if (tipoPeriodo.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoPeriodoService.update(tipoPeriodo));
    } else {
      this.subscribeToSaveResponse(this.tipoPeriodoService.create(tipoPeriodo));
    }
  }

  private createFromForm(): ITipoPeriodo {
    return {
      ...new TipoPeriodo(),
      id: this.editForm.get(['id']).value,
      periodo: this.editForm.get(['periodo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoPeriodo>>) {
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
