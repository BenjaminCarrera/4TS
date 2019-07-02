import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRequerimiento } from 'app/shared/model/requerimiento.model';

@Component({
  selector: 'jhi-requerimiento-detail',
  templateUrl: './requerimiento-detail.component.html'
})
export class RequerimientoDetailComponent implements OnInit {
  requerimiento: IRequerimiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.requerimiento = requerimiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
