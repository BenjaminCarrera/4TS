import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoIngreso } from 'app/shared/model/tipo-ingreso.model';

@Component({
  selector: 'jhi-tipo-ingreso-detail',
  templateUrl: './tipo-ingreso-detail.component.html'
})
export class TipoIngresoDetailComponent implements OnInit {
  tipoIngreso: ITipoIngreso;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoIngreso }) => {
      this.tipoIngreso = tipoIngreso;
    });
  }

  previousState() {
    window.history.back();
  }
}
