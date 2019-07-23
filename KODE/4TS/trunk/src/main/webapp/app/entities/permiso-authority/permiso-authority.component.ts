import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription, Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IPermisoAuthority } from 'app/shared/model/permiso-authority.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PermisoAuthorityService } from './permiso-authority.service';
import { PermisoAuthority } from '../../shared/model/permiso-authority.model';
import { IPermiso } from '../../shared/model/permiso.model';
import { IArrePermisoAuthority, ArrePermisoAuthority } from '../../shared/model/permiso-authority-array.model';

@Component({
  selector: 'jhi-permiso-authority',
  templateUrl: './permiso-authority.component.html'
})
export class PermisoAuthorityComponent implements OnInit, OnDestroy {

  isSaving: boolean;

  currentAccount: any;
  permisoAuthorities: IPermisoAuthority[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  // temp: IArrePermisoAuthority;
  newArre: any[] = [];

  constructor(
    protected permisoAuthorityService: PermisoAuthorityService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.permisoAuthorityService
      .query({
        page: this.page - 1,
        size: 40,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPermisoAuthority[]>) => this.paginatePermisoAuthorities(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/permiso-authority'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/permiso-authority',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPermisoAuthorities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPermisoAuthority) {
    return item.id;
  }

  registerChangeInPermisoAuthorities() {
    this.eventSubscriber = this.eventManager.subscribe('permisoAuthorityListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginatePermisoAuthorities(data: IPermisoAuthority[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.permisoAuthorities = data;

    this.temp = new ArrePermisoAuthority();
    let contadorPermisos: number;
    contadorPermisos = 0;
      this.permisoAuthorities.forEach(e => {
        if (e.permisoId === 1) {
          this.temp.admin = e.id;
          this.temp.actAdmin = e.activated;
        } else if (e.permisoId === 2) {
          this.temp.requirements = e.id;
          this.temp.actRequirements = e.activated;
        } else if (e.permisoId === 3) {
          this.temp.candidates = e.id;
          this.temp.actCandidates = e.activated;
        } else if (e.permisoId === 4) {
          this.temp.entity = e.id;
          this.temp.actEntity = e.activated;
          this.temp.authority = e.authority;
          this.newArre.push(this.temp);
          contadorPermisos = contadorPermisos + 1;
          this.temp = new ArrePermisoAuthority();
        }
        });
        this.totalItems = contadorPermisos;

  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  save() {

  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPermisoAuthority>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  previousState() {
    window.history.back();
  }

  protected onSaveError() {
    this.isSaving = false;
  }

}
