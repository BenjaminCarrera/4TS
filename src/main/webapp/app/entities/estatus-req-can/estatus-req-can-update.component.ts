import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IEstatusReqCan, EstatusReqCan } from 'app/shared/model/estatus-req-can.model';
import { EstatusReqCanService } from './estatus-req-can.service';

@Component({
  selector: 'jhi-estatus-req-can-update',
  templateUrl: './estatus-req-can-update.component.html'
})
export class EstatusReqCanUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    estatus: [null, [Validators.maxLength(100)]]
  });

  constructor(protected estatusReqCanService: EstatusReqCanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusReqCan }) => {
      this.updateForm(estatusReqCan);
    });
  }

  updateForm(estatusReqCan: IEstatusReqCan) {
    this.editForm.patchValue({
      id: estatusReqCan.id,
      estatus: estatusReqCan.estatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusReqCan = this.createFromForm();
    if (estatusReqCan.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusReqCanService.update(estatusReqCan));
    } else {
      this.subscribeToSaveResponse(this.estatusReqCanService.create(estatusReqCan));
    }
  }

  private createFromForm(): IEstatusReqCan {
    return {
      ...new EstatusReqCan(),
      id: this.editForm.get(['id']).value,
      estatus: this.editForm.get(['estatus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusReqCan>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
