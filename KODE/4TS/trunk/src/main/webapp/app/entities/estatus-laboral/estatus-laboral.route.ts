import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusLaboral } from 'app/shared/model/estatus-laboral.model';
import { EstatusLaboralService } from './estatus-laboral.service';
import { EstatusLaboralComponent } from './estatus-laboral.component';
import { EstatusLaboralDetailComponent } from './estatus-laboral-detail.component';
import { EstatusLaboralUpdateComponent } from './estatus-laboral-update.component';
import { EstatusLaboralDeletePopupComponent } from './estatus-laboral-delete-dialog.component';
import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';

@Injectable({ providedIn: 'root' })
export class EstatusLaboralResolve implements Resolve<IEstatusLaboral> {
  constructor(private service: EstatusLaboralService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusLaboral> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusLaboral>) => response.ok),
        map((estatusLaboral: HttpResponse<EstatusLaboral>) => estatusLaboral.body)
      );
    }
    return of(new EstatusLaboral());
  }
}

export const estatusLaboralRoute: Routes = [
  {
    path: '',
    component: EstatusLaboralComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['81'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusLaboral.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusLaboralDetailComponent,
    resolve: {
      estatusLaboral: EstatusLaboralResolve
    },
    data: {
      authorities: ['81'],
      pageTitle: 'App.estatusLaboral.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusLaboralUpdateComponent,
    resolve: {
      estatusLaboral: EstatusLaboralResolve
    },
    data: {
      authorities: ['83'],
      pageTitle: 'App.estatusLaboral.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusLaboralUpdateComponent,
    resolve: {
      estatusLaboral: EstatusLaboralResolve
    },
    data: {
      authorities: ['82'],
      pageTitle: 'App.estatusLaboral.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusLaboralPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusLaboralDeletePopupComponent,
    resolve: {
      estatusLaboral: EstatusLaboralResolve
    },
    data: {
      authorities: ['84'],
      pageTitle: 'App.estatusLaboral.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
