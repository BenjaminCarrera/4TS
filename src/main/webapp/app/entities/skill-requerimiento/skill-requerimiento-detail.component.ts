import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

@Component({
  selector: 'jhi-skill-requerimiento-detail',
  templateUrl: './skill-requerimiento-detail.component.html'
})
export class SkillRequerimientoDetailComponent implements OnInit {
  skillRequerimiento: ISkillRequerimiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillRequerimiento }) => {
      this.skillRequerimiento = skillRequerimiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
