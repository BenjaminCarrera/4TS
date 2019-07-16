import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bitacora } from 'app/shared/model/bitacora.model';
import { BitacoraService } from './bitacora.service';
import { BitacoraComponent } from './bitacora.component';
import { BitacoraDetailComponent } from './bitacora-detail.component';
import { BitacoraUpdateComponent } from './bitacora-update.component';
import { BitacoraDeletePopupComponent } from './bitacora-delete-dialog.component';
import { IBitacora } from 'app/shared/model/bitacora.model';

@Injectable({ providedIn: 'root' })
export class BitacoraResolve implements Resolve<IBitacora> {
  constructor(private service: BitacoraService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBitacora> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Bitacora>) => response.ok),
        map((bitacora: HttpResponse<Bitacora>) => bitacora.body)
      );
    }
    return of(new Bitacora());
  }
}

export const bitacoraRoute: Routes = [
  {
    path: '',
    component: BitacoraComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.bitacora.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BitacoraDetailComponent,
    resolve: {
      bitacora: BitacoraResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.bitacora.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BitacoraUpdateComponent,
    resolve: {
      bitacora: BitacoraResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.bitacora.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BitacoraUpdateComponent,
    resolve: {
      bitacora: BitacoraResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.bitacora.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bitacoraPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BitacoraDeletePopupComponent,
    resolve: {
      bitacora: BitacoraResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.bitacora.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
