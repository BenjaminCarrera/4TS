import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FormacionAcademica } from 'app/shared/model/formacion-academica.model';
import { FormacionAcademicaService } from './formacion-academica.service';
import { FormacionAcademicaComponent } from './formacion-academica.component';
import { FormacionAcademicaDetailComponent } from './formacion-academica-detail.component';
import { FormacionAcademicaUpdateComponent } from './formacion-academica-update.component';
import { FormacionAcademicaDeletePopupComponent } from './formacion-academica-delete-dialog.component';
import { IFormacionAcademica } from 'app/shared/model/formacion-academica.model';

@Injectable({ providedIn: 'root' })
export class FormacionAcademicaResolve implements Resolve<IFormacionAcademica> {
  constructor(private service: FormacionAcademicaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFormacionAcademica> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<FormacionAcademica>) => response.ok),
        map((formacionAcademica: HttpResponse<FormacionAcademica>) => formacionAcademica.body)
      );
    }
    return of(new FormacionAcademica());
  }
}

export const formacionAcademicaRoute: Routes = [
  {
    path: '',
    component: FormacionAcademicaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['17'],
      defaultSort: 'id,asc',
      pageTitle: 'App.formacionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FormacionAcademicaDetailComponent,
    resolve: {
      formacionAcademica: FormacionAcademicaResolve
    },
    data: {
      authorities: ['17'],
      pageTitle: 'App.formacionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FormacionAcademicaUpdateComponent,
    resolve: {
      formacionAcademica: FormacionAcademicaResolve
    },
    data: {
      authorities: ['19'],
      pageTitle: 'App.formacionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FormacionAcademicaUpdateComponent,
    resolve: {
      formacionAcademica: FormacionAcademicaResolve
    },
    data: {
      authorities: ['18'],
      pageTitle: 'App.formacionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const formacionAcademicaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FormacionAcademicaDeletePopupComponent,
    resolve: {
      formacionAcademica: FormacionAcademicaResolve
    },
    data: {
      authorities: ['20'],
      pageTitle: 'App.formacionAcademica.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
