import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';

@Component({
  selector: 'jhi-tipo-periodo-detail',
  templateUrl: './tipo-periodo-detail.component.html'
})
export class TipoPeriodoDetailComponent implements OnInit {
  tipoPeriodo: ITipoPeriodo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoPeriodo }) => {
      this.tipoPeriodo = tipoPeriodo;
    });
  }

  previousState() {
    window.history.back();
  }
}
