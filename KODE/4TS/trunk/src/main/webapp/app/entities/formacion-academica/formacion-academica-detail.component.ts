import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFormacionAcademica } from 'app/shared/model/formacion-academica.model';

@Component({
  selector: 'jhi-formacion-academica-detail',
  templateUrl: './formacion-academica-detail.component.html'
})
export class FormacionAcademicaDetailComponent implements OnInit {
  formacionAcademica: IFormacionAcademica;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ formacionAcademica }) => {
      this.formacionAcademica = formacionAcademica;
    });
  }

  previousState() {
    window.history.back();
  }
}
