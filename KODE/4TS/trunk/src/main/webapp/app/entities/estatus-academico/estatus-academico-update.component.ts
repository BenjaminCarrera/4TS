import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatusAcademico, EstatusAcademico } from 'app/shared/model/estatus-academico.model';
import { EstatusAcademicoService } from './estatus-academico.service';

@Component({
  selector: 'jhi-estatus-academico-update',
  templateUrl: './estatus-academico-update.component.html'
})
export class EstatusAcademicoUpdateComponent implements OnInit {
  estatusAcademico: IEstatusAcademico;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estatus: [null, [Validators.maxLength(100)]]
  });

  constructor(
    protected estatusAcademicoService: EstatusAcademicoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusAcademico }) => {
      this.updateForm(estatusAcademico);
      this.estatusAcademico = estatusAcademico;
    });
  }

  updateForm(estatusAcademico: IEstatusAcademico) {
    this.editForm.patchValue({
      id: estatusAcademico.id,
      estatus: estatusAcademico.estatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusAcademico = this.createFromForm();
    if (estatusAcademico.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusAcademicoService.update(estatusAcademico));
    } else {
      this.subscribeToSaveResponse(this.estatusAcademicoService.create(estatusAcademico));
    }
  }

  private createFromForm(): IEstatusAcademico {
    const entity = {
      ...new EstatusAcademico(),
      id: this.editForm.get(['id']).value,
      estatus: this.editForm.get(['estatus']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusAcademico>>) {
    result.subscribe((res: HttpResponse<IEstatusAcademico>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
