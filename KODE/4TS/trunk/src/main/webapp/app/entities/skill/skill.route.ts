import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Skill } from 'app/shared/model/skill.model';
import { SkillService } from './skill.service';
import { SkillComponent } from './skill.component';
import { SkillDetailComponent } from './skill-detail.component';
import { SkillUpdateComponent } from './skill-update.component';
import { SkillDeletePopupComponent } from './skill-delete-dialog.component';
import { ISkill } from 'app/shared/model/skill.model';

@Injectable({ providedIn: 'root' })
export class SkillResolve implements Resolve<ISkill> {
  constructor(private service: SkillService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkill> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Skill>) => response.ok),
        map((skill: HttpResponse<Skill>) => skill.body)
      );
    }
    return of(new Skill());
  }
}

export const skillRoute: Routes = [
  {
    path: '',
    component: SkillComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['89'],
      defaultSort: 'id,asc',
      pageTitle: 'App.skill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillDetailComponent,
    resolve: {
      skill: SkillResolve
    },
    data: {
      authorities: ['89'],
      pageTitle: 'App.skill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillUpdateComponent,
    resolve: {
      skill: SkillResolve
    },
    data: {
      authorities: ['91'],
      pageTitle: 'App.skill.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillUpdateComponent,
    resolve: {
      skill: SkillResolve
    },
    data: {
      authorities: ['90'],
      pageTitle: 'App.skill.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillDeletePopupComponent,
    resolve: {
      skill: SkillResolve
    },
    data: {
      authorities: ['92'],
      pageTitle: 'App.skill.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
