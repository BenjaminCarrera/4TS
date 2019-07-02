import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';

@Component({
  selector: 'jhi-estatus-candidato-detail',
  templateUrl: './estatus-candidato-detail.component.html'
})
export class EstatusCandidatoDetailComponent implements OnInit {
  estatusCandidato: IEstatusCandidato;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusCandidato }) => {
      this.estatusCandidato = estatusCandidato;
    });
  }

  previousState() {
    window.history.back();
  }
}
