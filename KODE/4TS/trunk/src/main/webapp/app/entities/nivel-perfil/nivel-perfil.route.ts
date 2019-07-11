import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from './nivel-perfil.service';
import { NivelPerfilComponent } from './nivel-perfil.component';
import { NivelPerfilDetailComponent } from './nivel-perfil-detail.component';
import { NivelPerfilUpdateComponent } from './nivel-perfil-update.component';
import { NivelPerfilDeletePopupComponent } from './nivel-perfil-delete-dialog.component';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';

@Injectable({ providedIn: 'root' })
export class NivelPerfilResolve implements Resolve<INivelPerfil> {
  constructor(private service: NivelPerfilService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INivelPerfil> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NivelPerfil>) => response.ok),
        map((nivelPerfil: HttpResponse<NivelPerfil>) => nivelPerfil.body)
      );
    }
    return of(new NivelPerfil());
  }
}

export const nivelPerfilRoute: Routes = [
  {
    path: '',
    component: NivelPerfilComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['57'],
      defaultSort: 'id,asc',
      pageTitle: 'App.nivelPerfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NivelPerfilDetailComponent,
    resolve: {
      nivelPerfil: NivelPerfilResolve
    },
    data: {
      authorities: ['57'],
      pageTitle: 'App.nivelPerfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NivelPerfilUpdateComponent,
    resolve: {
      nivelPerfil: NivelPerfilResolve
    },
    data: {
      authorities: ['59'],
      pageTitle: 'App.nivelPerfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NivelPerfilUpdateComponent,
    resolve: {
      nivelPerfil: NivelPerfilResolve
    },
    data: {
      authorities: ['58'],
      pageTitle: 'App.nivelPerfil.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nivelPerfilPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NivelPerfilDeletePopupComponent,
    resolve: {
      nivelPerfil: NivelPerfilResolve
    },
    data: {
      authorities: ['60'],
      pageTitle: 'App.nivelPerfil.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
