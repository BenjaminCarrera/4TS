import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstatusAcademico } from 'app/shared/model/estatus-academico.model';

@Component({
  selector: 'jhi-estatus-academico-detail',
  templateUrl: './estatus-academico-detail.component.html'
})
export class EstatusAcademicoDetailComponent implements OnInit {
  estatusAcademico: IEstatusAcademico;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estatusAcademico }) => {
      this.estatusAcademico = estatusAcademico;
    });
  }

  previousState() {
    window.history.back();
  }
}
