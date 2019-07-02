import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPrioridadReq, PrioridadReq } from 'app/shared/model/prioridad-req.model';
import { PrioridadReqService } from './prioridad-req.service';

@Component({
  selector: 'jhi-prioridad-req-update',
  templateUrl: './prioridad-req-update.component.html'
})
export class PrioridadReqUpdateComponent implements OnInit {
  prioridadReq: IPrioridadReq;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    prioridad: [null, [Validators.maxLength(100)]]
  });

  constructor(protected prioridadReqService: PrioridadReqService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prioridadReq }) => {
      this.updateForm(prioridadReq);
      this.prioridadReq = prioridadReq;
    });
  }

  updateForm(prioridadReq: IPrioridadReq) {
    this.editForm.patchValue({
      id: prioridadReq.id,
      prioridad: prioridadReq.prioridad
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prioridadReq = this.createFromForm();
    if (prioridadReq.id !== undefined) {
      this.subscribeToSaveResponse(this.prioridadReqService.update(prioridadReq));
    } else {
      this.subscribeToSaveResponse(this.prioridadReqService.create(prioridadReq));
    }
  }

  private createFromForm(): IPrioridadReq {
    const entity = {
      ...new PrioridadReq(),
      id: this.editForm.get(['id']).value,
      prioridad: this.editForm.get(['prioridad']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrioridadReq>>) {
    result.subscribe((res: HttpResponse<IPrioridadReq>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
