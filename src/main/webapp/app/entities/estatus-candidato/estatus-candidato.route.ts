import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusCandidato } from 'app/shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from './estatus-candidato.service';
import { EstatusCandidatoComponent } from './estatus-candidato.component';
import { EstatusCandidatoDetailComponent } from './estatus-candidato-detail.component';
import { EstatusCandidatoUpdateComponent } from './estatus-candidato-update.component';
import { EstatusCandidatoDeletePopupComponent } from './estatus-candidato-delete-dialog.component';
import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';

@Injectable({ providedIn: 'root' })
export class EstatusCandidatoResolve implements Resolve<IEstatusCandidato> {
  constructor(private service: EstatusCandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusCandidato> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusCandidato>) => response.ok),
        map((estatusCandidato: HttpResponse<EstatusCandidato>) => estatusCandidato.body)
      );
    }
    return of(new EstatusCandidato());
  }
}

export const estatusCandidatoRoute: Routes = [
  {
    path: '',
    component: EstatusCandidatoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusCandidatoDetailComponent,
    resolve: {
      estatusCandidato: EstatusCandidatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusCandidatoUpdateComponent,
    resolve: {
      estatusCandidato: EstatusCandidatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusCandidatoUpdateComponent,
    resolve: {
      estatusCandidato: EstatusCandidatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusCandidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusCandidatoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusCandidatoDeletePopupComponent,
    resolve: {
      estatusCandidato: EstatusCandidatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusCandidato.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
