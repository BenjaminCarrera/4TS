import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';

@Component({
  selector: 'jhi-fuente-reclutamiento-detail',
  templateUrl: './fuente-reclutamiento-detail.component.html'
})
export class FuenteReclutamientoDetailComponent implements OnInit {
  fuenteReclutamiento: IFuenteReclutamiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fuenteReclutamiento }) => {
      this.fuenteReclutamiento = fuenteReclutamiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
