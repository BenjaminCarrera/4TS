import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDominioSkill } from 'app/shared/model/dominio-skill.model';

@Component({
  selector: 'jhi-dominio-skill-detail',
  templateUrl: './dominio-skill-detail.component.html'
})
export class DominioSkillDetailComponent implements OnInit {
  dominioSkill: IDominioSkill;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dominioSkill }) => {
      this.dominioSkill = dominioSkill;
    });
  }

  previousState() {
    window.history.back();
  }
}
