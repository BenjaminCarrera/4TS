import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';

@Component({
  selector: 'jhi-nivel-perfil-detail',
  templateUrl: './nivel-perfil-detail.component.html'
})
export class NivelPerfilDetailComponent implements OnInit {
  nivelPerfil: INivelPerfil;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nivelPerfil }) => {
      this.nivelPerfil = nivelPerfil;
    });
  }

  previousState() {
    window.history.back();
  }
}
