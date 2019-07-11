import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InstitucionAcademica } from 'app/shared/model/institucion-academica.model';
import { InstitucionAcademicaService } from './institucion-academica.service';
import { InstitucionAcademicaComponent } from './institucion-academica.component';
import { InstitucionAcademicaDetailComponent } from './institucion-academica-detail.component';
import { InstitucionAcademicaUpdateComponent } from './institucion-academica-update.component';
import { InstitucionAcademicaDeletePopupComponent } from './institucion-academica-delete-dialog.component';
import { IInstitucionAcademica } from 'app/shared/model/institucion-academica.model';

@Injectable({ providedIn: 'root' })
export class InstitucionAcademicaResolve implements Resolve<IInstitucionAcademica> {
  constructor(private service: InstitucionAcademicaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInstitucionAcademica> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InstitucionAcademica>) => response.ok),
        map((institucionAcademica: HttpResponse<InstitucionAcademica>) => institucionAcademica.body)
      );
    }
    return of(new InstitucionAcademica());
  }
}

export const institucionAcademicaRoute: Routes = [
  {
    path: '',
    component: InstitucionAcademicaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['41'],
      defaultSort: 'id,asc',
      pageTitle: 'App.institucionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstitucionAcademicaDetailComponent,
    resolve: {
      institucionAcademica: InstitucionAcademicaResolve
    },
    data: {
      authorities: ['41'],
      pageTitle: 'App.institucionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstitucionAcademicaUpdateComponent,
    resolve: {
      institucionAcademica: InstitucionAcademicaResolve
    },
    data: {
      authorities: ['43'],
      pageTitle: 'App.institucionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstitucionAcademicaUpdateComponent,
    resolve: {
      institucionAcademica: InstitucionAcademicaResolve
    },
    data: {
      authorities: ['42'],
      pageTitle: 'App.institucionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const institucionAcademicaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InstitucionAcademicaDeletePopupComponent,
    resolve: {
      institucionAcademica: InstitucionAcademicaResolve
    },
    data: {
      authorities: ['44'],
      pageTitle: 'App.institucionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
