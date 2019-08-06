import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
import { ITarea } from '../../shared/model/tarea.model';
import { TareaService } from '../tarea/tarea.service';
import { IBitacora } from 'app/shared/model/bitacora.model';
import { BitacoraService } from '../bitacora/bitacora.service';

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
    'requerimiento.component.scss'
  ]
})

export class RequerimientoDetailComponent implements OnInit {

  page: any;
  itemsPerPage: any;

  // skills
  skillsToShow: ISkillRequerimiento;
  skillTS: any [];
  skillReq1: ISkillRequerimiento;
  tipoReq1: any [];
  skillReq2: ISkillRequerimiento;
  tipoReq2: any [];
  skillReq3: ISkillRequerimiento;
  tipoReq3: any [];
  tareas: ITarea [];
  bitacoras: IBitacora[];
  // Enfoque del mapa
  lat: any;
  lng: any;
  zoom = 10;
  requerimiento: IRequerimiento;
  skillRequerimientos: ISkillRequerimiento [];
  // Variables Tarea
  dataSourceTarea: PeriodicElement[];

  // VAriables Bitacora
  DATA_BITACORA: Tarea[] = [
    { Fecha: '26/06/2019', Creador: 'Sistema', Comentario: 'MABE eliminó "C#" y "LinQ" de la lista de "Skills requeridos"' },
    { Fecha: '26/06/2019', Creador: 'MABE', Comentario: 'MABE agregó "Spring MVC" a la lista de "Skills esenciales"' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'El cliente me solicita esperar a que se lleven a cabo las entrevistas antes de enviar más gente.' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'MABE actualizó el campo "Tarifa" de $35 000.00 a $45 000.00' }
  ];
  dataSourceBitacora: Tarea[];
  displayedColumnsBitacora: string[] = ['Fecha', 'Creador', 'Comentario'];
  predicate: string;
  reverse: any;
  links: any;
  parseLinks: any;
  totalItems: number;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected skillRequerimientoService: SkillRequerimientoService,
    protected tareaService: TareaService,
    protected bitacoraService: BitacoraService
    ) {
    this.dataSourceBitacora = this.DATA_BITACORA.slice();
  }

  sortDataBitacora(sort: MatSort) {
    const data = this.DATA_BITACORA.slice();
    if (!sort.active || sort.direction === '') {
      this.dataSourceBitacora = data;
      return;
    }

    this.dataSourceBitacora = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'Fecha': return compare(a.Fecha, b.Fecha, isAsc);
        case 'Creador': return compare(a.Creador, b.Creador, isAsc);
        case 'Comentario': return compare(a.Comentario, b.Comentario, isAsc);
        default: return 0;
      }
    });
  }

  ngOnInit() {

    this.loadAll();

    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.requerimiento = requerimiento;
    });

    this.tareaService
      .query({
        page: this.page - 1,
        size: 9999
      })
      .subscribe(
        (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message, 'tareas')
      );

      this.bitacoraService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage
      })
      .subscribe(
        (res: HttpResponse<IBitacora[]>) => this.paginateBitacoras(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message, 'bitacora-requerimiento')
      );

    // Enfoque del mapa
    this.lat = this.requerimiento.coorLat;
    this.lng = this.requerimiento.coorLong;
    this.zoom = 10;
  }

  loadAll() {
    this.skillRequerimientoService
      .query({
        page: this.page - 1,
        size: 9999
      })
      .subscribe(
        (res: HttpResponse<ISkillRequerimiento[]>) => this.paginateSkillRequerimientos(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message, 'skill')
      );
      this.tareas = [];
  }

  protected onError(message: string, aviso: string): void {
    console.log(message, aviso);
    // throw new Error('');
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

    console.log('tipo 1', this.tipoReq1);
    console.log('tipo 2', this.tipoReq2);
    console.log('tipo 3', this.tipoReq3);
  }

  protected paginateTareas(data: ITarea[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.tareas = data;
    }

    protected paginateBitacoras(data: IBitacora[], headers: HttpHeaders) {
      // this.links = this.parseLinks.parse(headers.get('link'));
      // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
      this.bitacoras = data;
    }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
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

  trackId(index: number, item: ITarea) {
    return item.id;
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
