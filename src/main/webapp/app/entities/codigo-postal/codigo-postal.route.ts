import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from './codigo-postal.service';
import { CodigoPostalComponent } from './codigo-postal.component';
import { CodigoPostalDetailComponent } from './codigo-postal-detail.component';
import { CodigoPostalUpdateComponent } from './codigo-postal-update.component';
import { CodigoPostalDeletePopupComponent } from './codigo-postal-delete-dialog.component';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';

@Injectable({ providedIn: 'root' })
export class CodigoPostalResolve implements Resolve<ICodigoPostal> {
  constructor(private service: CodigoPostalService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICodigoPostal> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CodigoPostal>) => response.ok),
        map((codigoPostal: HttpResponse<CodigoPostal>) => codigoPostal.body)
      );
    }
    return of(new CodigoPostal());
  }
}

export const codigoPostalRoute: Routes = [
  {
    path: '',
    component: CodigoPostalComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['33'],
      defaultSort: 'id,asc',
      pageTitle: 'App.codigoPostal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CodigoPostalDetailComponent,
    resolve: {
      codigoPostal: CodigoPostalResolve
    },
    data: {
      authorities: ['33'],
      pageTitle: 'App.codigoPostal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CodigoPostalUpdateComponent,
    resolve: {
      codigoPostal: CodigoPostalResolve
    },
    data: {
      authorities: ['35'],
      pageTitle: 'App.codigoPostal.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CodigoPostalUpdateComponent,
    resolve: {
      codigoPostal: CodigoPostalResolve
    },
    data: {
      authorities: ['34'],
      pageTitle: 'App.codigoPostal.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const codigoPostalPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CodigoPostalDeletePopupComponent,
    resolve: {
      codigoPostal: CodigoPostalResolve
    },
    data: {
      authorities: ['36'],
      pageTitle: 'App.codigoPostal.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
