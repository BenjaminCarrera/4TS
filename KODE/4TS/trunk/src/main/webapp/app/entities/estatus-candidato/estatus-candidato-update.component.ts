import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatusCandidato, EstatusCandidato } from 'app/shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from './estatus-candidato.service';

@Component({
  selector: 'jhi-estatus-candidato-update',
  templateUrl: './estatus-candidato-update.component.html'
})
export class EstatusCandidatoUpdateComponent implements OnInit {
  estatusCandidato: IEstatusCandidato;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estatus: [null, [Validators.maxLength(200)]]
  });

  constructor(
    protected estatusCandidatoService: EstatusCandidatoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusCandidato }) => {
      this.updateForm(estatusCandidato);
      this.estatusCandidato = estatusCandidato;
    });
  }

  updateForm(estatusCandidato: IEstatusCandidato) {
    this.editForm.patchValue({
      id: estatusCandidato.id,
      estatus: estatusCandidato.estatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusCandidato = this.createFromForm();
    if (estatusCandidato.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusCandidatoService.update(estatusCandidato));
    } else {
      this.subscribeToSaveResponse(this.estatusCandidatoService.create(estatusCandidato));
    }
  }

  private createFromForm(): IEstatusCandidato {
    const entity = {
      ...new EstatusCandidato(),
      id: this.editForm.get(['id']).value,
      estatus: this.editForm.get(['estatus']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusCandidato>>) {
    result.subscribe((res: HttpResponse<IEstatusCandidato>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
