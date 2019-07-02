import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMunicipio, Municipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from './municipio.service';
import { IEstado } from 'app/shared/model/estado.model';
import { EstadoService } from 'app/entities/estado';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from 'app/entities/codigo-postal';

@Component({
  selector: 'jhi-municipio-update',
  templateUrl: './municipio-update.component.html'
})
export class MunicipioUpdateComponent implements OnInit {
  municipio: IMunicipio;
  isSaving: boolean;

  estados: IEstado[];

  codigopostals: ICodigoPostal[];

  editForm = this.fb.group({
    id: [],
    municipio: [null, [Validators.maxLength(100)]],
    estadoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected municipioService: MunicipioService,
    protected estadoService: EstadoService,
    protected codigoPostalService: CodigoPostalService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ municipio }) => {
      this.updateForm(municipio);
      this.municipio = municipio;
    });
    this.estadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstado[]>) => response.body)
      )
      .subscribe((res: IEstado[]) => (this.estados = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.codigoPostalService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICodigoPostal[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICodigoPostal[]>) => response.body)
      )
      .subscribe((res: ICodigoPostal[]) => (this.codigopostals = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(municipio: IMunicipio) {
    this.editForm.patchValue({
      id: municipio.id,
      municipio: municipio.municipio,
      estadoId: municipio.estadoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const municipio = this.createFromForm();
    if (municipio.id !== undefined) {
      this.subscribeToSaveResponse(this.municipioService.update(municipio));
    } else {
      this.subscribeToSaveResponse(this.municipioService.create(municipio));
    }
  }

  private createFromForm(): IMunicipio {
    const entity = {
      ...new Municipio(),
      id: this.editForm.get(['id']).value,
      municipio: this.editForm.get(['municipio']).value,
      estadoId: this.editForm.get(['estadoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipio>>) {
    result.subscribe((res: HttpResponse<IMunicipio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEstadoById(index: number, item: IEstado) {
    return item.id;
  }

  trackCodigoPostalById(index: number, item: ICodigoPostal) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
