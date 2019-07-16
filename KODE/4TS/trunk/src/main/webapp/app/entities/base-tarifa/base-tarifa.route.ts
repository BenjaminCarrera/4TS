import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BaseTarifa } from 'app/shared/model/base-tarifa.model';
import { BaseTarifaService } from './base-tarifa.service';
import { BaseTarifaComponent } from './base-tarifa.component';
import { BaseTarifaDetailComponent } from './base-tarifa-detail.component';
import { BaseTarifaUpdateComponent } from './base-tarifa-update.component';
import { BaseTarifaDeletePopupComponent } from './base-tarifa-delete-dialog.component';
import { IBaseTarifa } from 'app/shared/model/base-tarifa.model';

@Injectable({ providedIn: 'root' })
export class BaseTarifaResolve implements Resolve<IBaseTarifa> {
  constructor(private service: BaseTarifaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBaseTarifa> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BaseTarifa>) => response.ok),
        map((baseTarifa: HttpResponse<BaseTarifa>) => baseTarifa.body)
      );
    }
    return of(new BaseTarifa());
  }
}

export const baseTarifaRoute: Routes = [
  {
    path: '',
    component: BaseTarifaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.baseTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BaseTarifaDetailComponent,
    resolve: {
      baseTarifa: BaseTarifaResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.baseTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BaseTarifaUpdateComponent,
    resolve: {
      baseTarifa: BaseTarifaResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.baseTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BaseTarifaUpdateComponent,
    resolve: {
      baseTarifa: BaseTarifaResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.baseTarifa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const baseTarifaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BaseTarifaDeletePopupComponent,
    resolve: {
      baseTarifa: BaseTarifaResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.baseTarifa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
