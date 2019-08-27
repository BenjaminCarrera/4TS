import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICandidato } from 'app/shared/model/candidato.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CandidatoService } from './candidato.service';
import { EstatusCandidato, IEstatusCandidato } from '../../shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from '../estatus-candidato/estatus-candidato.service';
// import { EsquemaContratacionKode } from '../../shared/model/esquema-contratacion-kode.model';
import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';
import { EsquemaContratacionKodeService } from '../esquema-contratacion-kode/esquema-contratacion-kode.service';
import { IUser } from '../../core/user/user.model';
import { UserService } from '../../core/user/user.service';
import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';
import { EstatusLaboralService } from '../estatus-laboral';
import { FormGroup, FormBuilder } from '@angular/forms';
import { esquemaContratacionKodePopupRoute } from '../esquema-contratacion-kode/esquema-contratacion-kode.route';
import { IPerfil } from '../../shared/model/perfil.model';
import { PerfilService } from '../perfil/perfil.service';
import { INivelPerfil } from '../../shared/model/nivel-perfil.model';
import { NivelPerfilService } from '../nivel-perfil/nivel-perfil.service';
import { moneyFormat } from 'app/shared/util/money-format';

@Component({
  selector: 'jhi-candidato',
  templateUrl: './candidato.component.html',
  styleUrls: [
    'agreg-cand.component.scss'
  ]
})
export class CandidatoComponent implements OnInit, OnDestroy {
  currentAccount: any;
  candidatoes: ICandidato[];
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
  criteriaTemp: any;
  criteria: any[];
  estatusCandidatos: IEstatusCandidato[];
  estatusLaborales: IEstatusLaboral[];
  esquemaContratacionKodes: IEsquemaContratacionKode[];
  usuariosAsignado: IUser[];
  perfiles: IPerfil[];
  nivelPerfiles: INivelPerfil[];
  formatNumber: (arg0: string) => String = moneyFormat;

  editForm = this.fb.group({
    estatusCandId: [],
    nombre: [],
    apellidoPaterno: [],
    apellidoMaterno: [],
    reclutadorId: [],
    estatusLabId: [],
    esquemaContratacionId: [],
    perfilId: [],
    nivelPerfilId: []
  });

  constructor(
    protected estatusCandidatoService: EstatusCandidatoService,
    protected perfilService: PerfilService,
    protected nivelPerfilService: NivelPerfilService,
    protected userService: UserService,
    protected esquemaContratacionKodeService: EsquemaContratacionKodeService,
    protected estatusLaboralService: EstatusLaboralService,
    protected candidatoService: CandidatoService,
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
    this.candidatoService
      .query({
        criteria: this.criteria,
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ICandidato[]>) => this.paginateCandidatoes(res.body, res.headers),
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
    this.router.navigate(['/candidato'], {
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
    this.criteria = [];
    this.criteriaTemp = {};
    this.router.navigate([
      '/candidato',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.estatusCandidatoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEstatusCandidato[]>) => this.paginateEstatusCandidatoes(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.estatusLaboralService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEstatusLaboral[]>) => this.paginateEstatusLaborales(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.esquemaContratacionKodeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEsquemaContratacionKode[]>) => this.paginateEsquemaContratacionKodes(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.userService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IUser[]>) => this.paginateUser(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.perfilService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IPerfil[]>) => this.paginatePerfils(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

    this.nivelPerfilService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<INivelPerfil[]>) => this.paginateNivelPerfils(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCandidatoes();
    console.log(this.usuariosAsignado);
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackEstatusCandById(index: number, item: IEstatusCandidato) {
    return item.id;
  }

  trackId(index: number, item: ICandidato) {
    return item.id;
  }

  registerChangeInCandidatoes() {
    this.eventSubscriber = this.eventManager.subscribe('candidatoListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  updateFilters() {
    this.page = 0;
    this.criteria = [];
    this.criteriaTemp = {};
    console.log(this.editForm.get(['estatusCandId']).value);
    if (this.editForm.get(['estatusCandId']).value !== null) {
      this.criteriaTemp = { key: 'estatusCandidatoId.equals', value: this.editForm.get(['estatusCandId']).value };
      console.log(this.criteriaTemp);
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['estatusLabId']).value !== null) {
      this.criteriaTemp = { key: 'estatusLaboralId.equals', value: this.editForm.get(['estatusLabId']).value };
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['esquemaContratacionId']).value !== null) {
      this.criteriaTemp = { key: 'esquemaContratacionKodeId.equals', value: this.editForm.get(['esquemaContratacionId']).value };
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['perfilId']).value !== null) {
      this.criteriaTemp = { key: 'perfilId.equals', value: this.editForm.get(['perfilId']).value };
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['nivelPerfilId']).value !== null) {
      this.criteriaTemp = { key: 'nivelPerfilId.equals', value: this.editForm.get(['nivelPerfilId']).value };
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['reclutadorId']).value !== null) {
      this.criteriaTemp = { key: 'usuarioAsignadoId.equals', value: this.editForm.get(['reclutadorId']).value };
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['nombre']).value !== null) {
      this.criteriaTemp = { key: 'nombre.equals', value: this.editForm.get(['nombre']).value };
      console.log(this.criteriaTemp);
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['apellidoPaterno']).value !== null) {
      this.criteriaTemp = { key: 'apellidoPaterno.equals', value: this.editForm.get(['apellidoPaterno']).value };
      this.criteria.push(this.criteriaTemp);
    }
    if (this.editForm.get(['apellidoMaterno']).value !== null) {
      this.criteriaTemp = { key: 'apellidoMaterno.equals', value: this.editForm.get(['apellidoMaterno']).value };
      this.criteria.push(this.criteriaTemp);
    }
    this.router.navigate([
      '/candidato',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  protected paginateUser(data: IUser[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.usuariosAsignado = data;
    console.log(this.usuariosAsignado);
  }

  protected paginateCandidatoes(data: ICandidato[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.candidatoes = data;
    console.log('------8');
    console.log(this.candidatoes);
  }

  protected paginateEstatusCandidatoes(data: IEstatusCandidato[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.estatusCandidatos = data;
  }

  protected paginateEstatusLaborales(data: IEstatusLaboral[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.estatusLaborales = data;
  }

  protected paginateEsquemaContratacionKodes(data: IEsquemaContratacionKode[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.esquemaContratacionKodes = data;
  }

  protected paginatePerfils(data: IPerfil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.perfiles = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateNivelPerfils(data: INivelPerfil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.nivelPerfiles = data;
  }
  buscarIniciales(id) {
    let res = '';
    if (this.usuariosAsignado) {
      this.usuariosAsignado.forEach(element => {
        if (element.id === id) {
          res = element.iniciales;
        }
      });
    }
    return res;
  }
}
