import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';
import { SkillRequerimientoService } from './skill-requerimiento.service';
import { SkillRequerimientoComponent } from './skill-requerimiento.component';
import { SkillRequerimientoDetailComponent } from './skill-requerimiento-detail.component';
import { SkillRequerimientoUpdateComponent } from './skill-requerimiento-update.component';
import { SkillRequerimientoDeletePopupComponent } from './skill-requerimiento-delete-dialog.component';
import { ISkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

@Injectable({ providedIn: 'root' })
export class SkillRequerimientoResolve implements Resolve<ISkillRequerimiento> {
  constructor(private service: SkillRequerimientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISkillRequerimiento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SkillRequerimiento>) => response.ok),
        map((skillRequerimiento: HttpResponse<SkillRequerimiento>) => skillRequerimiento.body)
      );
    }
    return of(new SkillRequerimiento());
  }
}

export const skillRequerimientoRoute: Routes = [
  {
    path: '',
    component: SkillRequerimientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.skillRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SkillRequerimientoDetailComponent,
    resolve: {
      skillRequerimiento: SkillRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SkillRequerimientoUpdateComponent,
    resolve: {
      skillRequerimiento: SkillRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SkillRequerimientoUpdateComponent,
    resolve: {
      skillRequerimiento: SkillRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const skillRequerimientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SkillRequerimientoDeletePopupComponent,
    resolve: {
      skillRequerimiento: SkillRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.skillRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
