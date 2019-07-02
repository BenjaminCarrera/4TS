import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';

@Component({
  selector: 'jhi-esq-cont-rec-detail',
  templateUrl: './esq-cont-rec-detail.component.html'
})
export class EsqContRecDetailComponent implements OnInit {
  esqContRec: IEsqContRec;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ esqContRec }) => {
      this.esqContRec = esqContRec;
    });
  }

  previousState() {
    window.history.back();
  }
}
