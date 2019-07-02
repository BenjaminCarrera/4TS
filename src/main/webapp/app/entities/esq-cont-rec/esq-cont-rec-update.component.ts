import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEsqContRec, EsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from './esq-cont-rec.service';

@Component({
  selector: 'jhi-esq-cont-rec-update',
  templateUrl: './esq-cont-rec-update.component.html'
})
export class EsqContRecUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    esquema: [null, [Validators.maxLength(100)]]
  });

  constructor(protected esqContRecService: EsqContRecService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ esqContRec }) => {
      this.updateForm(esqContRec);
    });
  }

  updateForm(esqContRec: IEsqContRec) {
    this.editForm.patchValue({
      id: esqContRec.id,
      esquema: esqContRec.esquema
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const esqContRec = this.createFromForm();
    if (esqContRec.id !== undefined) {
      this.subscribeToSaveResponse(this.esqContRecService.update(esqContRec));
    } else {
      this.subscribeToSaveResponse(this.esqContRecService.create(esqContRec));
    }
  }

  private createFromForm(): IEsqContRec {
    return {
      ...new EsqContRec(),
      id: this.editForm.get(['id']).value,
      esquema: this.editForm.get(['esquema']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEsqContRec>>) {
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
