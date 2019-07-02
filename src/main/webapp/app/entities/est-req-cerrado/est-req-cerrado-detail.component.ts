import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';

@Component({
  selector: 'jhi-est-req-cerrado-detail',
  templateUrl: './est-req-cerrado-detail.component.html'
})
export class EstReqCerradoDetailComponent implements OnInit {
  estReqCerrado: IEstReqCerrado;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estReqCerrado }) => {
      this.estReqCerrado = estReqCerrado;
    });
  }

  previousState() {
    window.history.back();
  }
}
