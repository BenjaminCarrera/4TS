import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';

@Component({
  selector: 'jhi-estatus-requerimiento-detail',
  templateUrl: './estatus-requerimiento-detail.component.html'
})
export class EstatusRequerimientoDetailComponent implements OnInit {
  estatusRequerimiento: IEstatusRequerimiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusRequerimiento }) => {
      this.estatusRequerimiento = estatusRequerimiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
