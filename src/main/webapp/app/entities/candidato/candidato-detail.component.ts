import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ICandidato } from 'app/shared/model/candidato.model';
// Inicio datatable
import { MatSort } from '@angular/material/sort';
// Tareas
import { ITarea } from 'app/shared/model/tarea.model';
import { AccountService } from 'app/core';
import { IEstatusTarea } from 'app/shared/model/estatus-tarea.model';
import { TareaService } from '../tarea/tarea.service';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { EstatusTareaService } from '../estatus-tarea';
import { ALL_ITEMS } from 'app/shared';
import { filter, map } from 'rxjs/operators';
import { IBitacora } from 'app/shared/model/bitacora.model';
import { BitacoraService } from '../bitacora/bitacora.service';
import { SkillCandidato, ISkillCandidato } from '../../shared/model/skill-candidato.model';
import { SkillCandidatoService } from '../skill-candidato/skill-candidato.service';
import { CANDIDATO_IMAGE, CANDIDATO_DEFAULT_IMAGE } from 'app/shared/constants/candidato.constants';
import { formatNumber } from '@angular/common';

export interface Tarea {
  Fecha: string;
  Creador: string;
  Comentario: string;
}
export interface Skills {
  Skills: string;
  Dominio: string;
  Calificacion: string;
  Eliminar: string;
}

@Component({
  selector: 'jhi-candidato-detail',
  templateUrl: './candidato-detail.component.html',
  styleUrls: [
    '../../res-concand/res-concand.component.scss'
  ]
})
export class CandidatoDetailComponent implements OnInit, OnDestroy {
  // Imagen
  selectedFile: File;
  imagen: string | ArrayBuffer;
  // Variables Bitacora
  DATA_BITACORA: Tarea[] = [
    { Fecha: '26/06/2019', Creador: 'Sistema', Comentario: 'MABE eliminó "C#" y "LinQ" de la lista de "Skills requeridos"' },
    { Fecha: '26/06/2019', Creador: 'MABE', Comentario: 'MABE agregó "Spring MVC" a la lista de "Skills esenciales"' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'El cliente me solicita esperar a que se lleven a cabo las entrevistas antes de enviar más gente.' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'MABE actualizó el campo "Tarifa" de $35 000.00 a $45 000.00' }
  ];
  dataSourceBitacora: Tarea[];
  displayedColumnsBitacora: string[] = ['Fecha', 'Creador', 'Comentario'];

  // Variables Skills
  DATA_SKILLS: Skills[] = [
    { Skills: 'Hibernate', Dominio: 'Intermedio', Calificacion: '10.0', Eliminar: 'Eliminar' },
    { Skills: 'Angular', Dominio: 'Avanzado', Calificacion: '9.0', Eliminar: 'Eliminar' },
    { Skills: 'Java', Dominio: 'Principiante', Calificacion: '7.0', Eliminar: 'Eliminar' },
  ];
  dataSourceSkills: Skills[];
  displayedColumnsSkills: string[] = ['Skills', 'Dominio', 'Calificacion'];

  // Tarea
  estatusTareas: IEstatusTarea[];
  currentAccount: any;
  tareas: ITarea[];
  bitacoras: IBitacora[];
  skillsCandidato: ISkillCandidato[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  routeDataBitacora: any;
  routeDataSkillCand: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  linksBitacora: any;
  totalItemsBitacora: any;
  itemsPerPageBitacora: any;
  linksSkillCand: any;
  totalItemsSkillCand: any;
  itemsPerPageSkillCand: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  pageBitacora: any;
  predicateBitacora: any;
  reverseBitacora: any;
  previousPageBitacora: any;
  pageSkillCand: any;
  predicateSkillCand: any;
  previousPageSkillCand: any;
  reverseSkillCand: any;
  age: number;

  // Mostrar u ocultar cosas
  mostrarDetalleCandidatoInactivo: boolean;
  mostrarDisponibilidadEntrevistaCandidato: boolean;
  candidato: ICandidato;
  mostrarAsignacionCandidato: boolean;
  // Enfoque del mapa
  lat = 19.391213;
  lng = -99.165761;
  zoom = 16;
  message: string;
  primerosDigitos: string;
  segundosDigitos: string;
  tercerosDigitos: string;
  constructor(
    // Tarea
    protected tareaService: TareaService,
    protected bitacoraService: BitacoraService,
    protected skillCandidatoService: SkillCandidatoService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected estatusTareaService: EstatusTareaService
  ) {
    // Tarea
    this.itemsPerPage = 100;
    this.itemsPerPageBitacora = 100;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = 1;
      this.previousPage = 1;
      this.reverse = true;
      this.predicate = 'id';
    });

    this.routeDataBitacora = this.activatedRoute.data.subscribe(data => {
      this.pageBitacora = 1;
      this.previousPageBitacora = 1;
      this.reverseBitacora = true;
      this.predicateBitacora = 'id';
    });

    this.routeDataSkillCand = this.activatedRoute.data.subscribe(data => {
      this.pageSkillCand = 1;
      this.previousPageSkillCand = 1;
      this.reverseSkillCand = true;
      this.predicateSkillCand = 'id';
    });

  }

  // Tarea
  loadAll() {
    this.tareaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        'candidatoId.equals': this.candidato.id
      })
      .subscribe(
        (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadAllBitacora() {
    this.bitacoraService
      .query({
        page: this.pageBitacora - 1,
        size: this.itemsPerPageBitacora,
        sort: this.sortBitacora(),
        'candidatoId.equals': this.candidato.id
      })
      .subscribe(
        (res: HttpResponse<IBitacora[]>) => this.paginateBitacoras(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadAllSkillCand() {
    this.skillCandidatoService
      .query({
        page: this.pageSkillCand - 1,
        size: this.itemsPerPageSkillCand,
        sort: this.sortSkillCand(),
        'idCandidatoId.equals': this.candidato.id
      })
      .subscribe(
        (res: HttpResponse<ISkillCandidato[]>) => this.paginateSkillCandidatoes(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  loadPageBitacora(pageBitacora: number) {
    if (pageBitacora !== this.previousPageBitacora) {
      this.previousPageBitacora = pageBitacora;
      this.transitionBitacora();
    }
  }

  loadPageSkillCand(pageSkillCand: number) {
    if (pageSkillCand !== this.previousPageSkillCand) {
      this.previousPageSkillCand = pageSkillCand;
      this.transitionSkillCand();
    }
  }

  transition() {
    this.router.navigate(['/candidato/' + this.candidato.id + '/view'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  transitionBitacora() {
    this.router.navigate(['/candidato/' + this.candidato.id + '/view'], {
      queryParams: {
        page: this.pageBitacora,
        size: this.itemsPerPageBitacora,
        sort: this.predicateBitacora + ',' + (this.reverseBitacora ? 'asc' : 'desc')
      }
    });
    this.loadAllBitacora();
  }

  transitionSkillCand() {
    this.router.navigate(['/candidato/' + this.candidato.id + '/view'], {
      queryParams: {
        page: this.pageSkillCand,
        size: this.itemsPerPageSkillCand,
        sort: this.predicateSkillCand + ',' + (this.reverseSkillCand ? 'asc' : 'desc')
      }
    });
    this.loadAllSkillCand();
  }

  public clear() {
    this.page = 0;
    this.router.navigate([
      '/candidato/' + this.candidato.id + '/view',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
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

  sortBitacora() {
    const result = [this.predicateBitacora + ',' + (this.reverseBitacora ? 'asc' : 'desc')];
    if (this.predicateBitacora !== 'id') {
      result.push('id');
    }
    return result;
  }

  sortSkillCand() {
    const result = [this.predicateSkillCand + ',' + (this.reverseSkillCand ? 'asc' : 'desc')];
    if (this.predicateSkillCand !== 'id') {
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

  protected paginateSkillCandidatoes(data: ISkillCandidato[], headers: HttpHeaders) {
    this.linksSkillCand = this.parseLinks.parse(headers.get('link'));
    this.totalItemsSkillCand = parseInt(headers.get('X-Total-Count'), 10);
    this.skillsCandidato = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
  setEstatusTarea(res: IEstatusTarea[]) {
    this.estatusTareas = res;
  }
  // fin
  ngOnInit() {
    this.age = null;
    this.activatedRoute.data.subscribe(({ candidato }) => {
      if (candidato.fechaNacimiento) {
        const timeDiff = Math.abs(Date.now() - new Date(candidato.fechaNacimiento.toDate()).getTime());
        this.age = Math.floor(timeDiff / (1000 * 3600 * 24) / 365.25);
      }
      if (candidato.foto) {
        candidato.foto = CANDIDATO_IMAGE + candidato.foto;
      } else {
        candidato.foto = CANDIDATO_IMAGE + CANDIDATO_DEFAULT_IMAGE;
      }
      this.candidato = candidato;
    });
    this.verificarStatus();
    this.verificarDisponibilidadEntrevista();
    this.verificarDisponibilidaAsignacion();
    // Tareas
    this.loadAll();
    this.loadAllBitacora();
    this.loadAllSkillCand();
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
    this.imagen = this.candidato.foto;
     this.candidato.salarioNeto = formatNumber(this.candidato.salarioNeto) ;
     this.candidato.costoTotal = formatNumber(this.candidato.costoTotal) ;
     this.candidato.antecedenteSalarioNeto = formatNumber(this.candidato.antecedenteSalarioNeto) ;
  }
  previousState() {
    window.history.back();
  }
  filtrarTareas(estatus: string) {
    if (estatus === 'Abierta') {
      console.log('Abierta');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'candidatoId.equals': this.candidato.id,
          'estatusTareaId.equals': 1
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (estatus === 'Atendida') {
      console.log('Atendida');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'candidatoId.equals': this.candidato.id,
          'estatusTareaId.equals': 2
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (estatus === 'Cerrada') {
      console.log('Cerrada');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'candidatoId.equals': this.candidato.id,
          'estatusTareaId.equals': 3
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (estatus === 'Todas') {
      console.log('Todas');
      this.tareaService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'candidatoId.equals': this.candidato.id
        })
        .subscribe(
          (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
  }
  verificarStatus() {
    if (this.candidato.estatusCandidatoEstatus === 'Activo') {
      console.log('Esta activo');
      this.mostrarDetalleCandidatoInactivo = false;
    } else {
      console.log('Esta inactivo');
      this.mostrarDetalleCandidatoInactivo = true;
    }
  }
  verificarDisponibilidadEntrevista() {
    console.log(this.candidato.disponibilidadEntrevistaFecha);
    if (this.candidato.disponibilidadEntrevistaFecha) {
      console.log('Tiene fecha');
      this.mostrarDisponibilidadEntrevistaCandidato = false;
    } else {
      console.log('No tiene fecha');
      this.mostrarDisponibilidadEntrevistaCandidato = true;
    }
  }
  verificarDisponibilidaAsignacion() {
    console.log(this.candidato.disponibilidadAsignacionFecha);
    if (this.candidato.disponibilidadAsignacionFecha) {
      console.log('Tiene fecha de asignacion');
      this.mostrarAsignacionCandidato = false;
    } else {
      console.log('No tiene fecha de asignacion');
      this.mostrarAsignacionCandidato = true;
    }
  }
  // Imagen
  onFileChanged(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = e => this.imagen = reader.result;
    reader.readAsDataURL(file);
  }

  onUpload() {
    // upload code goes here
  }
}
function formatNumber(num) {
  return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
