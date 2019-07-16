import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstReqCerrado } from 'app/shared/model/est-req-cerrado.model';
import { EstReqCerradoService } from './est-req-cerrado.service';
import { EstReqCerradoComponent } from './est-req-cerrado.component';
import { EstReqCerradoDetailComponent } from './est-req-cerrado-detail.component';
import { EstReqCerradoUpdateComponent } from './est-req-cerrado-update.component';
import { EstReqCerradoDeletePopupComponent } from './est-req-cerrado-delete-dialog.component';
import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';

@Injectable({ providedIn: 'root' })
export class EstReqCerradoResolve implements Resolve<IEstReqCerrado> {
  constructor(private service: EstReqCerradoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstReqCerrado> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstReqCerrado>) => response.ok),
        map((estReqCerrado: HttpResponse<EstReqCerrado>) => estReqCerrado.body)
      );
    }
    return of(new EstReqCerrado());
  }
}

export const estReqCerradoRoute: Routes = [
  {
    path: '',
    component: EstReqCerradoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estReqCerrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstReqCerradoDetailComponent,
    resolve: {
      estReqCerrado: EstReqCerradoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estReqCerrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstReqCerradoUpdateComponent,
    resolve: {
      estReqCerrado: EstReqCerradoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estReqCerrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstReqCerradoUpdateComponent,
    resolve: {
      estReqCerrado: EstReqCerradoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estReqCerrado.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estReqCerradoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstReqCerradoDeletePopupComponent,
    resolve: {
      estReqCerrado: EstReqCerradoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estReqCerrado.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
