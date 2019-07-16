import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoIngreso } from 'app/shared/model/tipo-ingreso.model';
import { TipoIngresoService } from './tipo-ingreso.service';
import { TipoIngresoComponent } from './tipo-ingreso.component';
import { TipoIngresoDetailComponent } from './tipo-ingreso-detail.component';
import { TipoIngresoUpdateComponent } from './tipo-ingreso-update.component';
import { TipoIngresoDeletePopupComponent } from './tipo-ingreso-delete-dialog.component';
import { ITipoIngreso } from 'app/shared/model/tipo-ingreso.model';

@Injectable({ providedIn: 'root' })
export class TipoIngresoResolve implements Resolve<ITipoIngreso> {
  constructor(private service: TipoIngresoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoIngreso> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoIngreso>) => response.ok),
        map((tipoIngreso: HttpResponse<TipoIngreso>) => tipoIngreso.body)
      );
    }
    return of(new TipoIngreso());
  }
}

export const tipoIngresoRoute: Routes = [
  {
    path: '',
    component: TipoIngresoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.tipoIngreso.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoIngresoDetailComponent,
    resolve: {
      tipoIngreso: TipoIngresoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.tipoIngreso.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoIngresoUpdateComponent,
    resolve: {
      tipoIngreso: TipoIngresoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.tipoIngreso.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoIngresoUpdateComponent,
    resolve: {
      tipoIngreso: TipoIngresoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.tipoIngreso.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoIngresoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoIngresoDeletePopupComponent,
    resolve: {
      tipoIngreso: TipoIngresoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.tipoIngreso.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
