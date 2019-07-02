import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstatusReqCanRec, EstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';
import { EstatusReqCanRecService } from './estatus-req-can-rec.service';
import { IEstatusReqCan } from 'app/shared/model/estatus-req-can.model';
import { EstatusReqCanService } from 'app/entities/estatus-req-can';

@Component({
  selector: 'jhi-estatus-req-can-rec-update',
  templateUrl: './estatus-req-can-rec-update.component.html'
})
export class EstatusReqCanRecUpdateComponent implements OnInit {
  isSaving: boolean;

  estatusreqcans: IEstatusReqCan[];

  editForm = this.fb.group({
    id: [],
    motivo: [null, [Validators.maxLength(100)]],
    estatusReqCanId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected estatusReqCanRecService: EstatusReqCanRecService,
    protected estatusReqCanService: EstatusReqCanService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estatusReqCanRec }) => {
      this.updateForm(estatusReqCanRec);
    });
    this.estatusReqCanService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusReqCan[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusReqCan[]>) => response.body)
      )
      .subscribe((res: IEstatusReqCan[]) => (this.estatusreqcans = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(estatusReqCanRec: IEstatusReqCanRec) {
    this.editForm.patchValue({
      id: estatusReqCanRec.id,
      motivo: estatusReqCanRec.motivo,
      estatusReqCanId: estatusReqCanRec.estatusReqCanId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estatusReqCanRec = this.createFromForm();
    if (estatusReqCanRec.id !== undefined) {
      this.subscribeToSaveResponse(this.estatusReqCanRecService.update(estatusReqCanRec));
    } else {
      this.subscribeToSaveResponse(this.estatusReqCanRecService.create(estatusReqCanRec));
    }
  }

  private createFromForm(): IEstatusReqCanRec {
    return {
      ...new EstatusReqCanRec(),
      id: this.editForm.get(['id']).value,
      motivo: this.editForm.get(['motivo']).value,
      estatusReqCanId: this.editForm.get(['estatusReqCanId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstatusReqCanRec>>) {
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

  trackEstatusReqCanById(index: number, item: IEstatusReqCan) {
    return item.id;
  }
}
