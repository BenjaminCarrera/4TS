import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstCanInactivo } from 'app/shared/model/est-can-inactivo.model';
import { EstCanInactivoService } from './est-can-inactivo.service';
import { EstCanInactivoComponent } from './est-can-inactivo.component';
import { EstCanInactivoDetailComponent } from './est-can-inactivo-detail.component';
import { EstCanInactivoUpdateComponent } from './est-can-inactivo-update.component';
import { EstCanInactivoDeletePopupComponent } from './est-can-inactivo-delete-dialog.component';
import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';

@Injectable({ providedIn: 'root' })
export class EstCanInactivoResolve implements Resolve<IEstCanInactivo> {
  constructor(private service: EstCanInactivoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstCanInactivo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstCanInactivo>) => response.ok),
        map((estCanInactivo: HttpResponse<EstCanInactivo>) => estCanInactivo.body)
      );
    }
    return of(new EstCanInactivo());
  }
}

export const estCanInactivoRoute: Routes = [
  {
    path: '',
    component: EstCanInactivoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estCanInactivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstCanInactivoDetailComponent,
    resolve: {
      estCanInactivo: EstCanInactivoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estCanInactivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstCanInactivoUpdateComponent,
    resolve: {
      estCanInactivo: EstCanInactivoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estCanInactivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstCanInactivoUpdateComponent,
    resolve: {
      estCanInactivo: EstCanInactivoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estCanInactivo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estCanInactivoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstCanInactivoDeletePopupComponent,
    resolve: {
      estCanInactivo: EstCanInactivoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estCanInactivo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
