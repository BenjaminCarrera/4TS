import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';

@Component({
  selector: 'jhi-prioridad-req-detail',
  templateUrl: './prioridad-req-detail.component.html'
})
export class PrioridadReqDetailComponent implements OnInit {
  prioridadReq: IPrioridadReq;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prioridadReq }) => {
      this.prioridadReq = prioridadReq;
    });
  }

  previousState() {
    window.history.back();
  }
}
