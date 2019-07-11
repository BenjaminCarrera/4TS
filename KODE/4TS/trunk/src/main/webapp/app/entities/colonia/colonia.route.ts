import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Colonia } from 'app/shared/model/colonia.model';
import { ColoniaService } from './colonia.service';
import { ColoniaComponent } from './colonia.component';
import { ColoniaDetailComponent } from './colonia-detail.component';
import { ColoniaUpdateComponent } from './colonia-update.component';
import { ColoniaDeletePopupComponent } from './colonia-delete-dialog.component';
import { IColonia } from 'app/shared/model/colonia.model';

@Injectable({ providedIn: 'root' })
export class ColoniaResolve implements Resolve<IColonia> {
  constructor(private service: ColoniaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IColonia> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Colonia>) => response.ok),
        map((colonia: HttpResponse<Colonia>) => colonia.body)
      );
    }
    return of(new Colonia());
  }
}

export const coloniaRoute: Routes = [
  {
    path: '',
    component: ColoniaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['21'],
      defaultSort: 'id,asc',
      pageTitle: 'App.colonia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ColoniaDetailComponent,
    resolve: {
      colonia: ColoniaResolve
    },
    data: {
      authorities: ['21'],
      pageTitle: 'App.colonia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ColoniaUpdateComponent,
    resolve: {
      colonia: ColoniaResolve
    },
    data: {
      authorities: ['23'],
      pageTitle: 'App.colonia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ColoniaUpdateComponent,
    resolve: {
      colonia: ColoniaResolve
    },
    data: {
      authorities: ['22'],
      pageTitle: 'App.colonia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const coloniaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ColoniaDeletePopupComponent,
    resolve: {
      colonia: ColoniaResolve
    },
    data: {
      authorities: ['24'],
      pageTitle: 'App.colonia.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
