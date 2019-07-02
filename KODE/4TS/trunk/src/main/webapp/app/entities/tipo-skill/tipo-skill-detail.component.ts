import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoSkill } from 'app/shared/model/tipo-skill.model';

@Component({
  selector: 'jhi-tipo-skill-detail',
  templateUrl: './tipo-skill-detail.component.html'
})
export class TipoSkillDetailComponent implements OnInit {
  tipoSkill: ITipoSkill;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipoSkill }) => {
      this.tipoSkill = tipoSkill;
    });
  }

  previousState() {
    window.history.back();
  }
}
