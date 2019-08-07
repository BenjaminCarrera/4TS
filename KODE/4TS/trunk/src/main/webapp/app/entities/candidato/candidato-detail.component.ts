import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICandidato } from 'app/shared/model/candidato.model';
// Inicio datatable
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ITarea } from '../../shared/model/tarea.model';
import { TareaService } from '../tarea/tarea.service';
import { HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { IBitacora } from 'app/shared/model/bitacora.model';
import { BitacoraService } from '../bitacora/bitacora.service';
import { ISkill } from '../../shared/model/skill.model';
import { SkillService } from '../skill/skill.service';
import { ISkillCandidato } from '../../shared/model/skill-candidato.model';
import { SkillCandidatoService } from '../skill-candidato';

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
  selector: 'jhi-candidato-detail',
  templateUrl: './candidato-detail.component.html',
  styleUrls: ['../../res-concand/res-concand.component.scss']
})
export class CandidatoDetailComponent implements OnInit {
  // Mostrar u ocultar cosas
  mostrarDetalleCandidatoInactivo: boolean;
  mostrarDisponibilidadEntrevistaCandidato: boolean;
  candidato: ICandidato;
  mostrarAsignacionCandidato: boolean;
  tareas: ITarea[];
  tareaCand: ITarea[];
  bitacoras: IBitacora[];
  bitacorasCand: IBitacora[];
  skills: ISkillCandidato[];
  skillsCand: ISkillCandidato[];

  // Enfoque del mapa
  lat = 19.391213;
  lng = -99.165761;
  zoom = 20;

  // Variables Tarea
  // DATA_TAREA: PeriodicElement[] = [
  //   { Id: 1, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '09/07/2019', Estatus: 'Abierta' },
  //   { Id: 2, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '09/07/2019', Estatus: 'Abierta' },
  //   { Id: 3, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '09/07/2019', Estatus: 'Abierta' },
  //   { Id: 4, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '09/07/2019', Estatus: 'Abierta' },
  //   { Id: 5, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '09/07/2019', Estatus: 'Abierta' },
  //   { Id: 3, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '09/07/2019', Estatus: 'Atendida' },
  //   { Id: 6, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Abierta' },
  //   { Id: 7, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Abierta' },
  //   { Id: 8, Tarea: 'Abierto', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Cerrada' },
  //   { Id: 9, Tarea: 'Cerrado', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Cerrada' },
  //   { Id: 10, Tarea: 'Cerrado', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Cerrada' },
  //   { Id: 11, Tarea: 'Cerrado', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Cerrada' },
  //   { Id: 12, Tarea: 'Cerrado', Creador: 'Yasser', Destinatario: 'Juan', FechaAlta: '01/07/2019', Estatus: 'Cerrada' }
  // ];
  // dataSourceTarea: PeriodicElement[];
  // displayedColumnsTarea: string[] = ['Id', 'Tarea', 'Creador', 'Destinatario', 'FechaAlta', 'Estatus'];

  // Variables Bitacora
  // DATA_BITACORA: Tarea[] = [
  //   { Fecha: '26/06/2019', Creador: 'Sistema', Comentario: 'MABE eliminó "C#" y "LinQ" de la lista de "Skills requeridos"' },
  //   { Fecha: '26/06/2019', Creador: 'MABE', Comentario: 'MABE agregó "Spring MVC" a la lista de "Skills esenciales"' },
  //   {
  //     Fecha: '04/01/2019',
  //     Creador: 'Sistema',
  //     Comentario: 'El cliente me solicita esperar a que se lleven a cabo las entrevistas antes de enviar más gente.'
  //   },
  //   { Fecha: '04/01/2019', Creador: 'Sistema', Comentario: 'MABE actualizó el campo "Tarifa" de $35 000.00 a $45 000.00' }
  // ];
  // dataSourceBitacora: Tarea[];
  // displayedColumnsBitacora: string[] = ['Fecha', 'Creador', 'Comentario'];

  // Variables Skills
  // DATA_SKILLS: Skills[] = [
  //   { Skills: 'Hibernate', Dominio: 'Intermedio', Calificacion: '10.0', Eliminar: 'Eliminar' },
  //   { Skills: 'Angular', Dominio: 'Avanzado', Calificacion: '9.0', Eliminar: 'Eliminar' },
  //   { Skills: 'Java', Dominio: 'Principiante', Calificacion: '7.0', Eliminar: 'Eliminar' }
  // ];
  // dataSourceSkills: Skills[];
  // displayedColumnsSkills: string[] = ['Skills', 'Dominio', 'Calificacion'];

  message: string;
  page: number;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected jhiAlertService: JhiAlertService,
    protected tareaService: TareaService,
    protected bitacoraService: BitacoraService,
    protected skillCandidatoService: SkillCandidatoService
    ) {
    // this.dataSourceTarea = this.DATA_TAREA.slice();
    // this.dataSourceBitacora = this.DATA_BITACORA.slice();
    // this.dataSourceSkills = this.DATA_SKILLS.slice();
  }

  // sortDataTarea(sort: MatSort) {
  //   const data = this.DATA_TAREA.slice();
  //   if (!sort.active || sort.direction === '') {
  //     this.dataSourceTarea = data;
  //     return;
  //   }

  //   this.dataSourceTarea = data.sort((a, b) => {
  //     const isAsc = sort.direction === 'asc';
  //     switch (sort.active) {
  //       case 'Id':
  //         return compare(a.Id, b.Id, isAsc);
  //       case 'Tarea':
  //         return compare(a.Tarea, b.Tarea, isAsc);
  //       case 'Creador':
  //         return compare(a.Creador, b.Creador, isAsc);
  //       case 'Destinatario':
  //         return compare(a.Destinatario, b.Destinatario, isAsc);
  //       case 'FechaAlta':
  //         return compare(a.FechaAlta, b.FechaAlta, isAsc);
  //       case 'Estatus':
  //         return compare(a.Estatus, b.Estatus, isAsc);
  //       default:
  //         return 0;
  //     }
  //   });
  // }

  // sortDataBitacora(sort: MatSort) {
  //   const data = this.DATA_BITACORA.slice();
  //   if (!sort.active || sort.direction === '') {
  //     this.dataSourceBitacora = data;
  //     return;
  //   }

  //   this.dataSourceBitacora = data.sort((a, b) => {
  //     const isAsc = sort.direction === 'asc';
  //     switch (sort.active) {
  //       case 'Fecha':
  //         return compare(a.Fecha, b.Fecha, isAsc);
  //       case 'Creador':
  //         return compare(a.Creador, b.Creador, isAsc);
  //       case 'Comentario':
  //         return compare(a.Comentario, b.Comentario, isAsc);
  //       default:
  //         return 0;
  //     }
  //   });
  // }

  // sortDataSkills(sort: MatSort) {
  //   const data = this.DATA_SKILLS.slice();
  //   if (!sort.active || sort.direction === '') {
  //     this.dataSourceSkills = data;
  //     return;
  //   }

  //   this.dataSourceSkills = data.sort((a, b) => {
  //     const isAsc = sort.direction === 'asc';
  //     switch (sort.active) {
  //       case 'Skills':
  //         return compare(a.Skills, b.Skills, isAsc);
  //       case 'Dominio':
  //         return compare(a.Dominio, b.Dominio, isAsc);
  //       case 'Calificacion':
  //         return compare(a.Calificacion, b.Calificacion, isAsc);
  //       default:
  //         return 0;
  //     }
  //   });
  // }
  ngOnInit() {
    this.activatedRoute.data.subscribe(({ candidato }) => {
      this.candidato = candidato;
    });
    this.loadAll();
    this.verificarStatus();
    this.verificarDisponibilidadEntrevista();
    this.verificarDisponibilidaAsignacion();
    this.candidato.foto = 'content/fotoCandidato/' + this.candidato.foto;
  }

  loadAll() {
    this.tareaService
      .query({
        page: this.page - 1,
        size: 9999
      })
      .subscribe(
        (res: HttpResponse<ITarea[]>) => this.paginateTareas(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.bitacoraService
      .query({
        page: this.page - 1,
        size: 9999
      })
      .subscribe(
        (res: HttpResponse<IBitacora[]>) => this.paginateBitacoras(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );

      this.skillCandidatoService
      .query({
        page: this.page - 1,
        size: 9999
      })
      .subscribe(
        (res: HttpResponse<ISkillCandidato[]>) => this.paginateSkillCandidatoes(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  previousState() {
    window.history.back();
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

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  protected paginateTareas(data: ITarea[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.tareas = data;

    this.tareaCand = this.tareas.filter(tar => tar.candidatoId === this.candidato.id);
  }

  protected paginateBitacoras(data: IBitacora[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.bitacoras = data;

    this.bitacorasCand = this.bitacoras.filter( bit => bit.candidatoId === this.candidato.id);
  }

  protected paginateSkillCandidatoes(data: ISkillCandidato[], headers: HttpHeaders) {
    // this.links = this.parseLinks.parse(headers.get('link'));
    // this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.skills = data;

    this.skillsCand = this.skills.filter( skill => skill.idCandidatoId === this.candidato.id);
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
