import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatusLaboral, EstatusLaboral } from 'app/shared/model/estatus-laboral.model';
import { EstatusLaboralService } from './estatus-laboral.service';

@Component({
  selector: 'jhi-estatus-laboral-update',
  templateUrl: './estatus-laboral-update.component.html'
})
export class EstatusLaboralUpdateComponent implements OnInit {
  estatusLaboral: IEstatusLaboral;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estatus: [null, [Validators.maxLength(100)]]
  });

  constructor(protected estatusLaboralService: EstatusLaboralService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusLaboral }) => {
      this.updateForm(estatusLaboral);
      this.estatusLaboral = estatusLaboral;
    });
  }

  updateForm(estatusLaboral: IEstatusLaboral) {
    this.editForm.patchValue({
      id: estatusLaboral.id,
      estatus: estatusLaboral.estatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusLaboral = this.createFromForm();
    if (estatusLaboral.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusLaboralService.update(estatusLaboral));
    } else {
      this.subscribeToSaveResponse(this.estatusLaboralService.create(estatusLaboral));
    }
  }

  private createFromForm(): IEstatusLaboral {
    const entity = {
      ...new EstatusLaboral(),
      id: this.editForm.get(['id']).value,
      estatus: this.editForm.get(['estatus']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusLaboral>>) {
    result.subscribe((res: HttpResponse<IEstatusLaboral>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
