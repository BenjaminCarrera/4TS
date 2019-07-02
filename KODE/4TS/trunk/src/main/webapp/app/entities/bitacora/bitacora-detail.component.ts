import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBitacora } from 'app/shared/model/bitacora.model';

@Component({
  selector: 'jhi-bitacora-detail',
  templateUrl: './bitacora-detail.component.html'
})
export class BitacoraDetailComponent implements OnInit {
  bitacora: IBitacora;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ bitacora }) => {
      this.bitacora = bitacora;
    });
  }

  previousState() {
    window.history.back();
  }
}
