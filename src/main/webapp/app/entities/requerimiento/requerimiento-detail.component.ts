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

export class RequerimientoDetailComponent implements OnInit {

  page: any;

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
  // Variables Tarea
  DATA_TAREA: PeriodicElement[] = [
    { Id: 1, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 2, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 3, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 4, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 5, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 3, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 6, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 7, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 8, Tarea: 'Abiertas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 9, Tarea: 'Cerradas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 10, Tarea: 'Cerradas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 11, Tarea: 'Cerradas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 12, Tarea: 'Cerradas', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' }
  ];
  dataSourceTarea: PeriodicElement[];
  displayedColumnsTarea: string[] = ['Id', 'Tarea', 'Creador', 'Destinatario', 'FechaAlta', 'Estatus'];

  // VAriables Bitacora
  DATA_BITACORA: Tarea[] = [
    { Fecha: '26/06/2019', Creador: 'Sistema', Comentario: 'MABE eliminó "C#" y "LinQ" de la lista de "Skills requeridos"' },
    { Fecha: '26/06/2019', Creador: 'MABE', Comentario: 'MABE agregó "Spring MVC" a la lista de "Skills esenciales"' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'El cliente me solicita esperar a que se lleven a cabo las entrevistas antes de enviar más gente.' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'MABE actualizó el campo "Tarifa" de $35 000.00 a $45 000.00' }
  ];
  dataSourceBitacora: Tarea[];
  displayedColumnsBitacora: string[] = ['Fecha', 'Creador', 'Comentario'];
  constructor(protected activatedRoute: ActivatedRoute,
    protected skillRequerimientoService: SkillRequerimientoService) {
    this.dataSourceTarea = this.DATA_TAREA.slice();
    this.dataSourceBitacora = this.DATA_BITACORA.slice();
  }

  sortDataTarea(sort: MatSort) {
    const data = this.DATA_TAREA.slice();
    if (!sort.active || sort.direction === '') {
      this.dataSourceTarea = data;
      return;
    }

    this.dataSourceTarea = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'Id': return compare(a.Id, b.Id, isAsc);
        case 'Tarea': return compare(a.Tarea, b.Tarea, isAsc);
        case 'Creador': return compare(a.Creador, b.Creador, isAsc);
        case 'Destinatario': return compare(a.Destinatario, b.Destinatario, isAsc);
        case 'FechaAlta': return compare(a.FechaAlta, b.FechaAlta, isAsc);
        case 'Estatus': return compare(a.Estatus, b.Estatus, isAsc);
        default: return 0;
      }
    });
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
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.requerimiento = requerimiento;
    });
    this.loadSkillReq();
    // Enfoque del mapa
    this.lat = this.requerimiento.coorLat;
    this.lng = this.requerimiento.coorLong;
    this.zoom = 10;
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
