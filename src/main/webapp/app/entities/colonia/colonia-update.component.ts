import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IColonia, Colonia } from 'app/shared/model/colonia.model';
import { ColoniaService } from './colonia.service';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from 'app/entities/municipio';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from 'app/entities/codigo-postal';

@Component({
  selector: 'jhi-colonia-update',
  templateUrl: './colonia-update.component.html'
})
export class ColoniaUpdateComponent implements OnInit {
  colonia: IColonia;
  isSaving: boolean;

  municipios: IMunicipio[];

  codigopostals: ICodigoPostal[];

  editForm = this.fb.group({
    id: [],
    colonia: [null, [Validators.maxLength(100)]],
    municipioId: [],
    codigoPostalId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected coloniaService: ColoniaService,
    protected municipioService: MunicipioService,
    protected codigoPostalService: CodigoPostalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ colonia }) => {
      this.updateForm(colonia);
      this.colonia = colonia;
    });
    this.municipioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe((res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.codigoPostalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICodigoPostal[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICodigoPostal[]>) => response.body)
      )
      .subscribe((res: ICodigoPostal[]) => (this.codigopostals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(colonia: IColonia) {
    this.editForm.patchValue({
      id: colonia.id,
      colonia: colonia.colonia,
      municipioId: colonia.municipioId,
      codigoPostalId: colonia.codigoPostalId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const colonia = this.createFromForm();
    if (colonia.id !== undefined) {
      this.subscribeToSaveResponse(this.coloniaService.update(colonia));
    } else {
      this.subscribeToSaveResponse(this.coloniaService.create(colonia));
    }
  }

  private createFromForm(): IColonia {
    const entity = {
      ...new Colonia(),
      id: this.editForm.get(['id']).value,
      colonia: this.editForm.get(['colonia']).value,
      municipioId: this.editForm.get(['municipioId']).value,
      codigoPostalId: this.editForm.get(['codigoPostalId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColonia>>) {
    result.subscribe((res: HttpResponse<IColonia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCodigoPostalById(index: number, item: ICodigoPostal) {
    return item.id;
  }
}
