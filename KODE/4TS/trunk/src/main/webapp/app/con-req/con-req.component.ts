import {Component, OnInit, ViewChild} from '@angular/core';
// Inicio datatable
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';

export interface PeriodicElement {
  Id: number;
  Estatus: string;
  Cuenta: string;
  Perfil: string;
  Nivel: string;
  Ejecutivo: string;
  Reclutador: string;
  TipoSolic: string;
  VacSolic: string;
  VacPend: string;
  Prioridad: String;
  UltEnvCv: string;
  UltEnt: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {Id: 1, Estatus: 'Abierto', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Junior', Ejecutivo: 'MABE' , Reclutador: 'JCE', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Enero-2019'},
  {Id: 2, Estatus: 'Abierto', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Semi Sr', Ejecutivo: 'MABE' , Reclutador: 'JCE', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Enero-2019'},
  {Id: 3, Estatus: 'Abierto', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Semi Sr', Ejecutivo: 'MABE' , Reclutador: 'JCE', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Septiembre-2018'},
  {Id: 4, Estatus: 'Abierto', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Semi Sr', Ejecutivo: 'MABE' , Reclutador: 'JCE', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Enero-2019'},
  {Id: 4, Estatus: 'Abierto', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Semi Sr', Ejecutivo: 'MABE' , Reclutador: 'JCE', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Enero-2019'},
  {Id: 4, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Semi Sr', Ejecutivo: 'MABE' , Reclutador: 'FJR', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Noviembre-2018'},
  {Id: 4, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Semi Sr', Ejecutivo: 'MABE' , Reclutador: 'FJR', TipoSolic: 'OutSourcing', VacSolic: '3', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '01-Enero-2019', UltEnt: '24-Enero-2019'},
  {Id: 4, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Sr', Ejecutivo: 'CALS' , Reclutador: 'FJR', TipoSolic: 'Interno', VacSolic: '1', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '05-Mayo-2019', UltEnt: '24-Marzo-2019'},
  {Id: 9, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Sr', Ejecutivo: 'CALS' , Reclutador: 'FJR', TipoSolic: 'Interno', VacSolic: '1', VacPend: '2', Prioridad: 'Alta', UltEnvCv: '05-Mayo-2019', UltEnt: '24-Marzo-2019'},
  {Id: 9, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Sr', Ejecutivo: 'CALS' , Reclutador: 'PCG', TipoSolic: 'Interno', VacSolic: '1', VacPend: '0', Prioridad: 'Alta', UltEnvCv: '05-Mayo-2019', UltEnt: '24-Marzo-2019'},
  {Id: 9, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Java', Nivel: 'Sr', Ejecutivo: 'CALS' , Reclutador: 'PCG', TipoSolic: 'Interno', VacSolic: '1', VacPend: '1', Prioridad: 'Alta', UltEnvCv: '05-Mayo-2019', UltEnt: '24-Marzo-2019'},
  {Id: 9, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Php', Nivel: 'Sr', Ejecutivo: 'CALS' , Reclutador: 'PCG', TipoSolic: 'Interno', VacSolic: '1', VacPend: '1', Prioridad: 'Alta', UltEnvCv: '05-Mayo-2019', UltEnt: '24-Enero-2019'},
  {Id: 9, Estatus: 'Cerrado', Cuenta: 'Capgemini', Perfil: 'Php', Nivel: 'Sr', Ejecutivo: 'CALS' , Reclutador: 'PCG', TipoSolic: 'Interno', VacSolic: '1', VacPend: '1', Prioridad: 'Alta', UltEnvCv: '05-Mayo-2019', UltEnt: '24-Abril-2019'}
];
// Fin datatable

@Component({
  selector: 'jhi-con-req',
  templateUrl: './con-req.component.html',
  styleUrls: [
    'con-req.component.scss'
  ]
})

export class ConReqComponent implements OnInit {

  // Inicio datatable
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  // Fin datatable

  // Inicio datatable
  displayedColumns: string[] = ['Id', 'Estatus', 'Cuenta', 'Perfil', 'Nivel', 'Ejecutivo', 'Reclutador', 'TipoSolic', 'VacSolic', 'VacPend', 'Prioridad', 'UltEnvCv', 'UltEnt'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  // Fin datatable
  message: string;

  constructor() {
    this.message = 'ConReqComponent message';
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    // Inicio datatable
    this.dataSource.sort = this.sort;
    // Fin datatable
  }

}
