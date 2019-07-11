import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipoTarea } from 'app/shared/model/tipo-tarea.model';
import { TipoTareaService } from './tipo-tarea.service';
import { TipoTareaComponent } from './tipo-tarea.component';
import { TipoTareaDetailComponent } from './tipo-tarea-detail.component';
import { TipoTareaUpdateComponent } from './tipo-tarea-update.component';
import { TipoTareaDeletePopupComponent } from './tipo-tarea-delete-dialog.component';
import { ITipoTarea } from 'app/shared/model/tipo-tarea.model';

@Injectable({ providedIn: 'root' })
export class TipoTareaResolve implements Resolve<ITipoTarea> {
  constructor(private service: TipoTareaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipoTarea> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipoTarea>) => response.ok),
        map((tipoTarea: HttpResponse<TipoTarea>) => tipoTarea.body)
      );
    }
    return of(new TipoTarea());
  }
}

export const tipoTareaRoute: Routes = [
  {
    path: '',
    component: TipoTareaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['125'],
      defaultSort: 'id,asc',
      pageTitle: 'App.tipoTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoTareaDetailComponent,
    resolve: {
      tipoTarea: TipoTareaResolve
    },
    data: {
      authorities: ['125'],
      pageTitle: 'App.tipoTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoTareaUpdateComponent,
    resolve: {
      tipoTarea: TipoTareaResolve
    },
    data: {
      authorities: ['127'],
      pageTitle: 'App.tipoTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoTareaUpdateComponent,
    resolve: {
      tipoTarea: TipoTareaResolve
    },
    data: {
      authorities: ['126'],
      pageTitle: 'App.tipoTarea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipoTareaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipoTareaDeletePopupComponent,
    resolve: {
      tipoTarea: TipoTareaResolve
    },
    data: {
      authorities: ['128'],
      pageTitle: 'App.tipoTarea.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
