import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Candidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from './candidato.service';
import { CandidatoComponent } from './candidato.component';
import { CandidatoDetailComponent } from './candidato-detail.component';
import { CandidatoUpdateComponent } from './candidato-update.component';
import { CandidatoDeletePopupComponent } from './candidato-delete-dialog.component';
import { ICandidato } from 'app/shared/model/candidato.model';

@Injectable({ providedIn: 'root' })
export class CandidatoResolve implements Resolve<ICandidato> {
  constructor(private service: CandidatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICandidato> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Candidato>) => response.ok),
        map((candidato: HttpResponse<Candidato>) => candidato.body)
      );
    }
    return of(new Candidato());
  }
}

export const candidatoRoute: Routes = [
  {
    path: '',
    component: CandidatoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['3'],
      defaultSort: 'id,asc',
      pageTitle: 'App.candidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CandidatoDetailComponent,
    resolve: {
      candidato: CandidatoResolve
    },
    data: {
      authorities: ['3'],
      pageTitle: 'App.candidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CandidatoUpdateComponent,
    resolve: {
      candidato: CandidatoResolve
    },
    data: {
      authorities: ['3'],
      pageTitle: 'App.candidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CandidatoUpdateComponent,
    resolve: {
      candidato: CandidatoResolve
    },
    data: {
      authorities: ['3'],
      pageTitle: 'App.candidato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const candidatoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CandidatoDeletePopupComponent,
    resolve: {
      candidato: CandidatoResolve
    },
    data: {
      authorities: ['3'],
      pageTitle: 'App.candidato.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
