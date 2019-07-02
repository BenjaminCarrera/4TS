import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';

@Component({
  selector: 'jhi-estatus-laboral-detail',
  templateUrl: './estatus-laboral-detail.component.html'
})
export class EstatusLaboralDetailComponent implements OnInit {
  estatusLaboral: IEstatusLaboral;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusLaboral }) => {
      this.estatusLaboral = estatusLaboral;
    });
  }

  previousState() {
    window.history.back();
  }
}
