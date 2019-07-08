import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPermisoAuthority } from 'app/shared/model/permiso-authority.model';

@Component({
  selector: 'jhi-permiso-authority-detail',
  templateUrl: './permiso-authority-detail.component.html'
})
export class PermisoAuthorityDetailComponent implements OnInit {
  permisoAuthority: IPermisoAuthority;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ permisoAuthority }) => {
      this.permisoAuthority = permisoAuthority;
    });
  }

  previousState() {
    window.history.back();
  }
}
