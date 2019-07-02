import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';

@Component({
  selector: 'jhi-skill-candidato-detail',
  templateUrl: './skill-candidato-detail.component.html'
})
export class SkillCandidatoDetailComponent implements OnInit {
  skillCandidato: ISkillCandidato;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ skillCandidato }) => {
      this.skillCandidato = skillCandidato;
    });
  }

  previousState() {
    window.history.back();
  }
}
