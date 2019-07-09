import { Component, OnInit, ViewChild } from '@angular/core';
// Inicio datatable
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

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
export interface Skills {
  Skills: string;
  Dominio: string;
  Calificacion: string;
  Eliminar: string;
}

@Component({
  selector: 'jhi-res-concand',
  templateUrl: './res-concand.component.html',
  styleUrls: [
    'res-concand.component.scss'
  ]
})
export class ResConcandComponent implements OnInit {

  // Variables Tarea
  DATA_TAREA: PeriodicElement[] = [
    { Id: 1, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 2, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 3, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 4, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 5, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 3, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 6, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 7, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 8, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 9, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 10, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 11, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' },
    { Id: 12, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE' }
  ];
  dataSourceTarea: PeriodicElement[];
  displayedColumnsTarea: string[] = ['Id', 'Tarea', 'Creador', 'Destinatario', 'FechaAlta', 'Estatus'];

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
  displayedColumnsSkills: string[] = ['Skills', 'Dominio', 'Calificacion', 'Eliminar'];

  message: string;

  constructor() {
    this.message = 'ConCandComponent message';
    this.dataSourceTarea = this.DATA_TAREA.slice();
    this.dataSourceBitacora = this.DATA_BITACORA.slice();
    this.dataSourceSkills = this.DATA_SKILLS.slice();
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

  sortDataSkills(sort: MatSort) {
    const data = this.DATA_SKILLS.slice();
    if (!sort.active || sort.direction === '') {
      this.dataSourceSkills = data;
      return;
    }

    this.dataSourceSkills = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'Skills': return compare(a.Skills, b.Skills, isAsc);
        case 'Dominio': return compare(a.Dominio, b.Dominio, isAsc);
        case 'Calificacion': return compare(a.Calificacion, b.Calificacion, isAsc);
        case 'Eliminar': return compare(a.Eliminar, b.Eliminar, isAsc);
        default: return 0;
      }
    });
  }

  ngOnInit() {
  }

}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
