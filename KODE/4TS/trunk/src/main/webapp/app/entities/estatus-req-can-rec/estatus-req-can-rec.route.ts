import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';
import { EstatusReqCanRecService } from './estatus-req-can-rec.service';
import { EstatusReqCanRecComponent } from './estatus-req-can-rec.component';
import { EstatusReqCanRecDetailComponent } from './estatus-req-can-rec-detail.component';
import { EstatusReqCanRecUpdateComponent } from './estatus-req-can-rec-update.component';
import { EstatusReqCanRecDeletePopupComponent } from './estatus-req-can-rec-delete-dialog.component';
import { IEstatusReqCanRec } from 'app/shared/model/estatus-req-can-rec.model';

@Injectable({ providedIn: 'root' })
export class EstatusReqCanRecResolve implements Resolve<IEstatusReqCanRec> {
  constructor(private service: EstatusReqCanRecService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusReqCanRec> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusReqCanRec>) => response.ok),
        map((estatusReqCanRec: HttpResponse<EstatusReqCanRec>) => estatusReqCanRec.body)
      );
    }
    return of(new EstatusReqCanRec());
  }
}

export const estatusReqCanRecRoute: Routes = [
  {
    path: '',
    component: EstatusReqCanRecComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['113'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusReqCanRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusReqCanRecDetailComponent,
    resolve: {
      estatusReqCanRec: EstatusReqCanRecResolve
    },
    data: {
      authorities: ['115'],
      pageTitle: 'App.estatusReqCanRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusReqCanRecUpdateComponent,
    resolve: {
      estatusReqCanRec: EstatusReqCanRecResolve
    },
    data: {
      authorities: ['114'],
      pageTitle: 'App.estatusReqCanRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusReqCanRecUpdateComponent,
    resolve: {
      estatusReqCanRec: EstatusReqCanRecResolve
    },
    data: {
      authorities: ['116'],
      pageTitle: 'App.estatusReqCanRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusReqCanRecPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusReqCanRecDeletePopupComponent,
    resolve: {
      estatusReqCanRec: EstatusReqCanRecResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusReqCanRec.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
