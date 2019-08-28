import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatPaginator} from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { MatSort } from '@angular/material';
import { TareaApi } from '../../servicios/tareas.service';
import { ITipoSkill } from 'app/shared/model/tipo-skill.model';
import { ISkillRequerimiento, SkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';
import { SkillRequerimientoService } from '../skill-requerimiento/skill-requerimiento.service';
import { skillRequerimientoPopupRoute } from '../skill-requerimiento/skill-requerimiento.route';
import { HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
// Tareas
import { ITarea } from 'app/shared/model/tarea.model';
import { AccountService } from 'app/core';
import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';
import { TareaService } from '../tarea/tarea.service';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { EstatusTareaService } from '../estatus-tarea';
import { ALL_ITEMS } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { IBitacora } from '../../shared/model/bitacora.model';
import { BitacoraService } from '../bitacora/bitacora.service';
import { moneyFormat } from 'app/shared/util/money-format';
import moment = require('moment');

export interface PeriodicElement {
  Id: number;
  Tarea: string;
  Creador: string;
  Destinatario: string;
  FechaAlta: string;
  Estatus: string;
}

export interface Tarea {
  Fecha: string;
  Creador: string;
  Comentario: string;
}

@Component({
  selector: 'jhi-requerimiento-detail',
  templateUrl: './requerimiento-detail.component.html',
  styleUrls: [
    '../../res-conreq/res-conreq.component.scss'
  ]
})

export class RequerimientoDetailComponent implements OnInit, OnDestroy {
  // Tarea
  estatusTareas: IEstatusTarea[];
  currentAccount: any;
  tareas: ITarea[];
  bitacoras: IBitacora[];
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
  routeDataBitacora: any;
  linksBitacora: any;
  totalItemsBitacora: any;
  itemsPerPageBitacora: any;
  pageBitacora: any;
  predicateBitacora: any;
  previousPageBitacora: any;
  reverseBitacora: any;
  formatNumber: any = moneyFormat;

  // Mostrar u ocultar cosas
  // skills
  skillsToShow: ISkillRequerimiento;
  skillTS: any [];
  skillReq1: ISkillRequerimiento;
  tipoReq1: any [];
  skillReq2: ISkillRequerimiento;
  tipoReq2: any [];
  skillReq3: ISkillRequerimiento;
  tipoReq3: any [];
  // Enfoque del mapa
  lat: any;
  lng: any;
  zoom = 10;
  requerimiento: IRequerimiento;
  skillRequerimientos: ISkillRequerimiento [];

  constructor(
    protected skillRequerimientoService: SkillRequerimientoService,
    protected tareaService: TareaService,
    protected bitacoraService: BitacoraService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected estatusTareaService: EstatusTareaService) {
    // Tarea
    this.itemsPerPage = 100;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = 1;
      this.previousPage = 1;
      this.reverse = true;
      this.predicate = 'id';
    });
    // Bitácora
    this.itemsPerPageBitacora = 100;
    this.routeDataBitacora = this.activatedRoute.data.subscribe(data => {
      this.pageBitacora = 1;
      this.previousPageBitacora = 1;
      this.reverseBitacora = true;
      this.predicateBitacora = 'id';
    });
  }

  // Tarea
  loadAll() {
    this.tareaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'requerimientoId.equals': this.requerimiento.id
      })
      .subscribe(
        (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
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
      this.router.navigate(['/requerimiento/' + this.requerimiento.id + '/view'], {
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
      this.router.navigate([
        '/requerimiento/' + this.requerimiento.id + '/view',
        {
          page: this.page,
          sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }
      ]);
      this.loadAll();
    }

  // Bitacoras
  loadBitacora() {
    this.bitacoraService
      .query({
        page: this.pageBitacora - 1,
        size: this.itemsPerPageBitacora,
        sort: this.sortBitacora(),
        'requerimientoId.equals': this.requerimiento.id
      })
      .subscribe(
        (res: HttpResponse<IBitacora[]>) => this.paginateBitacoras(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  sortBitacora() {
    const result = [this.predicateBitacora + ',' + (this.reverseBitacora ? 'asc' : 'desc')];
    if (this.predicateBitacora !== 'id') {
      result.push('id');
    }
    return result;
  }

  loadPageBitacora(pageBitacora: number) {
    if (pageBitacora !== this.previousPageBitacora) {
      this.previousPageBitacora = pageBitacora;
      this.transitionBitacora();
    }
  }

  transitionBitacora() {
    this.router.navigate(['/requerimiento/' + this.requerimiento.id + '/view'], {
      queryParams: {
        page: this.pageBitacora,
        size: this.itemsPerPageBitacora,
        sort: this.predicateBitacora + ',' + (this.reverseBitacora ? 'asc' : 'desc')
      }
    });
    this.loadBitacora();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITarea) {
    return item.id;
  }

  registerChangeInTareas() {
    this.eventSubscriber = this.eventManager.subscribe('tareaListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTareas(data: ITarea[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.tareas = data;
  }

  protected paginateBitacoras(data: IBitacora[], headers: HttpHeaders) {
    this.linksBitacora = this.parseLinks.parse(headers.get('link'));
    this.totalItemsBitacora = parseInt(headers.get('X-Total-Count'), 10);
    this.bitacoras = data;
  }

  setEstatusTarea(res: IEstatusTarea[]) {
    this.estatusTareas = res;
  }
  // fin

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.requerimiento = requerimiento;
    });
    this.loadSkillReq();
    // Enfoque del mapa
    this.lat = this.requerimiento.coorLat;
    this.lng = this.requerimiento.coorLong;
    this.zoom = 10;
    // Bitácoras
    this.loadBitacora();
    // Tareas
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTareas();
    this.estatusTareaService
      .query({
        size: ALL_ITEMS
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusTarea[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusTarea[]>) => response.body)
      )
      .subscribe(
        (res: IEstatusTarea[]) => (this.setEstatusTarea(res)),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      console.log(this.estatusTareas);
  }

  loadSkillReq() {
    this.skillRequerimientoService
      .query({
        page: this.page - 1,
        size: 9999
      })
      .subscribe(
        (res: HttpResponse<ISkillRequerimiento[]>) => this.paginateSkillRequerimientos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  onError(message: string): void {
    throw new Error('');
  }

  filtrarTareas(estatus: string) {
    if (estatus === 'Abierta') {
      console.log( 'Abierta');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'requerimientoId.equals': this.requerimiento.id,
          'estatusTareaId.equals': 1
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (estatus === 'Atendida') {
      console.log( 'Atendida');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'requerimientoId.equals': this.requerimiento.id,
          'estatusTareaId.equals': 2
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (estatus === 'Cerrada') {
      console.log( 'Cerrada');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'requerimientoId.equals': this.requerimiento.id,
          'estatusTareaId.equals': 3
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (estatus === 'Todas') {
      console.log( 'Todas');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'requerimientoId.equals': this.requerimiento.id
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
  }
  protected paginateSkillRequerimientos(data: ISkillRequerimiento[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.skillRequerimientos = data;
    this.skillsToShow = {};
    this.skillTS = [];
    this.tipoReq1 = [];
    this.tipoReq2 = [];
    this.tipoReq3 = [];

    this.skillReq1 = new SkillRequerimiento();
    this.skillReq2 = new SkillRequerimiento();
    this.skillReq3 = new SkillRequerimiento();
      this.skillRequerimientos.forEach( req => {
        if (this.requerimiento.id === req.idRequerimientoId) {
         this.skillsToShow = req;
          this.skillTS.push(this.skillsToShow);
        }
      });
      console.log(this.skillTS);
    this.skillTS.forEach( skill => {
      if (skill.tipoSkillId === 1) {
        this.skillReq1 = skill;
        this.tipoReq1.push(this.skillReq1);
      } else if (skill.tipoSkillId === 2) {
        this.skillReq2 = skill;
        this.tipoReq2.push(this.skillReq2);
      } else if (skill.tipoSkillId === 3) {
        this.skillReq3 = skill;
        this.tipoReq3.push(this.skillReq3);
      }
    });

    console.log(this.tipoReq1);
    console.log(this.tipoReq2);
    console.log(this.tipoReq3);
  }
  // Get employees list
  // cargarTareas() {
  //   return this.restApi.getTareas().subscribe((data: ) => {
  //     this.Tareas = data;
  //     this.dataSourceTarea2 = data;
  //   });
  // }
  previousState() {
    window.history.back();
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
