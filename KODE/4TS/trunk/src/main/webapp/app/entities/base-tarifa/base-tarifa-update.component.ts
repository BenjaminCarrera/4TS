import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IBaseTarifa, BaseTarifa } from 'app/shared/model/base-tarifa.model';
import { BaseTarifaService } from './base-tarifa.service';

@Component({
  selector: 'jhi-base-tarifa-update',
  templateUrl: './base-tarifa-update.component.html'
})
export class BaseTarifaUpdateComponent implements OnInit {
  baseTarifa: IBaseTarifa;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    base: [null, [Validators.maxLength(100)]]
  });

  constructor(protected baseTarifaService: BaseTarifaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ baseTarifa }) => {
      this.updateForm(baseTarifa);
      this.baseTarifa = baseTarifa;
    });
  }

  updateForm(baseTarifa: IBaseTarifa) {
    this.editForm.patchValue({
      id: baseTarifa.id,
      base: baseTarifa.base
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const baseTarifa = this.createFromForm();
    if (baseTarifa.id !== undefined) {
      this.subscribeToSaveResponse(this.baseTarifaService.update(baseTarifa));
    } else {
      this.subscribeToSaveResponse(this.baseTarifaService.create(baseTarifa));
    }
  }

  private createFromForm(): IBaseTarifa {
    const entity = {
      ...new BaseTarifa(),
      id: this.editForm.get(['id']).value,
      base: this.editForm.get(['base']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBaseTarifa>>) {
    result.subscribe((res: HttpResponse<IBaseTarifa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
