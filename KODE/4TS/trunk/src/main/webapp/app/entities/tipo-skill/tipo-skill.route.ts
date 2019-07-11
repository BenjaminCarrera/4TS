import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoSkill } from 'app/shared/model/tipo-skill.model';
import { TipoSkillService } from './tipo-skill.service';
import { TipoSkillComponent } from './tipo-skill.component';
import { TipoSkillDetailComponent } from './tipo-skill-detail.component';
import { TipoSkillUpdateComponent } from './tipo-skill-update.component';
import { TipoSkillDeletePopupComponent } from './tipo-skill-delete-dialog.component';
import { ITipoSkill } from 'app/shared/model/tipo-skill.model';

@Injectable({ providedIn: 'root' })
export class TipoSkillResolve implements Resolve<ITipoSkill> {
  constructor(private service: TipoSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoSkill> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoSkill>) => response.ok),
        map((tipoSkill: HttpResponse<TipoSkill>) => tipoSkill.body)
      );
    }
    return of(new TipoSkill());
  }
}

export const tipoSkillRoute: Routes = [
  {
    path: '',
    component: TipoSkillComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['93'],
      defaultSort: 'id,asc',
      pageTitle: 'App.tipoSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoSkillDetailComponent,
    resolve: {
      tipoSkill: TipoSkillResolve
    },
    data: {
      authorities: ['93'],
      pageTitle: 'App.tipoSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoSkillUpdateComponent,
    resolve: {
      tipoSkill: TipoSkillResolve
    },
    data: {
      authorities: ['95'],
      pageTitle: 'App.tipoSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoSkillUpdateComponent,
    resolve: {
      tipoSkill: TipoSkillResolve
    },
    data: {
      authorities: ['94'],
      pageTitle: 'App.tipoSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoSkillPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoSkillDeletePopupComponent,
    resolve: {
      tipoSkill: TipoSkillResolve
    },
    data: {
      authorities: ['96'],
      pageTitle: 'App.tipoSkill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
