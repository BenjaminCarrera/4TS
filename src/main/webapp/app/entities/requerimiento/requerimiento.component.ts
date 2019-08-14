import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { AccountService, IUser, UserService } from 'app/core';

import { RequerimientoService } from './requerimiento.service';
import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from '../estatus-requerimiento';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from '../cuenta';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from '../perfil';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from '../nivel-perfil';
import { ITipoSolicitud } from 'app/shared/model/tipo-solicitud.model';
import { TipoSolicitudService } from '../tipo-solicitud';
import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';
import { PrioridadReqService } from '../prioridad-req';
import { FormBuilder, Validators } from '@angular/forms';
import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
  selector: 'jhi-requerimiento',
  templateUrl: './requerimiento.component.html',
  styleUrls: ['../../con-req/con-req.component.scss']
})
export class RequerimientoComponent implements OnInit, OnDestroy {
  currentAccount: any;
  requerimientos: IRequerimiento[];
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

  users: IUser[];
  estatusrequerimientos: IEstatusRequerimiento[];
  cuentas: ICuenta[];
  perfils: IPerfil[];
  nivelperfils: INivelPerfil[];
  tiposolicituds: ITipoSolicitud[];
  prioridadreqs: IPrioridadReq[];
  criteria: any[];
  criteriaTemp: any;

  editForm = this.fb.group({
    idReq: ['', [Validators.max(9999), Validators.min(0)]],
    usuarioAsignadoId: [],
    estatusRequerimientoId: [],
    cuentaId: [],
    perfilId: [],
    nivelPerfilId: [],
    tipoSolicitudId: [],
    prioridadId: []
  });

  constructor(
    protected userService: UserService,
    protected estatusRequerimientoService: EstatusRequerimientoService,
    protected cuentaService: CuentaService,
    protected perfilService: PerfilService,
    protected nivelPerfilService: NivelPerfilService,
    protected tipoSolicitudService: TipoSolicitudService,
    protected prioridadReqService: PrioridadReqService,
    protected requerimientoService: RequerimientoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    private fb: FormBuilder
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
    console.log(this.criteria);
    this.requerimientoService
      .query({
        criteria: this.criteria,
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IRequerimiento[]>) => this.paginateRequerimientos(res.body, res.headers),
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
    this.router.navigate(['/requerimiento'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  public clear() {
    this.page = 0;
    this.criteria = [];
    this.criteriaTemp = {};
    this.router.navigate([
      '/requerimiento',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }
  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
  updateFilters() {
    this.page = 0;
    this.criteria = [];
    this.criteriaTemp = {};
    const id = this.editForm.get(['idReq']).value;
    if (id > 9999) {
    } else {
      if (this.editForm.get(['idReq']).value != null) {
        this.criteriaTemp = { key: 'id.equals', value: this.editForm.get(['idReq']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['usuarioAsignadoId']).value != null) {
        this.criteriaTemp = { key: 'usuarioAsignadoId.equals', value: this.editForm.get(['usuarioAsignadoId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['estatusRequerimientoId']).value != null) {
        this.criteriaTemp = { key: 'estatusRequerimientoId.equals', value: this.editForm.get(['estatusRequerimientoId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['cuentaId']).value != null) {
        this.criteriaTemp = { key: 'cuentaId.equals', value: this.editForm.get(['cuentaId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['perfilId']).value != null) {
        this.criteriaTemp = { key: 'perfilId.equals', value: this.editForm.get(['perfilId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['nivelPerfilId']).value != null) {
        this.criteriaTemp = { key: 'nivelPerfilId.equals', value: this.editForm.get(['nivelPerfilId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['tipoSolicitudId']).value != null) {
        this.criteriaTemp = { key: 'tipoSolicitudId.equals', value: this.editForm.get(['tipoSolicitudId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      if (this.editForm.get(['prioridadId']).value != null) {
        this.criteriaTemp = { key: 'prioridadId.equals', value: this.editForm.get(['prioridadId']).value };
        this.criteria.push(this.criteriaTemp);
      }
      this.router.navigate([
        '/requerimiento',
        {
          page: this.page,
          sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }
      ]);
      this.loadAll();
    }
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRequerimientos();

    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusRequerimientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusRequerimiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusRequerimiento[]>) => response.body)
      )
      .subscribe(
        (res: IEstatusRequerimiento[]) => (this.estatusrequerimientos = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.cuentaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICuenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICuenta[]>) => response.body)
      )
      .subscribe((res: ICuenta[]) => (this.cuentas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.nivelPerfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INivelPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<INivelPerfil[]>) => response.body)
      )
      .subscribe((res: INivelPerfil[]) => (this.nivelperfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoSolicitudService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoSolicitud[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoSolicitud[]>) => response.body)
      )
      .subscribe((res: ITipoSolicitud[]) => (this.tiposolicituds = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.prioridadReqService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPrioridadReq[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPrioridadReq[]>) => response.body)
      )
      .subscribe((res: IPrioridadReq[]) => (this.prioridadreqs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRequerimiento) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackEstatusRequerimientoById(index: number, item: IEstatusRequerimiento) {
    return item.id;
  }

  trackCuentaById(index: number, item: ICuenta) {
    return item.id;
  }

  trackPerfilById(index: number, item: IPerfil) {
    return item.id;
  }

  trackNivelPerfilById(index: number, item: INivelPerfil) {
    return item.id;
  }

  trackTipoSolicitudById(index: number, item: ITipoSolicitud) {
    return item.id;
  }

  trackPrioridadReqById(index: number, item: IPrioridadReq) {
    return item.id;
  }

  registerChangeInRequerimientos() {
      this.eventSubscriber = this.eventManager.subscribe('requerimientoListModification', response => this.loadAll());
  }

  protected paginateRequerimientos(data: IRequerimiento[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.requerimientos = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
