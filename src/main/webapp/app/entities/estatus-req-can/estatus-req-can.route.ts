import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusReqCan } from 'app/shared/model/estatus-req-can.model';
import { EstatusReqCanService } from './estatus-req-can.service';
import { EstatusReqCanComponent } from './estatus-req-can.component';
import { EstatusReqCanDetailComponent } from './estatus-req-can-detail.component';
import { EstatusReqCanUpdateComponent } from './estatus-req-can-update.component';
import { EstatusReqCanDeletePopupComponent } from './estatus-req-can-delete-dialog.component';
import { IEstatusReqCan } from 'app/shared/model/estatus-req-can.model';

@Injectable({ providedIn: 'root' })
export class EstatusReqCanResolve implements Resolve<IEstatusReqCan> {
  constructor(private service: EstatusReqCanService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusReqCan> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusReqCan>) => response.ok),
        map((estatusReqCan: HttpResponse<EstatusReqCan>) => estatusReqCan.body)
      );
    }
    return of(new EstatusReqCan());
  }
}

export const estatusReqCanRoute: Routes = [
  {
    path: '',
    component: EstatusReqCanComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusReqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusReqCanDetailComponent,
    resolve: {
      estatusReqCan: EstatusReqCanResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusReqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusReqCanUpdateComponent,
    resolve: {
      estatusReqCan: EstatusReqCanResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusReqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusReqCanUpdateComponent,
    resolve: {
      estatusReqCan: EstatusReqCanResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusReqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusReqCanPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusReqCanDeletePopupComponent,
    resolve: {
      estatusReqCan: EstatusReqCanResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusReqCan.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
