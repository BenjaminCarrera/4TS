import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PrioridadReq } from 'app/shared/model/prioridad-req.model';
import { PrioridadReqService } from './prioridad-req.service';
import { PrioridadReqComponent } from './prioridad-req.component';
import { PrioridadReqDetailComponent } from './prioridad-req-detail.component';
import { PrioridadReqUpdateComponent } from './prioridad-req-update.component';
import { PrioridadReqDeletePopupComponent } from './prioridad-req-delete-dialog.component';
import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';

@Injectable({ providedIn: 'root' })
export class PrioridadReqResolve implements Resolve<IPrioridadReq> {
  constructor(private service: PrioridadReqService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPrioridadReq> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PrioridadReq>) => response.ok),
        map((prioridadReq: HttpResponse<PrioridadReq>) => prioridadReq.body)
      );
    }
    return of(new PrioridadReq());
  }
}

export const prioridadReqRoute: Routes = [
  {
    path: '',
    component: PrioridadReqComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.prioridadReq.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrioridadReqDetailComponent,
    resolve: {
      prioridadReq: PrioridadReqResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.prioridadReq.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrioridadReqUpdateComponent,
    resolve: {
      prioridadReq: PrioridadReqResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.prioridadReq.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrioridadReqUpdateComponent,
    resolve: {
      prioridadReq: PrioridadReqResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.prioridadReq.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const prioridadReqPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PrioridadReqDeletePopupComponent,
    resolve: {
      prioridadReq: PrioridadReqResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.prioridadReq.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
