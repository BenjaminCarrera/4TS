import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReqCan, ReqCan } from 'app/shared/model/req-can.model';
import { ReqCanService } from './req-can.service';
import { ICandidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from 'app/entities/candidato';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from 'app/entities/requerimiento';
import { IEstatusReqCan } from 'app/shared/model/estatus-req-can.model';
import { EstatusReqCanService } from 'app/entities/estatus-req-can';
import { IEstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';
import { EstatusReqCanRecService } from 'app/entities/estatus-req-can-rec';

@Component({
  selector: 'jhi-req-can-update',
  templateUrl: './req-can-update.component.html'
})
export class ReqCanUpdateComponent implements OnInit {
  reqCan: IReqCan;
  isSaving: boolean;

  candidatoes: ICandidato[];

  requerimientos: IRequerimiento[];

  estatusreqcans: IEstatusReqCan[];

  estatusreqcanrecs: IEstatusReqCanRec[];

  editForm = this.fb.group({
    id: [],
    candidatoId: [],
    requerimientoId: [],
    estatusReqCanId: [],
    estatusReqCanRecId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected reqCanService: ReqCanService,
    protected candidatoService: CandidatoService,
    protected requerimientoService: RequerimientoService,
    protected estatusReqCanService: EstatusReqCanService,
    protected estatusReqCanRecService: EstatusReqCanRecService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reqCan }) => {
      this.updateForm(reqCan);
      this.reqCan = reqCan;
    });
    this.candidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICandidato[]>) => response.body)
      )
      .subscribe((res: ICandidato[]) => (this.candidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.requerimientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRequerimiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRequerimiento[]>) => response.body)
      )
      .subscribe((res: IRequerimiento[]) => (this.requerimientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusReqCanService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusReqCan[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusReqCan[]>) => response.body)
      )
      .subscribe((res: IEstatusReqCan[]) => (this.estatusreqcans = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusReqCanRecService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusReqCanRec[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusReqCanRec[]>) => response.body)
      )
      .subscribe((res: IEstatusReqCanRec[]) => (this.estatusreqcanrecs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(reqCan: IReqCan) {
    this.editForm.patchValue({
      id: reqCan.id,
      candidatoId: reqCan.candidatoId,
      requerimientoId: reqCan.requerimientoId,
      estatusReqCanId: reqCan.estatusReqCanId,
      estatusReqCanRecId: reqCan.estatusReqCanRecId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reqCan = this.createFromForm();
    if (reqCan.id !== undefined) {
      this.subscribeToSaveResponse(this.reqCanService.update(reqCan));
    } else {
      this.subscribeToSaveResponse(this.reqCanService.create(reqCan));
    }
  }

  private createFromForm(): IReqCan {
    const entity = {
      ...new ReqCan(),
      id: this.editForm.get(['id']).value,
      candidatoId: this.editForm.get(['candidatoId']).value,
      requerimientoId: this.editForm.get(['requerimientoId']).value,
      estatusReqCanId: this.editForm.get(['estatusReqCanId']).value,
      estatusReqCanRecId: this.editForm.get(['estatusReqCanRecId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReqCan>>) {
    result.subscribe((res: HttpResponse<IReqCan>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRequerimientoById(index: number, item: IRequerimiento) {
    return item.id;
  }

  trackEstatusReqCanById(index: number, item: IEstatusReqCan) {
    return item.id;
  }

  trackEstatusReqCanRecById(index: number, item: IEstatusReqCanRec) {
    return item.id;
  }
}
