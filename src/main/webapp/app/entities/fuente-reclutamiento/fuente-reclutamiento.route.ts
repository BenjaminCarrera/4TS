import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';
import { FuenteReclutamientoService } from './fuente-reclutamiento.service';
import { FuenteReclutamientoComponent } from './fuente-reclutamiento.component';
import { FuenteReclutamientoDetailComponent } from './fuente-reclutamiento-detail.component';
import { FuenteReclutamientoUpdateComponent } from './fuente-reclutamiento-update.component';
import { FuenteReclutamientoDeletePopupComponent } from './fuente-reclutamiento-delete-dialog.component';
import { IFuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';

@Injectable({ providedIn: 'root' })
export class FuenteReclutamientoResolve implements Resolve<IFuenteReclutamiento> {
  constructor(private service: FuenteReclutamientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFuenteReclutamiento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FuenteReclutamiento>) => response.ok),
        map((fuenteReclutamiento: HttpResponse<FuenteReclutamiento>) => fuenteReclutamiento.body)
      );
    }
    return of(new FuenteReclutamiento());
  }
}

export const fuenteReclutamientoRoute: Routes = [
  {
    path: '',
    component: FuenteReclutamientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.fuenteReclutamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FuenteReclutamientoDetailComponent,
    resolve: {
      fuenteReclutamiento: FuenteReclutamientoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.fuenteReclutamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FuenteReclutamientoUpdateComponent,
    resolve: {
      fuenteReclutamiento: FuenteReclutamientoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.fuenteReclutamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FuenteReclutamientoUpdateComponent,
    resolve: {
      fuenteReclutamiento: FuenteReclutamientoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.fuenteReclutamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fuenteReclutamientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FuenteReclutamientoDeletePopupComponent,
    resolve: {
      fuenteReclutamiento: FuenteReclutamientoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.fuenteReclutamiento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
