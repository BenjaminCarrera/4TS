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

@Component({
  selector: 'jhi-municipio-update',
  templateUrl: './municipio-update.component.html'
})
export class MunicipioUpdateComponent implements OnInit {
  isSaving: boolean;

  estados: IEstado[];

  editForm = this.fb.group({
    id: [],
    municipio: [null, [Validators.maxLength(100)]],
    estadoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected municipioService: MunicipioService,
    protected estadoService: EstadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ municipio }) => {
      this.updateForm(municipio);
    });
    this.estadoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstado[]>) => response.body)
      )
      .subscribe((res: IEstado[]) => (this.estados = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    return {
      ...new Municipio(),
      id: this.editForm.get(['id']).value,
      municipio: this.editForm.get(['municipio']).value,
      estadoId: this.editForm.get(['estadoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMunicipio>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
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
}
