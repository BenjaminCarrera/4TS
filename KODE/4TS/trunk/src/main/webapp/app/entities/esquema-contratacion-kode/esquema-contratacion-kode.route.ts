import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';
import { EsquemaContratacionKodeService } from './esquema-contratacion-kode.service';
import { EsquemaContratacionKodeComponent } from './esquema-contratacion-kode.component';
import { EsquemaContratacionKodeDetailComponent } from './esquema-contratacion-kode-detail.component';
import { EsquemaContratacionKodeUpdateComponent } from './esquema-contratacion-kode-update.component';
import { EsquemaContratacionKodeDeletePopupComponent } from './esquema-contratacion-kode-delete-dialog.component';
import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';

@Injectable({ providedIn: 'root' })
export class EsquemaContratacionKodeResolve implements Resolve<IEsquemaContratacionKode> {
  constructor(private service: EsquemaContratacionKodeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEsquemaContratacionKode> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EsquemaContratacionKode>) => response.ok),
        map((esquemaContratacionKode: HttpResponse<EsquemaContratacionKode>) => esquemaContratacionKode.body)
      );
    }
    return of(new EsquemaContratacionKode());
  }
}

export const esquemaContratacionKodeRoute: Routes = [
  {
    path: '',
    component: EsquemaContratacionKodeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.esquemaContratacionKode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EsquemaContratacionKodeDetailComponent,
    resolve: {
      esquemaContratacionKode: EsquemaContratacionKodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.esquemaContratacionKode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EsquemaContratacionKodeUpdateComponent,
    resolve: {
      esquemaContratacionKode: EsquemaContratacionKodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.esquemaContratacionKode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EsquemaContratacionKodeUpdateComponent,
    resolve: {
      esquemaContratacionKode: EsquemaContratacionKodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.esquemaContratacionKode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const esquemaContratacionKodePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EsquemaContratacionKodeDeletePopupComponent,
    resolve: {
      esquemaContratacionKode: EsquemaContratacionKodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.esquemaContratacionKode.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
