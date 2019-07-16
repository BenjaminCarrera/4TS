import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DominioSkill } from 'app/shared/model/dominio-skill.model';
import { DominioSkillService } from './dominio-skill.service';
import { DominioSkillComponent } from './dominio-skill.component';
import { DominioSkillDetailComponent } from './dominio-skill-detail.component';
import { DominioSkillUpdateComponent } from './dominio-skill-update.component';
import { DominioSkillDeletePopupComponent } from './dominio-skill-delete-dialog.component';
import { IDominioSkill } from 'app/shared/model/dominio-skill.model';

@Injectable({ providedIn: 'root' })
export class DominioSkillResolve implements Resolve<IDominioSkill> {
  constructor(private service: DominioSkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDominioSkill> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DominioSkill>) => response.ok),
        map((dominioSkill: HttpResponse<DominioSkill>) => dominioSkill.body)
      );
    }
    return of(new DominioSkill());
  }
}

export const dominioSkillRoute: Routes = [
  {
    path: '',
    component: DominioSkillComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.dominioSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DominioSkillDetailComponent,
    resolve: {
      dominioSkill: DominioSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.dominioSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DominioSkillUpdateComponent,
    resolve: {
      dominioSkill: DominioSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.dominioSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DominioSkillUpdateComponent,
    resolve: {
      dominioSkill: DominioSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.dominioSkill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dominioSkillPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DominioSkillDeletePopupComponent,
    resolve: {
      dominioSkill: DominioSkillResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.dominioSkill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
