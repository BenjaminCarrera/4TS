import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PermisoAuthority } from 'app/shared/model/permiso-authority.model';
import { PermisoAuthorityService } from './permiso-authority.service';
import { PermisoAuthorityComponent } from './permiso-authority.component';
import { PermisoAuthorityDetailComponent } from './permiso-authority-detail.component';
import { PermisoAuthorityUpdateComponent } from './permiso-authority-update.component';
import { PermisoAuthorityDeletePopupComponent } from './permiso-authority-delete-dialog.component';
import { IPermisoAuthority } from 'app/shared/model/permiso-authority.model';

@Injectable({ providedIn: 'root' })
export class PermisoAuthorityResolve implements Resolve<IPermisoAuthority> {
  constructor(private service: PermisoAuthorityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPermisoAuthority> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PermisoAuthority>) => response.ok),
        map((permisoAuthority: HttpResponse<PermisoAuthority>) => permisoAuthority.body)
      );
    }
    return of(new PermisoAuthority());
  }
}

export const permisoAuthorityRoute: Routes = [
  {
    path: '',
    component: PermisoAuthorityComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['1'],
      defaultSort: 'id,asc',
      pageTitle: 'App.permisoAuthority.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PermisoAuthorityDetailComponent,
    resolve: {
      permisoAuthority: PermisoAuthorityResolve
    },
    data: {
      authorities: ['1'],
      pageTitle: 'App.permisoAuthority.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PermisoAuthorityUpdateComponent,
    resolve: {
      permisoAuthority: PermisoAuthorityResolve
    },
    data: {
      authorities: ['1'],
      pageTitle: 'App.permisoAuthority.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PermisoAuthorityUpdateComponent,
    resolve: {
      permisoAuthority: PermisoAuthorityResolve
    },
    data: {
      authorities: ['1'],
      pageTitle: 'App.permisoAuthority.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const permisoAuthorityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PermisoAuthorityDeletePopupComponent,
    resolve: {
      permisoAuthority: PermisoAuthorityResolve
    },
    data: {
      authorities: ['1'],
      pageTitle: 'App.permisoAuthority.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
