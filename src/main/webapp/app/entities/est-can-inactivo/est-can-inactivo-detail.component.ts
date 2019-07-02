import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';

@Component({
  selector: 'jhi-est-can-inactivo-detail',
  templateUrl: './est-can-inactivo-detail.component.html'
})
export class EstCanInactivoDetailComponent implements OnInit {
  estCanInactivo: IEstCanInactivo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estCanInactivo }) => {
      this.estCanInactivo = estCanInactivo;
    });
  }

  previousState() {
    window.history.back();
  }
}
