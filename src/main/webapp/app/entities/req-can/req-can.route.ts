import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReqCan } from 'app/shared/model/req-can.model';
import { ReqCanService } from './req-can.service';
import { ReqCanComponent } from './req-can.component';
import { ReqCanDetailComponent } from './req-can-detail.component';
import { ReqCanUpdateComponent } from './req-can-update.component';
import { ReqCanDeletePopupComponent } from './req-can-delete-dialog.component';
import { IReqCan } from 'app/shared/model/req-can.model';

@Injectable({ providedIn: 'root' })
export class ReqCanResolve implements Resolve<IReqCan> {
  constructor(private service: ReqCanService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReqCan> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReqCan>) => response.ok),
        map((reqCan: HttpResponse<ReqCan>) => reqCan.body)
      );
    }
    return of(new ReqCan());
  }
}

export const reqCanRoute: Routes = [
  {
    path: '',
    component: ReqCanComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.reqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReqCanDetailComponent,
    resolve: {
      reqCan: ReqCanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.reqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReqCanUpdateComponent,
    resolve: {
      reqCan: ReqCanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.reqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReqCanUpdateComponent,
    resolve: {
      reqCan: ReqCanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.reqCan.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reqCanPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReqCanDeletePopupComponent,
    resolve: {
      reqCan: ReqCanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.reqCan.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
