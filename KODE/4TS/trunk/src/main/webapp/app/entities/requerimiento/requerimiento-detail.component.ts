import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { MatTableDataSource } from '@angular/material';
import { IRequerimiento } from 'app/shared/model/requerimiento.model';
import { MatSort } from '@angular/material';

export interface Tarea {
  Id: number;
  Tarea: string;
  Creador: string;
  Destinatario: string;
  FechaAlta: string;
  Estatus: string;
}

export interface Bitacora {
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
  public dataSourceTarea2 = new MatTableDataSource<Tarea>();
  // Enfoque del mapa
  lat: any;
  lng: any;
  zoom = 10;
  requerimiento: IRequerimiento;

  // Variables Tarea
  DATA_TAREA: Tarea[] = [
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
  dataSourceTarea: Tarea[];
  displayedColumnsTarea: string[] = [ 'Id', 'Tarea', 'Creador', 'Destinatario', 'FechaAlta', 'Estatus'];
  // VAriables Bitacora
  DATA_BITACORA: Bitacora[] = [
    { Fecha: '26/06/2019', Creador: 'Sistema', Comentario: 'MABE eliminó "C#" y "LinQ" de la lista de "Skills requeridos"' },
    { Fecha: '26/06/2019', Creador: 'MABE', Comentario: 'MABE agregó "Spring MVC" a la lista de "Skills esenciales"' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'El cliente me solicita esperar a que se lleven a cabo las entrevistas antes de enviar más gente.' },
    { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'MABE actualizó el campo "Tarifa" de $35 000.00 a $45 000.00' }
  ];
  dataSourceBitacora: Bitacora[];
  displayedColumnsBitacora: string[] = ['Fecha', 'Creador', 'Comentario'];
  constructor(protected activatedRoute: ActivatedRoute) {
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
  public filtrarTareasAbiertas = () => {
    this.dataSourceTarea2.filter = 'Abiertas'.trim().toLocaleLowerCase();
  }
  public filtrarTareasCerradas = () => {
    this.dataSourceTarea2.filter = 'Cerradas'.trim().toLocaleLowerCase();
  }
  public filtrarTareasTodas = () => {
    this.dataSourceTarea2.filter = ''.trim().toLocaleLowerCase();
  }
  ngOnInit() {
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      this.requerimiento = requerimiento;
    });
    // Enfoque del mapa
    this.lat = this.requerimiento.coorLat;
    this.lng = this.requerimiento.coorLong;
    this.zoom = 10;
    this.dataSourceTarea2.data = this.DATA_TAREA;
  }
  previousState() {
    window.history.back();
  }
}
function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
