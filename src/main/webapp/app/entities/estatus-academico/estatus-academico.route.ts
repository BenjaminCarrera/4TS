import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusAcademico } from 'app/shared/model/estatus-academico.model';
import { EstatusAcademicoService } from './estatus-academico.service';
import { EstatusAcademicoComponent } from './estatus-academico.component';
import { EstatusAcademicoDetailComponent } from './estatus-academico-detail.component';
import { EstatusAcademicoUpdateComponent } from './estatus-academico-update.component';
import { EstatusAcademicoDeletePopupComponent } from './estatus-academico-delete-dialog.component';
import { IEstatusAcademico } from 'app/shared/model/estatus-academico.model';

@Injectable({ providedIn: 'root' })
export class EstatusAcademicoResolve implements Resolve<IEstatusAcademico> {
  constructor(private service: EstatusAcademicoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusAcademico> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusAcademico>) => response.ok),
        map((estatusAcademico: HttpResponse<EstatusAcademico>) => estatusAcademico.body)
      );
    }
    return of(new EstatusAcademico());
  }
}

export const estatusAcademicoRoute: Routes = [
  {
    path: '',
    component: EstatusAcademicoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['4'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusAcademico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusAcademicoDetailComponent,
    resolve: {
      estatusAcademico: EstatusAcademicoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusAcademico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusAcademicoUpdateComponent,
    resolve: {
      estatusAcademico: EstatusAcademicoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusAcademico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusAcademicoUpdateComponent,
    resolve: {
      estatusAcademico: EstatusAcademicoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusAcademico.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusAcademicoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusAcademicoDeletePopupComponent,
    resolve: {
      estatusAcademico: EstatusAcademicoResolve
    },
    data: {
      authorities: ['4'],
      pageTitle: 'App.estatusAcademico.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
