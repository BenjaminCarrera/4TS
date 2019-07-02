import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';

@Component({
  selector: 'jhi-referencias-laborales-detail',
  templateUrl: './referencias-laborales-detail.component.html'
})
export class ReferenciasLaboralesDetailComponent implements OnInit {
  referenciasLaborales: IReferenciasLaborales;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ referenciasLaborales }) => {
      this.referenciasLaborales = referenciasLaborales;
    });
  }

  previousState() {
    window.history.back();
  }
}
