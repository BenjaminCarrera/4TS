import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from './tipo-periodo.service';
import { TipoPeriodoComponent } from './tipo-periodo.component';
import { TipoPeriodoDetailComponent } from './tipo-periodo-detail.component';
import { TipoPeriodoUpdateComponent } from './tipo-periodo-update.component';
import { TipoPeriodoDeletePopupComponent } from './tipo-periodo-delete-dialog.component';
import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';

@Injectable({ providedIn: 'root' })
export class TipoPeriodoResolve implements Resolve<ITipoPeriodo> {
  constructor(private service: TipoPeriodoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoPeriodo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoPeriodo>) => response.ok),
        map((tipoPeriodo: HttpResponse<TipoPeriodo>) => tipoPeriodo.body)
      );
    }
    return of(new TipoPeriodo());
  }
}

export const tipoPeriodoRoute: Routes = [
  {
    path: '',
    component: TipoPeriodoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.tipoPeriodo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoPeriodoDetailComponent,
    resolve: {
      tipoPeriodo: TipoPeriodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tipoPeriodo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoPeriodoUpdateComponent,
    resolve: {
      tipoPeriodo: TipoPeriodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tipoPeriodo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoPeriodoUpdateComponent,
    resolve: {
      tipoPeriodo: TipoPeriodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tipoPeriodo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoPeriodoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoPeriodoDeletePopupComponent,
    resolve: {
      tipoPeriodo: TipoPeriodoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.tipoPeriodo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
