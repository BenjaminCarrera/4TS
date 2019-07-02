import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstitucionAcademica } from 'app/shared/model/institucion-academica.model';

@Component({
  selector: 'jhi-institucion-academica-detail',
  templateUrl: './institucion-academica-detail.component.html'
})
export class InstitucionAcademicaDetailComponent implements OnInit {
  institucionAcademica: IInstitucionAcademica;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ institucionAcademica }) => {
      this.institucionAcademica = institucionAcademica;
    });
  }

  previousState() {
    window.history.back();
  }
}
