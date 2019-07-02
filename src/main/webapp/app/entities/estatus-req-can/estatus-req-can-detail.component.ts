import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusReqCan } from 'app/shared/model/estatus-req-can.model';

@Component({
  selector: 'jhi-estatus-req-can-detail',
  templateUrl: './estatus-req-can-detail.component.html'
})
export class EstatusReqCanDetailComponent implements OnInit {
  estatusReqCan: IEstatusReqCan;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusReqCan }) => {
      this.estatusReqCan = estatusReqCan;
    });
  }

  previousState() {
    window.history.back();
  }
}
