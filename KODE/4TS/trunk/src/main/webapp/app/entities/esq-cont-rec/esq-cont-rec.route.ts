import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from './esq-cont-rec.service';
import { EsqContRecComponent } from './esq-cont-rec.component';
import { EsqContRecDetailComponent } from './esq-cont-rec-detail.component';
import { EsqContRecUpdateComponent } from './esq-cont-rec-update.component';
import { EsqContRecDeletePopupComponent } from './esq-cont-rec-delete-dialog.component';
import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';

@Injectable({ providedIn: 'root' })
export class EsqContRecResolve implements Resolve<IEsqContRec> {
  constructor(private service: EsqContRecService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEsqContRec> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EsqContRec>) => response.ok),
        map((esqContRec: HttpResponse<EsqContRec>) => esqContRec.body)
      );
    }
    return of(new EsqContRec());
  }
}

export const esqContRecRoute: Routes = [
  {
    path: '',
    component: EsqContRecComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['77'],
      defaultSort: 'id,asc',
      pageTitle: 'App.esqContRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EsqContRecDetailComponent,
    resolve: {
      esqContRec: EsqContRecResolve
    },
    data: {
      authorities: ['77'],
      pageTitle: 'App.esqContRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EsqContRecUpdateComponent,
    resolve: {
      esqContRec: EsqContRecResolve
    },
    data: {
      authorities: ['79'],
      pageTitle: 'App.esqContRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EsqContRecUpdateComponent,
    resolve: {
      esqContRec: EsqContRecResolve
    },
    data: {
      authorities: ['78'],
      pageTitle: 'App.esqContRec.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const esqContRecPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EsqContRecDeletePopupComponent,
    resolve: {
      esqContRec: EsqContRecResolve
    },
    data: {
      authorities: ['80'],
      pageTitle: 'App.esqContRec.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
