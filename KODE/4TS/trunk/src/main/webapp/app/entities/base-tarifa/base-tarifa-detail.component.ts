import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBaseTarifa } from 'app/shared/model/base-tarifa.model';

@Component({
  selector: 'jhi-base-tarifa-detail',
  templateUrl: './base-tarifa-detail.component.html'
})
export class BaseTarifaDetailComponent implements OnInit {
  baseTarifa: IBaseTarifa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ baseTarifa }) => {
      this.baseTarifa = baseTarifa;
    });
  }

  previousState() {
    window.history.back();
  }
}
