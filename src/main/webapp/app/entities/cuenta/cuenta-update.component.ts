import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICuenta, Cuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from './cuenta.service';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';

@Component({
  selector: 'jhi-cuenta-update',
  templateUrl: './cuenta-update.component.html'
})
export class CuentaUpdateComponent implements OnInit {
  isSaving: boolean;

  candidatoes: ICandidato[];

  editForm = this.fb.group({
    id: [],
    clave: [null, [Validators.maxLength(100)]],
    nombre: [null, [Validators.maxLength(100)]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cuentaService: CuentaService,
    protected candidatoService: CandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cuenta }) => {
      this.updateForm(cuenta);
    });
    this.candidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICandidato[]>) => response.body)
      )
      .subscribe((res: ICandidato[]) => (this.candidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cuenta: ICuenta) {
    this.editForm.patchValue({
      id: cuenta.id,
      clave: cuenta.clave,
      nombre: cuenta.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cuenta = this.createFromForm();
    if (cuenta.id !== undefined) {
      this.subscribeToSaveResponse(this.cuentaService.update(cuenta));
    } else {
      this.subscribeToSaveResponse(this.cuentaService.create(cuenta));
    }
  }

  private createFromForm(): ICuenta {
    return {
      ...new Cuenta(),
      id: this.editForm.get(['id']).value,
      clave: this.editForm.get(['clave']).value,
      nombre: this.editForm.get(['nombre']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICuenta>>) {
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

  trackCandidatoById(index: number, item: ICandidato) {
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
