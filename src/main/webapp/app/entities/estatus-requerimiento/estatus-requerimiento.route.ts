import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from './estatus-requerimiento.service';
import { EstatusRequerimientoComponent } from './estatus-requerimiento.component';
import { EstatusRequerimientoDetailComponent } from './estatus-requerimiento-detail.component';
import { EstatusRequerimientoUpdateComponent } from './estatus-requerimiento-update.component';
import { EstatusRequerimientoDeletePopupComponent } from './estatus-requerimiento-delete-dialog.component';
import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';

@Injectable({ providedIn: 'root' })
export class EstatusRequerimientoResolve implements Resolve<IEstatusRequerimiento> {
  constructor(private service: EstatusRequerimientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusRequerimiento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusRequerimiento>) => response.ok),
        map((estatusRequerimiento: HttpResponse<EstatusRequerimiento>) => estatusRequerimiento.body)
      );
    }
    return of(new EstatusRequerimiento());
  }
}

export const estatusRequerimientoRoute: Routes = [
  {
    path: '',
    component: EstatusRequerimientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusRequerimientoDetailComponent,
    resolve: {
      estatusRequerimiento: EstatusRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusRequerimientoUpdateComponent,
    resolve: {
      estatusRequerimiento: EstatusRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusRequerimientoUpdateComponent,
    resolve: {
      estatusRequerimiento: EstatusRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusRequerimientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusRequerimientoDeletePopupComponent,
    resolve: {
      estatusRequerimiento: EstatusRequerimientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusRequerimiento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
