import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Requerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';
import { RequerimientoComponent } from './requerimiento.component';
import { RequerimientoDetailComponent } from './requerimiento-detail.component';
import { RequerimientoUpdateComponent } from './requerimiento-update.component';
import { RequerimientoDeletePopupComponent } from './requerimiento-delete-dialog.component';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';

@Injectable({ providedIn: 'root' })
export class RequerimientoResolve implements Resolve<IRequerimiento> {
  constructor(private service: RequerimientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRequerimiento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Requerimiento>) => response.ok),
        map((requerimiento: HttpResponse<Requerimiento>) => requerimiento.body)
      );
    }
    return of(new Requerimiento());
  }
}

export const requerimientoRoute: Routes = [
  {
    path: '',
    component: RequerimientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RequerimientoDetailComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RequerimientoUpdateComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RequerimientoUpdateComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const requerimientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RequerimientoDeletePopupComponent,
    resolve: {
      requerimiento: RequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.requerimiento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
