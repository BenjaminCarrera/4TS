import {Component, OnInit, ViewChild} from '@angular/core';
// Inicio datatable
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';

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

const ELEMENT_DATA: PeriodicElement[] = [
  {Id: 1, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 2, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 3, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 4, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 5, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 3, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 6, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 7, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 8, Tarea: 'Abierto', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 9, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 10, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 11, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'},
  {Id: 12, Tarea: 'Cerrado', Creador: 'Capgemini', Destinatario: 'Java', FechaAlta: 'Junior', Estatus: 'MABE'}
];

const ELEMENT_DATA2: Tarea[] = [
  {Fecha: '26/06/2019', Creador: 'Sistema', Comentario: 'MABE eliminó "C#" y "LinQ" de la lista de "Skills requeridos"'},
  {Fecha: '26/06/2019', Creador: 'MABE', Comentario: 'MABE agregó "Spring MVC" a la lista de "Skills esenciales"'},
  {Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'El cliente me solicita esperar a que se lleven a cabo las entrevistas antes de enviar más gente.'},
  {Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'MABE actualizó el campo "Tarifa" de $35 000.00 a $45 000.00'}
];
const ELEMENT_DATA3: Skills[] = [
  {Skills: 'Hibernate', Dominio: 'Intermedio', Calificacion: '10.0', Eliminar: 'Eliminar'},
  {Skills: 'Angular', Dominio: 'Avanzado', Calificacion: '9.0', Eliminar: 'Eliminar'},
  {Skills: 'Java', Dominio: 'Principiante', Calificacion: '7.0', Eliminar: 'Eliminar'},
];
@Component({
  selector: 'jhi-res-concand',
  templateUrl: './res-concand.component.html',
  styleUrls: [
    'res-concand.component.scss'
  ]
})
export class ResConcandComponent implements OnInit {
// Inicio datatable
@ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
// Fin datatable
// Inicio datatable
@ViewChild(MatPaginator, {static: true}) paginator2: MatPaginator;
// Fin datatable
// Inicio datatable
@ViewChild(MatPaginator, {static: true}) paginator3: MatPaginator;
// Fin datatable
// Inicio datatable
  displayedColumns: string[] = ['Id', 'Tarea', 'Creador', 'Destinatario' , 'FechaAlta' , 'Estatus'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);
  @ViewChild(MatSort, {static: true}) sort: MatSort;
// Fin datatable
// Inicio datatable
  displayedColumns2: string[] = ['Fecha', 'Creador', 'Comentario'];
  dataSource2 = new MatTableDataSource(ELEMENT_DATA2);
  @ViewChild(MatSort, {static: true}) sort2: MatSort;
// Fin datatable
// Inicio datatable
  displayedColumns3: string[] = ['Skills', 'Dominio', 'Calificacion', 'Eliminar'];
  dataSource3 = new MatTableDataSource(ELEMENT_DATA3);
  @ViewChild(MatSort, {static: true}) sort3: MatSort;
// Fin datatable

  message: string;

  constructor() {
    this.message = 'ConCandComponent message';
  }

  ngOnInit() {
    // Inicio datatable
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    // Fin datatable
    // Inicio datatable
    this.dataSource2.paginator = this.paginator2;
    this.dataSource2.sort = this.sort2;
    // Fin datatable
    // Inicio datatable
    this.dataSource3.paginator = this.paginator3;
    this.dataSource3.sort = this.sort3;
    // Fin datatable
  }

}
