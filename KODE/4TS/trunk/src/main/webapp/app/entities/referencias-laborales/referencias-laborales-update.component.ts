import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReferenciasLaborales, ReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';
import { ReferenciasLaboralesService } from './referencias-laborales.service';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';

@Component({
  selector: 'jhi-referencias-laborales-update',
  templateUrl: './referencias-laborales-update.component.html'
})
export class ReferenciasLaboralesUpdateComponent implements OnInit {
  referenciasLaborales: IReferenciasLaborales;
  isSaving: boolean;

  candidatoes: ICandidato[];

  editForm = this.fb.group({
    id: [],
    empresa: [null, [Validators.maxLength(200)]],
    nombreContacto: [null, [Validators.maxLength(200)]],
    puestoContacto: [null, [Validators.maxLength(100)]],
    emailContaco: [null, [Validators.maxLength(100)]],
    telefonoContacto: [null, [Validators.maxLength(20)]],
    candidatoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected referenciasLaboralesService: ReferenciasLaboralesService,
    protected candidatoService: CandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ referenciasLaborales }) => {
      this.updateForm(referenciasLaborales);
      this.referenciasLaborales = referenciasLaborales;
    });
    this.candidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICandidato[]>) => response.body)
      )
      .subscribe((res: ICandidato[]) => (this.candidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(referenciasLaborales: IReferenciasLaborales) {
    this.editForm.patchValue({
      id: referenciasLaborales.id,
      empresa: referenciasLaborales.empresa,
      nombreContacto: referenciasLaborales.nombreContacto,
      puestoContacto: referenciasLaborales.puestoContacto,
      emailContaco: referenciasLaborales.emailContaco,
      telefonoContacto: referenciasLaborales.telefonoContacto,
      candidatoId: referenciasLaborales.candidatoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const referenciasLaborales = this.createFromForm();
    if (referenciasLaborales.id !== undefined) {
      this.subscribeToSaveResponse(this.referenciasLaboralesService.update(referenciasLaborales));
    } else {
      this.subscribeToSaveResponse(this.referenciasLaboralesService.create(referenciasLaborales));
    }
  }

  private createFromForm(): IReferenciasLaborales {
    const entity = {
      ...new ReferenciasLaborales(),
      id: this.editForm.get(['id']).value,
      empresa: this.editForm.get(['empresa']).value,
      nombreContacto: this.editForm.get(['nombreContacto']).value,
      puestoContacto: this.editForm.get(['puestoContacto']).value,
      emailContaco: this.editForm.get(['emailContaco']).value,
      telefonoContacto: this.editForm.get(['telefonoContacto']).value,
      candidatoId: this.editForm.get(['candidatoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferenciasLaborales>>) {
    result.subscribe((res: HttpResponse<IReferenciasLaborales>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
