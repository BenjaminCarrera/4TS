import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategoriaSkill } from 'app/shared/model/categoria-skill.model';

@Component({
  selector: 'jhi-categoria-skill-detail',
  templateUrl: './categoria-skill-detail.component.html'
})
export class CategoriaSkillDetailComponent implements OnInit {
  categoriaSkill: ICategoriaSkill;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categoriaSkill }) => {
      this.categoriaSkill = categoriaSkill;
    });
  }

  previousState() {
    window.history.back();
  }
}
