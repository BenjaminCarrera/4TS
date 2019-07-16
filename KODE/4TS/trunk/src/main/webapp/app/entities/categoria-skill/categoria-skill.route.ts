import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CategoriaSkill } from 'app/shared/model/categoria-skill.model';
import { CategoriaSkillService } from './categoria-skill.service';
import { CategoriaSkillComponent } from './categoria-skill.component';
import { CategoriaSkillDetailComponent } from './categoria-skill-detail.component';
import { CategoriaSkillUpdateComponent } from './categoria-skill-update.component';
import { CategoriaSkillDeletePopupComponent } from './categoria-skill-delete-dialog.component';
import { ICategoriaSkill } from 'app/shared/model/categoria-skill.model';

@Injectable({ providedIn: 'root' })
export class CategoriaSkillResolve implements Resolve<ICategoriaSkill> {
  constructor(private service: CategoriaSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICategoriaSkill> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CategoriaSkill>) => response.ok),
        map((categoriaSkill: HttpResponse<CategoriaSkill>) => categoriaSkill.body)
      );
    }
    return of(new CategoriaSkill());
  }
}

export const categoriaSkillRoute: Routes = [
  {
    path: '',
    component: CategoriaSkillComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.categoriaSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoriaSkillDetailComponent,
    resolve: {
      categoriaSkill: CategoriaSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.categoriaSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoriaSkillUpdateComponent,
    resolve: {
      categoriaSkill: CategoriaSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.categoriaSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoriaSkillUpdateComponent,
    resolve: {
      categoriaSkill: CategoriaSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.categoriaSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const categoriaSkillPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CategoriaSkillDeletePopupComponent,
    resolve: {
      categoriaSkill: CategoriaSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.categoriaSkill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
