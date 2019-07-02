import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EstatusTarea } from 'app/shared/model/estatus-tarea.model';
import { EstatusTareaService } from './estatus-tarea.service';
import { EstatusTareaComponent } from './estatus-tarea.component';
import { EstatusTareaDetailComponent } from './estatus-tarea-detail.component';
import { EstatusTareaUpdateComponent } from './estatus-tarea-update.component';
import { EstatusTareaDeletePopupComponent } from './estatus-tarea-delete-dialog.component';
import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';

@Injectable({ providedIn: 'root' })
export class EstatusTareaResolve implements Resolve<IEstatusTarea> {
  constructor(private service: EstatusTareaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstatusTarea> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EstatusTarea>) => response.ok),
        map((estatusTarea: HttpResponse<EstatusTarea>) => estatusTarea.body)
      );
    }
    return of(new EstatusTarea());
  }
}

export const estatusTareaRoute: Routes = [
  {
    path: '',
    component: EstatusTareaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'App.estatusTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstatusTareaDetailComponent,
    resolve: {
      estatusTarea: EstatusTareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstatusTareaUpdateComponent,
    resolve: {
      estatusTarea: EstatusTareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstatusTareaUpdateComponent,
    resolve: {
      estatusTarea: EstatusTareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estatusTareaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstatusTareaDeletePopupComponent,
    resolve: {
      estatusTarea: EstatusTareaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'App.estatusTarea.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
