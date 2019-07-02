import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEsquemaContratacionKode, EsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';
import { EsquemaContratacionKodeService } from './esquema-contratacion-kode.service';

@Component({
  selector: 'jhi-esquema-contratacion-kode-update',
  templateUrl: './esquema-contratacion-kode-update.component.html'
})
export class EsquemaContratacionKodeUpdateComponent implements OnInit {
  esquemaContratacionKode: IEsquemaContratacionKode;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    esquema: [null, [Validators.maxLength(100)]]
  });

  constructor(
    protected esquemaContratacionKodeService: EsquemaContratacionKodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ esquemaContratacionKode }) => {
      this.updateForm(esquemaContratacionKode);
      this.esquemaContratacionKode = esquemaContratacionKode;
    });
  }

  updateForm(esquemaContratacionKode: IEsquemaContratacionKode) {
    this.editForm.patchValue({
      id: esquemaContratacionKode.id,
      esquema: esquemaContratacionKode.esquema
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const esquemaContratacionKode = this.createFromForm();
    if (esquemaContratacionKode.id !== undefined) {
      this.subscribeToSaveResponse(this.esquemaContratacionKodeService.update(esquemaContratacionKode));
    } else {
      this.subscribeToSaveResponse(this.esquemaContratacionKodeService.create(esquemaContratacionKode));
    }
  }

  private createFromForm(): IEsquemaContratacionKode {
    const entity = {
      ...new EsquemaContratacionKode(),
      id: this.editForm.get(['id']).value,
      esquema: this.editForm.get(['esquema']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEsquemaContratacionKode>>) {
    result.subscribe((res: HttpResponse<IEsquemaContratacionKode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
