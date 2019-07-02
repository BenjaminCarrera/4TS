import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';

@Component({
  selector: 'jhi-esquema-contratacion-kode-detail',
  templateUrl: './esquema-contratacion-kode-detail.component.html'
})
export class EsquemaContratacionKodeDetailComponent implements OnInit {
  esquemaContratacionKode: IEsquemaContratacionKode;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ esquemaContratacionKode }) => {
      this.esquemaContratacionKode = esquemaContratacionKode;
    });
  }

  previousState() {
    window.history.back();
  }
}
