import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';

@Component({
  selector: 'jhi-estatus-req-can-rec-detail',
  templateUrl: './estatus-req-can-rec-detail.component.html'
})
export class EstatusReqCanRecDetailComponent implements OnInit {
  estatusReqCanRec: IEstatusReqCanRec;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusReqCanRec }) => {
      this.estatusReqCanRec = estatusReqCanRec;
    });
  }

  previousState() {
    window.history.back();
  }
}
