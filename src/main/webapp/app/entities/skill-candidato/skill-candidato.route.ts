import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SkillCandidato } from 'app/shared/model/skill-candidato.model';
import { SkillCandidatoService } from './skill-candidato.service';
import { SkillCandidatoComponent } from './skill-candidato.component';
import { SkillCandidatoDetailComponent } from './skill-candidato-detail.component';
import { SkillCandidatoUpdateComponent } from './skill-candidato-update.component';
import { SkillCandidatoDeletePopupComponent } from './skill-candidato-delete-dialog.component';
import { ISkillCandidato } from 'app/shared/model/skill-candidato.model';

@Injectable({ providedIn: 'root' })
export class SkillCandidatoResolve implements Resolve<ISkillCandidato> {
  constructor(private service: SkillCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkillCandidato> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SkillCandidato>) => response.ok),
        map((skillCandidato: HttpResponse<SkillCandidato>) => skillCandidato.body)
      );
    }
    return of(new SkillCandidato());
  }
}

export const skillCandidatoRoute: Routes = [
  {
    path: '',
    component: SkillCandidatoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.skillCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillCandidatoDetailComponent,
    resolve: {
      skillCandidato: SkillCandidatoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.skillCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillCandidatoUpdateComponent,
    resolve: {
      skillCandidato: SkillCandidatoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.skillCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillCandidatoUpdateComponent,
    resolve: {
      skillCandidato: SkillCandidatoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.skillCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillCandidatoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillCandidatoDeletePopupComponent,
    resolve: {
      skillCandidato: SkillCandidatoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.skillCandidato.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
