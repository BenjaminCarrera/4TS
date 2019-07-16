import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';
import { ReferenciasLaboralesService } from './referencias-laborales.service';
import { ReferenciasLaboralesComponent } from './referencias-laborales.component';
import { ReferenciasLaboralesDetailComponent } from './referencias-laborales-detail.component';
import { ReferenciasLaboralesUpdateComponent } from './referencias-laborales-update.component';
import { ReferenciasLaboralesDeletePopupComponent } from './referencias-laborales-delete-dialog.component';
import { IReferenciasLaborales } from 'app/shared/model/referencias-laborales.model';

@Injectable({ providedIn: 'root' })
export class ReferenciasLaboralesResolve implements Resolve<IReferenciasLaborales> {
  constructor(private service: ReferenciasLaboralesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReferenciasLaborales> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReferenciasLaborales>) => response.ok),
        map((referenciasLaborales: HttpResponse<ReferenciasLaborales>) => referenciasLaborales.body)
      );
    }
    return of(new ReferenciasLaborales());
  }
}

export const referenciasLaboralesRoute: Routes = [
  {
    path: '',
    component: ReferenciasLaboralesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.referenciasLaborales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReferenciasLaboralesDetailComponent,
    resolve: {
      referenciasLaborales: ReferenciasLaboralesResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.referenciasLaborales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReferenciasLaboralesUpdateComponent,
    resolve: {
      referenciasLaborales: ReferenciasLaboralesResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.referenciasLaborales.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReferenciasLaboralesUpdateComponent,
    resolve: {
      referenciasLaborales: ReferenciasLaboralesResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.referenciasLaborales.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const referenciasLaboralesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReferenciasLaboralesDeletePopupComponent,
    resolve: {
      referenciasLaborales: ReferenciasLaboralesResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.referenciasLaborales.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
