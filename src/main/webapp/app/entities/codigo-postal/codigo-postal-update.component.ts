import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICodigoPostal, CodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from './codigo-postal.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';

@Component({
  selector: 'jhi-codigo-postal-update',
  templateUrl: './codigo-postal-update.component.html'
})
export class CodigoPostalUpdateComponent implements OnInit {
  codigoPostal: ICodigoPostal;
  isSaving: boolean;

  municipios: IMunicipio[];

  editForm = this.fb.group({
    id: [],
    codigoPostal: [null, [Validators.maxLength(5)]],
    municipioId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected codigoPostalService: CodigoPostalService,
    protected municipioService: MunicipioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ codigoPostal }) => {
      this.updateForm(codigoPostal);
      this.codigoPostal = codigoPostal;
    });
    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(codigoPostal: ICodigoPostal) {
    this.editForm.patchValue({
      id: codigoPostal.id,
      codigoPostal: codigoPostal.codigoPostal,
      municipioId: codigoPostal.municipioId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const codigoPostal = this.createFromForm();
    if (codigoPostal.id !== undefined) {
      this.subscribeToSaveResponse(this.codigoPostalService.update(codigoPostal));
    } else {
      this.subscribeToSaveResponse(this.codigoPostalService.create(codigoPostal));
    }
  }

  private createFromForm(): ICodigoPostal {
    const entity = {
      ...new CodigoPostal(),
      id: this.editForm.get(['id']).value,
      codigoPostal: this.editForm.get(['codigoPostal']).value,
      municipioId: this.editForm.get(['municipioId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICodigoPostal>>) {
    result.subscribe((res: HttpResponse<ICodigoPostal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackMunicipioById(index: number, item: IMunicipio) {
    return item.id;
  }
}
