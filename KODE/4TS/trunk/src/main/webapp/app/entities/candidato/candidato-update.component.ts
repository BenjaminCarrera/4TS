import { Component, ElementRef, ViewChild, OnInit, NgZone } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map, startWith } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ICandidato, Candidato } from 'app/shared/model/candidato.model';
import { CandidatoService } from './candidato.service';
import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from 'app/entities/tipo-periodo';
import { IUser, UserService } from 'app/core';
import { IDocumento } from 'app/shared/model/documento.model';
import { DocumentoService } from 'app/entities/documento';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from 'app/entities/cuenta';
import { IFuenteReclutamiento } from 'app/shared/model/fuente-reclutamiento.model';
import { FuenteReclutamientoService } from 'app/entities/fuente-reclutamiento';
import { IEstatusCandidato } from 'app/shared/model/estatus-candidato.model';
import { EstatusCandidatoService } from 'app/entities/estatus-candidato';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from 'app/entities/nivel-perfil';
import { IInstitucionAcademica } from 'app/shared/model/institucion-academica.model';
import { InstitucionAcademicaService } from 'app/entities/institucion-academica';
import { IEstatusAcademico } from 'app/shared/model/estatus-academico.model';
import { EstatusAcademicoService } from 'app/entities/estatus-academico';
import { IEsquemaContratacionKode } from 'app/shared/model/esquema-contratacion-kode.model';
import { EsquemaContratacionKodeService } from 'app/entities/esquema-contratacion-kode';
import { IEstatusLaboral } from 'app/shared/model/estatus-laboral.model';
import { EstatusLaboralService } from 'app/entities/estatus-laboral';
import { IColonia } from 'app/shared/model/colonia.model';
import { ColoniaService } from 'app/entities/colonia';
import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from 'app/entities/esq-cont-rec';
import { IFormacionAcademica } from 'app/shared/model/formacion-academica.model';
import { FormacionAcademicaService } from 'app/entities/formacion-academica';
import { IEstCanInactivo } from 'app/shared/model/est-can-inactivo.model';
import { EstCanInactivoService } from 'app/entities/est-can-inactivo';

import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Tarea } from 'app/agreg-cand';
import { EstadoService } from '../estado';
import { IEstado } from 'app/shared/model/estado.model';
import { ALL_ITEMS } from 'app/shared';
import { IMunicipio } from 'app/shared/model/municipio.model';
import { MunicipioService } from '../municipio/municipio.service';
import { ICodigoPostal } from 'app/shared/model/codigo-postal.model';
import { CodigoPostalService } from '../codigo-postal';
import { exportDefaultSpecifier } from '@babel/types';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillService } from '../skill';
import { IDominioSkill } from 'app/shared/model/dominio-skill.model';
import { DominioSkillService } from '../dominio-skill';
import { ISkillCandidato, SkillCandidato } from 'app/shared/model/skill-candidato.model';
import { MapsAPILoader, MouseEvent } from '@agm/core';
import { SkillCandidatoService } from '../skill-candidato';

@Component({
  selector: 'jhi-agreg-cand',
  templateUrl: './candidato-update.component.html',
  styleUrls: [
    'agreg-cand.component.scss'
  ]
})
export class CandidatoUpdateComponent implements OnInit {
  // Ocultar
  ocultar = true;
  // Verificar los skills
  skillsCandidatoBd: ISkillCandidato[];
  selecteds = new FormControl(0);
  matAutocomplete: MatAutocomplete;
  cuentaIntCtrl = new FormControl();
  cuentasIntSelected: ICuenta[];
  cuentasInteres: ICuenta[];
  cuentasRechSelected: ICuenta[];
  cuentasRechazadas: ICuenta[];
  estados: IEstado[];
  allItems: any;
  municipios: IMunicipio[];
  skills: ISkill[];
  skillsCandidato: ISkillCandidato[];
  skillsFilter: ISkill[];
  dominioSkill: IDominioSkill[];
  skillCandidatoes: ISkillCandidato[];
  skillsSelected: ISkill[];
  errorMessageSkill: any;
  latitude: number;
  longitude: number;
  zoom: number;
  geoCoder: any;
  searchElementRef: ElementRef;

  isSaving: boolean;

  tipoperiodos: ITipoPeriodo[];

  users: IUser[];

  documentos: IDocumento[];

  cuentas: ICuenta[];

  fuentereclutamientos: IFuenteReclutamiento[];

  estatuscandidatoes: IEstatusCandidato[];

  perfils: IPerfil[];

  nivelperfils: INivelPerfil[];

  institucionacademicas: IInstitucionAcademica[];

  estatusacademicos: IEstatusAcademico[];

  esquemacontratacionkodes: IEsquemaContratacionKode[];

  estatuslaborals: IEstatusLaboral[];

  colonias: IColonia[];

  esqcontrecs: IEsqContRec[];

  formacionacademicas: IFormacionAcademica[];

  estcaninactivos: IEstCanInactivo[];
  fechaNacimientoDp: any;
  disponibilidadEntrevistaFechaDp: any;
  disponibilidadAsignacionFechaDp: any;

  editForm = this.fb.group({
    id: [],
    anosExperiencia: [],
    nombre: [null, [Validators.required, Validators.maxLength(100)]],
    apellidoPaterno: [null, [Validators.required, Validators.maxLength(100)]],
    apellidoMaterno: [null, [Validators.maxLength(100)]],
    fechaNacimiento: [],
    edad: [],
    emailPrincipal: [null, [Validators.required, Validators.maxLength(100)]],
    emailAdicional: [null, [Validators.maxLength(100)]],
    emailAsignacion: [null, [Validators.maxLength(100)]],
    emailKode: [null, [Validators.maxLength(100)]],
    web: [null, [Validators.maxLength(100)]],
    telefonoCasa: [null, [Validators.maxLength(20)]],
    telefonoTrabajo: [null, [Validators.maxLength(20)]],
    telefonoCelular: [null, [Validators.maxLength(20)]],
    telefonoAdicional: [null, [Validators.maxLength(20)]],
    coorLat: [],
    coorLong: [],
    dirCodigoPostal: [null, [Validators.maxLength(5), Validators.minLength(5)]],
    dirEstado: [],
    dirMunicipio: [],
    dirColonia: [],
    dirCalle: [null, [Validators.maxLength(100)]],
    noExt: [null, [Validators.maxLength(100)]],
    noInt: [null, [Validators.maxLength(100)]],
    salarioNeto: [],
    costoTotal: [],
    contatoDuracionMinima: [],
    disponibilidadEntrevistaFecha: [],
    disponibilidadEntrevistaPeriodo: [],
    disponibilidadAsignacionFecha: [],
    disponibilidadAsignacionPeriodo: [null, [Validators.maxLength(100)]],
    antecedenteUltimoEmpleador: [null, [Validators.maxLength(100)]],
    antecedenteSalarioNeto: [],
    notas: [null, [Validators.maxLength(1000)]],
    porcentajeIngles: [],
    curp: [null, [Validators.maxLength(50)]],
    rfc: [null, [Validators.maxLength(50)]],
    nss: [null, [Validators.maxLength(50)]],
    sexo: [],
    estadoCivil: [],
    fechaAlta: [],
    fechaUltimoSeguimiento: [],
    foto: [null, [Validators.maxLength(1000)]],
    disponibilidadEntrevistaPeriodoTiempoId: [],
    disponibilidadAsignacionPeriodoTiempoId: [],
    usuarioCreadorId: [],
    usuarioAsignadoId: [],
    documentoId: [],
    cuentaInteres: [],
    cuentaRechazadas: [],
    fuenteReclutamientoId: [],
    estatusCandidatoId: [],
    perfilId: [],
    nivelPerfilId: [],
    institucionAcademicaId: [],
    estatusAcademicoId: [],
    esquemaContratacionKodeId: [],
    estatusLaboralId: [],
    coloniaId: [],
    antecedentesEsquemaContratacionId: [],
    estudiosId: [],
    estCanInactivoId: [],
    skill: [],
    skillDominio: [],
    skillCalificacion: []
  });

  selectable = true;
  selectable2 = true;
  removable = true;
  removable2 = true;
  filteredFruits: Observable<string[]>;
  filteredFruits2: Observable<string[]>;

  addOnBlur = true;
  addOnBlur2 = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  separatorKeysCodes2: number[] = [ENTER, COMMA];
  fruitCtrl2 = new FormControl();
  @ViewChild('fruitInput2', { static: false }) fruitInput2: ElementRef<HTMLInputElement>;
  @ViewChild('fruitInput', { static: false }) fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto2', { static: false }) matAutocomplete2: MatAutocomplete;

  constructor(
    protected codigoPostalService: CodigoPostalService,
    protected skillCandidatoService: SkillCandidatoService,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone,
    protected skillService: SkillService,
    protected dominioSkillService: DominioSkillService,
    protected estadoService: EstadoService,
    protected municipioService: MunicipioService,
    protected jhiAlertService: JhiAlertService,
    protected candidatoService: CandidatoService,
    protected tipoPeriodoService: TipoPeriodoService,
    protected userService: UserService,
    protected documentoService: DocumentoService,
    protected cuentaService: CuentaService,
    protected fuenteReclutamientoService: FuenteReclutamientoService,
    protected estatusCandidatoService: EstatusCandidatoService,
    protected perfilService: PerfilService,
    protected nivelPerfilService: NivelPerfilService,
    protected institucionAcademicaService: InstitucionAcademicaService,
    protected estatusAcademicoService: EstatusAcademicoService,
    protected esquemaContratacionKodeService: EsquemaContratacionKodeService,
    protected estatusLaboralService: EstatusLaboralService,
    protected coloniaService: ColoniaService,
    protected esqContRecService: EsqContRecService,
    protected formacionAcademicaService: FormacionAcademicaService,
    protected estCanInactivoService: EstCanInactivoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {
    this.allItems = ALL_ITEMS;
    this.cuentaIntCtrl.valueChanges.subscribe(newValue => {
      this.cuentasInteres = this.filterCuentasInteres(newValue);
    });
    this.fruitCtrl2.valueChanges.subscribe(newValue => {
      this.cuentasRechazadas = this.filterCuentasRechazadas(newValue);
    });
  }

  clearDir() {
    this.municipios = [];
    this.colonias = [];
    this.editForm.get(['dirEstado']).setValue(null);
    this.estadoService
      .query({
        size: this.allItems
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IEstado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstado[]>) => response.body)
      )
      .subscribe(
        (res: IEstado[]) => (this.estados = res), (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.errorMessageSkill = null;
    this.cuentasIntSelected = [];
    this.cuentasRechSelected = [];
    this.skillCandidatoes = [];
    this.skillsSelected = [];
    this.isSaving = false;
    this.clearDir();
    this.activatedRoute.data.subscribe(({ candidato }) => {
      if (candidato.coloniaId) {
        this.coloniaService
          .find(candidato.coloniaId)
          .pipe(
            filter((mayBeOk: HttpResponse<IColonia>) => mayBeOk.ok),
            map((response: HttpResponse<IColonia>) => response.body)
          )
          .subscribe(
            (col: IColonia) => this.loadColonia(col), (col: HttpErrorResponse) => this.onError(col.message)
          );
        this.updateForm(candidato);
      }
    });
    this.mapsAPILoader.load().then(() => {
      this.setCurrentLocation();
      this.geoCoder = new google.maps.Geocoder;
    });
    this.skillService
      .query({
        size: ALL_ITEMS
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe(
        (res: ISkill[]) => (this.setSkills(res)),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.skillCandidatoService
      .query({
        size: ALL_ITEMS
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe(
        (res: ISkillCandidato[]) => (this.setSkillsCandidato(res)),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.dominioSkillService
      .query({
        size: ALL_ITEMS,
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IDominioSkill[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDominioSkill[]>) => response.body)
      )
      .subscribe(
        (res: IDominioSkill[]) => this.dominioSkill = res,
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.tipoPeriodoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoPeriodo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoPeriodo[]>) => response.body)
      )
      .subscribe((res: ITipoPeriodo[]) => (this.tipoperiodos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.documentoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDocumento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDocumento[]>) => response.body)
      )
      .subscribe((res: IDocumento[]) => (this.documentos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.cuentaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICuenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICuenta[]>) => response.body)
      )
      .subscribe((res: ICuenta[]) => (this.setCuentas(res)), (res: HttpErrorResponse) => this.onError(res.message));
    this.fuenteReclutamientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuenteReclutamiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuenteReclutamiento[]>) => response.body)
      )
      .subscribe((res: IFuenteReclutamiento[]) => (this.fuentereclutamientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusCandidatoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusCandidato[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusCandidato[]>) => response.body)
      )
      .subscribe((res: IEstatusCandidato[]) => (this.estatuscandidatoes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.perfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerfil[]>) => response.body)
      )
      .subscribe((res: IPerfil[]) => (this.perfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.nivelPerfilService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INivelPerfil[]>) => mayBeOk.ok),
        map((response: HttpResponse<INivelPerfil[]>) => response.body)
      )
      .subscribe((res: INivelPerfil[]) => (this.nivelperfils = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.institucionAcademicaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInstitucionAcademica[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstitucionAcademica[]>) => response.body)
      )
      .subscribe(
        (res: IInstitucionAcademica[]) => (this.institucionacademicas = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.estatusAcademicoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusAcademico[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusAcademico[]>) => response.body)
      )
      .subscribe((res: IEstatusAcademico[]) => (this.estatusacademicos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.esquemaContratacionKodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsquemaContratacionKode[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsquemaContratacionKode[]>) => response.body)
      )
      .subscribe(
        (res: IEsquemaContratacionKode[]) => (this.esquemacontratacionkodes = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.estatusLaboralService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusLaboral[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusLaboral[]>) => response.body)
      )
      .subscribe((res: IEstatusLaboral[]) => (this.estatuslaborals = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.esqContRecService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsqContRec[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsqContRec[]>) => response.body)
      )
      .subscribe((res: IEsqContRec[]) => (this.esqcontrecs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.formacionAcademicaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFormacionAcademica[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFormacionAcademica[]>) => response.body)
      )
      .subscribe((res: IFormacionAcademica[]) => (this.formacionacademicas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estCanInactivoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstCanInactivo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstCanInactivo[]>) => response.body)
      )
      .subscribe((res: IEstCanInactivo[]) => (this.estcaninactivos = res), (res: HttpErrorResponse) => this.onError(res.message));

  }

  updateForm(candidato: ICandidato) {
    this.editForm.patchValue({
      id: candidato.id,
      anosExperiencia: candidato.anosExperiencia,
      nombre: candidato.nombre,
      apellidoPaterno: candidato.apellidoPaterno,
      apellidoMaterno: candidato.apellidoMaterno,
      fechaNacimiento: candidato.fechaNacimiento ? candidato.fechaNacimiento.toDate() : null,
      edad: candidato.edad,
      emailPrincipal: candidato.emailPrincipal,
      emailAdicional: candidato.emailAdicional,
      emailAsignacion: candidato.emailAsignacion,
      emailKode: candidato.emailKode,
      web: candidato.web,
      telefonoCasa: candidato.telefonoCasa,
      telefonoTrabajo: candidato.telefonoTrabajo,
      telefonoCelular: candidato.telefonoCelular,
      telefonoAdicional: candidato.telefonoAdicional,
      coorLat: candidato.coorLat,
      coorLong: candidato.coorLong,
      dirCodigoPostal: candidato.dirCodigoPostal,
      dirCalle: candidato.dirCalle,
      noExt: candidato.noExt,
      noInt: candidato.noInt,
      salarioNeto: candidato.salarioNeto,
      costoTotal: candidato.costoTotal,
      contatoDuracionMinima: candidato.contatoDuracionMinima,
      disponibilidadEntrevistaFecha: candidato.disponibilidadEntrevistaFecha ? candidato.disponibilidadEntrevistaFecha.toDate() : null,
      disponibilidadEntrevistaPeriodo: candidato.disponibilidadEntrevistaPeriodo,
      disponibilidadAsignacionFecha: candidato.disponibilidadAsignacionFecha ? candidato.disponibilidadAsignacionFecha.toDate() : null,
      disponibilidadAsignacionPeriodo: candidato.disponibilidadAsignacionPeriodo,
      antecedenteUltimoEmpleador: candidato.antecedenteUltimoEmpleador,
      antecedenteSalarioNeto: candidato.antecedenteSalarioNeto,
      notas: candidato.notas,
      porcentajeIngles: candidato.porcentajeIngles,
      curp: candidato.curp,
      rfc: candidato.rfc,
      nss: candidato.nss,
      sexo: candidato.sexo,
      estadoCivil: candidato.estadoCivil,
      fechaAlta: candidato.fechaAlta ? candidato.fechaAlta.format(DATE_TIME_FORMAT) : null,
      fechaUltimoSeguimiento: candidato.fechaUltimoSeguimiento ? candidato.fechaUltimoSeguimiento.format(DATE_TIME_FORMAT) : null,
      foto: candidato.foto,
      disponibilidadEntrevistaPeriodoTiempoId: candidato.disponibilidadEntrevistaPeriodoTiempoId,
      disponibilidadAsignacionPeriodoTiempoId: candidato.disponibilidadAsignacionPeriodoTiempoId,
      usuarioCreadorId: candidato.usuarioCreadorId,
      usuarioAsignadoId: candidato.usuarioAsignadoId,
      documentoId: candidato.documentoId,
      cuentaInteres: candidato.cuentaInteres,
      cuentaRechazadas: candidato.cuentaRechazadas,
      fuenteReclutamientoId: candidato.fuenteReclutamientoId,
      estatusCandidatoId: candidato.estatusCandidatoId,
      perfilId: candidato.perfilId,
      nivelPerfilId: candidato.nivelPerfilId,
      institucionAcademicaId: candidato.institucionAcademicaId,
      estatusAcademicoId: candidato.estatusAcademicoId,
      esquemaContratacionKodeId: candidato.esquemaContratacionKodeId,
      estatusLaboralId: candidato.estatusLaboralId,
      coloniaId: candidato.coloniaId,
      antecedentesEsquemaContratacionId: candidato.antecedentesEsquemaContratacionId,
      estudiosId: candidato.estudiosId,
      estCanInactivoId: candidato.estCanInactivoId
    });
  }

  previousState() {
    window.history.back();
  }
  borrarSkillCandidatos(id: number) {
    this.skillCandidatoService.delete(id).subscribe(response => { });
  }
  save() {
    this.isSaving = true;
    const candidato = this.createFromForm();
    if (candidato.id !== undefined) {
      for (const clave of this.skillsCandidato) {
        if (clave.idCandidatoId === this.editForm.get(['id']).value) {
          this.borrarSkillCandidatos(clave.id);
        }
      }
      this.subscribeToSaveResponse(this.candidatoService.update(candidato));
    } else {
      this.subscribeToSaveResponse(this.candidatoService.create(candidato));
    }
  }

  private createFromForm(): ICandidato {
    return {
      ...new Candidato(),
      id: this.editForm.get(['id']).value,
      anosExperiencia: this.editForm.get(['anosExperiencia']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellidoPaterno: this.editForm.get(['apellidoPaterno']).value,
      apellidoMaterno: this.editForm.get(['apellidoMaterno']).value,
      fechaNacimiento: moment(this.editForm.get(['fechaNacimiento']).value),
      edad: this.editForm.get(['edad']).value,
      emailPrincipal: this.editForm.get(['emailPrincipal']).value,
      emailAdicional: this.editForm.get(['emailAdicional']).value,
      emailAsignacion: this.editForm.get(['emailAsignacion']).value,
      emailKode: this.editForm.get(['emailKode']).value,
      web: this.editForm.get(['web']).value,
      telefonoCasa: this.editForm.get(['telefonoCasa']).value,
      telefonoTrabajo: this.editForm.get(['telefonoTrabajo']).value,
      telefonoCelular: this.editForm.get(['telefonoCelular']).value,
      telefonoAdicional: this.editForm.get(['telefonoAdicional']).value,
      coorLat: this.editForm.get(['coorLat']).value,
      coorLong: this.editForm.get(['coorLong']).value,
      dirCodigoPostal: this.editForm.get(['dirCodigoPostal']).value,
      dirCalle: this.editForm.get(['dirCalle']).value,
      noExt: this.editForm.get(['noExt']).value,
      noInt: this.editForm.get(['noInt']).value,
      salarioNeto: this.editForm.get(['salarioNeto']).value,
      costoTotal: this.editForm.get(['costoTotal']).value,
      contatoDuracionMinima: this.editForm.get(['contatoDuracionMinima']).value,
      disponibilidadEntrevistaFecha: this.editForm.get(['disponibilidadEntrevistaFecha']).value,
      disponibilidadEntrevistaPeriodo: this.editForm.get(['disponibilidadEntrevistaPeriodo']).value,
      disponibilidadAsignacionFecha: this.editForm.get(['disponibilidadAsignacionFecha']).value,
      disponibilidadAsignacionPeriodo: this.editForm.get(['disponibilidadAsignacionPeriodo']).value,
      antecedenteUltimoEmpleador: this.editForm.get(['antecedenteUltimoEmpleador']).value,
      antecedenteSalarioNeto: this.editForm.get(['antecedenteSalarioNeto']).value,
      notas: this.editForm.get(['notas']).value,
      porcentajeIngles: this.editForm.get(['porcentajeIngles']).value,
      curp: this.editForm.get(['curp']).value,
      rfc: this.editForm.get(['rfc']).value,
      nss: this.editForm.get(['nss']).value,
      sexo: this.editForm.get(['sexo']).value,
      estadoCivil: this.editForm.get(['estadoCivil']).value,
      fechaAlta:
        this.editForm.get(['fechaAlta']).value != null ? this.editForm.get(['fechaAlta']).value : undefined,
      fechaUltimoSeguimiento:
        this.editForm.get(['fechaUltimoSeguimiento']).value != null
          ? this.editForm.get(['fechaUltimoSeguimiento']).value
          : undefined,
      foto: this.editForm.get(['foto']).value,
      disponibilidadEntrevistaPeriodoTiempoId: this.editForm.get(['disponibilidadEntrevistaPeriodoTiempoId']).value,
      disponibilidadAsignacionPeriodoTiempoId: this.editForm.get(['disponibilidadAsignacionPeriodoTiempoId']).value,
      usuarioCreadorId: this.editForm.get(['usuarioCreadorId']).value,
      usuarioAsignadoId: this.editForm.get(['usuarioAsignadoId']).value,
      documentoId: this.editForm.get(['documentoId']).value,
      cuentaInteres: this.cuentasIntSelected,
      cuentaRechazadas: this.cuentasRechSelected,
      fuenteReclutamientoId: this.editForm.get(['fuenteReclutamientoId']).value,
      estatusCandidatoId: this.editForm.get(['estatusCandidatoId']).value,
      perfilId: this.editForm.get(['perfilId']).value,
      nivelPerfilId: this.editForm.get(['nivelPerfilId']).value,
      institucionAcademicaId: this.editForm.get(['institucionAcademicaId']).value,
      estatusAcademicoId: this.editForm.get(['estatusAcademicoId']).value,
      esquemaContratacionKodeId: this.editForm.get(['esquemaContratacionKodeId']).value,
      estatusLaboralId: this.editForm.get(['estatusLaboralId']).value,
      coloniaId: this.editForm.get(['dirColonia']).value,
      antecedentesEsquemaContratacionId: this.editForm.get(['antecedentesEsquemaContratacionId']).value,
      estudiosId: this.editForm.get(['estudiosId']).value,
      estCanInactivoId: this.editForm.get(['estCanInactivoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICandidato>>) {
    result.subscribe(r => this.onSaveSuccess(r), () => this.onSaveError());
  }

  protected subscribeToSaveResponseSkillsCand(result: Observable<HttpResponse<any>>) {
    result.subscribe(r => this.onSaveSuccessFinal(r), () => this.onSaveError());
  }

  protected onSaveSuccessFinal(r: HttpResponse<any>) {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveSuccess(r: HttpResponse<ICandidato>) {
    const idCandidato = r.body.id;
    if (this.skillCandidatoes.length > 0) {
      this.skillCandidatoes.forEach(c => {
        c.idCandidatoId = idCandidato;
      });
    }
    const updateSkillCan = { lista: this.skillCandidatoes };
    this.subscribeToSaveResponseSkillsCand(this.skillCandidatoService.patch(updateSkillCan));
  }

  protected onSaveError() {
    this.selecteds.setValue(0);
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTipoPeriodoById(index: number, item: ITipoPeriodo) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackEstadoById(index: number, item: IEstado) {
    return item.id;
  }

  trackMunicipioById(index: number, item: IMunicipio) {
    return item.id;
  }

  trackDocumentoById(index: number, item: IDocumento) {
    return item.id;
  }

  trackCuentaById(index: number, item: ICuenta) {
    return item.id;
  }

  trackFuenteReclutamientoById(index: number, item: IFuenteReclutamiento) {
    return item.id;
  }

  trackEstatusCandidatoById(index: number, item: IEstatusCandidato) {
    return item.id;
  }

  trackPerfilById(index: number, item: IPerfil) {
    return item.id;
  }

  trackNivelPerfilById(index: number, item: INivelPerfil) {
    return item.id;
  }

  trackInstitucionAcademicaById(index: number, item: IInstitucionAcademica) {
    return item.id;
  }

  trackEstatusAcademicoById(index: number, item: IEstatusAcademico) {
    return item.id;
  }

  trackEsquemaContratacionKodeById(index: number, item: IEsquemaContratacionKode) {
    return item.id;
  }

  trackEstatusLaboralById(index: number, item: IEstatusLaboral) {
    return item.id;
  }

  trackColoniaById(index: number, item: IColonia) {
    return item.id;
  }

  trackEsqContRecById(index: number, item: IEsqContRec) {
    return item.id;
  }

  trackFormacionAcademicaById(index: number, item: IFormacionAcademica) {
    return item.id;
  }

  trackEstCanInactivoById(index: number, item: IEstCanInactivo) {
    return item.id;
  }

  trackSkillById(index: number, item: ISkill) {
    return item.id;
  }

  trackDominioById(index: number, item: IDominioSkill) {
    return item.id;
  }

  trackSkillCandidatoById(index: number, item: ISkillCandidato) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }

  siguiente() {
    this.selecteds.setValue(1);
  }

  addCuentaInt(event: MatChipInputEvent): void {
    // No se permite agregar elementos que no esten en la base de datos
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    const cuentasIntSelected: ICuenta[] = this.cuentas.filter(c => (c.id === event.option.value));
    const cuentaIntSelected: ISkill = cuentasIntSelected.shift();
    this.cuentasIntSelected.push(cuentaIntSelected);

    this.fruitInput.nativeElement.value = '';
    this.cuentaIntCtrl.setValue(null);
    this.updateCuentasInteres();
  }

  remove(cuentaInteres: ICuenta): void {
    const index = this.cuentasIntSelected.indexOf(cuentaInteres);
    if (index >= 0) {
      this.cuentasIntSelected.splice(index, 1);
    }
    this.updateCuentasInteres();
  }

  filterCuentasInteres(value: string): ICuenta[] {
    const temp: ICuenta[] = this.cuentas.slice(0);
    this.cuentasIntSelected.forEach(ci => {
      const index = temp.indexOf(ci);
      if (index >= 0) {
        temp.splice(index, 1);
      }
    });
    return temp.filter(s => new RegExp(value, 'gi').test(s.nombre));
  }

  remove2(cuentaRechazada: ICuenta): void {
    const index = this.cuentasRechSelected.indexOf(cuentaRechazada);
    if (index >= 0) {
      this.cuentasRechSelected.splice(index, 1);
    }
    this.updateCuentasRechazadas();
  }
  selected2(event: MatAutocompleteSelectedEvent): void {
    const cuentasRechSelected: ICuenta[] = this.cuentas.filter(c => (c.id === event.option.value));
    const cuentaRechSelected: ISkill = cuentasRechSelected.shift();
    this.cuentasRechSelected.push(cuentaRechSelected);

    this.fruitInput2.nativeElement.value = '';
    this.fruitCtrl2.setValue(null);
    this.updateCuentasRechazadas();
  }
  add2(event: MatChipInputEvent): void {
    // No se permite agregar elementos que no esten en la base de datos
  }

  filterCuentasRechazadas(value: string): ICuenta[] {
    const temp: ICuenta[] = this.cuentas.slice(0);
    this.cuentasRechSelected.forEach(cr => {
      const index = temp.indexOf(cr);
      if (index >= 0) {
        temp.splice(index, 1);
      }
    });
    return temp.filter(s => new RegExp(value, 'gi').test(s.nombre));
  }

  setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(position => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = 50;
      });
    }
  }
  markerDragEnd($event: MouseEvent) {
    this.latitude = $event.coords.lat;
    this.longitude = $event.coords.lng;
  }

  getAddress() {

    let address: any = '';

    if (this.editForm.get('dirCalle').value !== null) {
      address += this.editForm.get('dirCalle').value + ' ';
    }
    if (this.editForm.get('noExt').value !== null) {
      address += this.editForm.get('noExt').value + ', ';
    }
    if (this.editForm.get('dirCodigoPostal').value !== null) {
      address += this.editForm.get('dirCodigoPostal').value + ' ';
    }
    if (this.editForm.get('dirColonia').value !== null) {
      this.colonias.forEach(col => {
        if (col.municipioId === this.editForm.get('dirColonia').value) {
          address += col.colonia + ' ';
        }
      });
    }
    if (this.editForm.get('dirMunicipio').value !== null) {
      this.municipios.forEach(mun => {
        if (mun.id === this.editForm.get('dirMunicipio').value) {
          address += mun.municipio + ' ';
        }
      });
    }
    if (this.editForm.get('dirEstado').value !== null) {
      this.estados.forEach(est => {
        if (est.id === this.editForm.get('dirEstado').value) {
          address += est.estado;
        }
      });
    }
    this.geoCoder.geocode({ 'address': address }, (results, status) => {
      if (status === 'OK') {
        if (results[0]) {
          this.latitude = results[0].geometry.location.lat();
          this.longitude = results[0].geometry.location.lng();
          this.zoom = 50;
          // this.address = results[0].formatted_address;
        } else {
          window.alert('No hay resultados disponibles');
        }
      } else {
        window.alert('La geolocalizacion fallo: ' + status);
      }

    });
  }

  changeCodigoPostal(event: any) {
    const cp = event.srcElement.value;
    if (cp.length === 5) {
      this.codigoPostalService
        .query({
          criteria: [{ key: 'codigoPostal.equals', value: cp }]
        })
        .pipe(
          filter((mayBeOk: HttpResponse<ICodigoPostal[]>) => mayBeOk.ok),
          map((response: HttpResponse<ICodigoPostal[]>) => response.body)
        )
        .subscribe(
          (res: ICodigoPostal[]) => (this.updateMunicipio(res)), (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else if (cp.length === 0) {
      this.clearDir();
    }
  }

  onChangeEstado(event: any) {
    this.colonias = [];
    this.municipioService
      .query({
        criteria: [{ key: 'estadoId.in', value: event.value }],
        size: ALL_ITEMS
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio[]>) => response.body)
      )
      .subscribe(
        (res: IMunicipio[]) => (this.municipios = res), (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  onChangeMunicipio(event: any) {
    this.coloniaService
      .query({
        criteria: [{ key: 'municipioId.equals', value: event.value }],
        size: ALL_ITEMS
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IColonia[]>) => mayBeOk.ok),
        map((response: HttpResponse<IColonia[]>) => response.body)
      )
      .subscribe(
        (res: IColonia[]) => (this.colonias = res), (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateMunicipio(codigosPostales: ICodigoPostal[]) {
    const cp = codigosPostales.shift();
    this.municipioService
      .find(cp.municipioId)
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio>) => response.body)
      )
      .subscribe(
        (res: IMunicipio) => (this.updateEstadoColonia(res, cp.id)), (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateEstadoColonia(municipio: IMunicipio, idCp: number) {
    this.estadoService
      .find(municipio.estadoId)
      .pipe(
        filter((mayBeOk: HttpResponse<IEstado>) => mayBeOk.ok),
        map((response: HttpResponse<IEstado>) => response.body)
      )
      .subscribe(
        (res: IEstado) => (this.setMunicipioEstado(municipio, res)), (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.coloniaService
      .query({
        criteria: [{ key: 'codigoPostalId.equals', value: idCp }],
      })
      .pipe(
        filter((mayBeOk: HttpResponse<IColonia[]>) => mayBeOk.ok),
        map((response: HttpResponse<IColonia[]>) => response.body)
      )
      .subscribe((res: IColonia[]) => (this.colonias = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  setMunicipioEstado(municipio: IMunicipio, estado: IEstado) {
    this.municipios = [];
    this.municipios.push(municipio);
    this.editForm.get(['dirMunicipio']).setValue(municipio.id);

    this.estados = [];
    this.estados.push(estado);
    this.editForm.get(['dirEstado']).setValue(estado.id);
  }

  setSkills(res: ISkill[]) {
    this.skills = res;
    this.skillsFilter = res;
  }
  setSkillsCandidato(res: ISkillCandidato[]) {
    this.skillsCandidato = res;
    for (const clave of this.skillsCandidato) {
      if (clave.idCandidatoId === this.editForm.get(['id']).value) {
        const newSkillCandidato: ISkillCandidato = new SkillCandidato();
        newSkillCandidato.id = clave.id;
        newSkillCandidato.idSkillId = clave.idSkillId;
        const skillSelected = this.skills.find(item => item.id === newSkillCandidato.idSkillId);
        this.skillsSelected.push(skillSelected);
        newSkillCandidato.idSkillNombre = skillSelected.nombre;
        newSkillCandidato.nivelSkillId = clave.nivelSkillId;
        newSkillCandidato.nivelSkillDominio = clave.nivelSkillDominio;
        newSkillCandidato.calificacionSkill = clave.calificacionSkill;
        this.skillCandidatoes.push(newSkillCandidato);
        this.editForm.get(['skill']).setValue(null);
        this.editForm.get(['skillDominio']).setValue(null);
        this.editForm.get(['skillCalificacion']).setValue(null);
        this.updateSkills();
      }
    }
  }

  setCuentas(res: ICuenta[]) {
    this.cuentas = res;
    this.cuentasInteres = res;
    this.cuentasRechazadas = res;
    if (this.editForm.get(['cuentaInteres']).value) {
      this.editForm.get(['cuentaInteres']).value.forEach(element => {
        this.cuentasIntSelected.push(element);
      });
    }
    if (this.editForm.get(['cuentaRechazadas']).value) {
      this.editForm.get(['cuentaRechazadas']).value.forEach(element => {
        this.cuentasRechSelected.push(element);
      });
    }
    // Recorremos todas las cuentas de la bd
    // for (const cuenta of this.cuentas) {
    //   if (clave.idRequerimientoId
    // }
  }

  addSkillCandidato() {
    this.errorMessageSkill = null;
    if (this.editForm.get(['skill']).value != null && this.editForm.get(['skillDominio']).value != null && this.editForm.get(['skillCalificacion']).value != null) {
      const newSkillCandidato: ISkillCandidato = new SkillCandidato();
      newSkillCandidato.idSkillId = this.editForm.get(['skill']).value;
      const skillSelected = this.skills.find(item => item.id === newSkillCandidato.idSkillId);
      this.skillsSelected.push(skillSelected);
      newSkillCandidato.idSkillNombre = skillSelected.nombre;
      newSkillCandidato.nivelSkillId = this.editForm.get(['skillDominio']).value;
      const letdominioSkillSelected = this.dominioSkill.find(item => item.id === newSkillCandidato.nivelSkillId);
      newSkillCandidato.nivelSkillDominio = letdominioSkillSelected.dominio;
      newSkillCandidato.calificacionSkill = this.editForm.get(['skillCalificacion']).value;
      this.skillCandidatoes.push(newSkillCandidato);
      this.editForm.get(['skill']).setValue(null);
      this.editForm.get(['skillDominio']).setValue(null);
      this.editForm.get(['skillCalificacion']).setValue(null);
      this.updateSkills();
    } else {
      this.errorMessageSkill = 'Debe ingresar todos los datos';
    }
  }

  updateSkills() {
    const temp: ISkill[] = this.skills.slice(0);
    this.skillsSelected.forEach(sk => {
      const index = temp.indexOf(sk);
      if (index >= 0) {
        temp.splice(index, 1);
      }
    });
    this.skillsFilter = temp;
  }

  updateCuentasInteres() {
    const temp: ICuenta[] = this.cuentas.slice(0);
    this.cuentasIntSelected.forEach(ci => {
      const index = temp.indexOf(ci);
      if (index >= 0) {
        temp.splice(index, 1);
      }
    });
    this.cuentasInteres = temp;
  }

  updateCuentasRechazadas() {
    const temp: ICuenta[] = this.cuentas.slice(0);
    this.cuentasRechSelected.forEach(cr => {
      const index = temp.indexOf(cr);
      if (index >= 0) {
        temp.splice(index, 1);
      }
    });
    this.cuentasRechazadas = temp;
  }

  removeSkillCandidato(event: any) {
    this.errorMessageSkill = null;
    const idSkill: number = parseInt(event.srcElement.value, 10);
    const skillsCandidatoDelete: ISkillCandidato[] = this.skillCandidatoes.filter(s => s.idSkillId === idSkill);
    const skillCandidatoDelete = skillsCandidatoDelete.shift();
    const skillDel = this.skills.find(item => item.id === skillCandidatoDelete.idSkillId);
    const index = this.skillCandidatoes.indexOf(skillCandidatoDelete);
    if (index >= 0) {
      this.skillCandidatoes.splice(index, 1);
    }
    const indeSkillDel = this.skillsSelected.indexOf(skillDel);
    if (indeSkillDel >= 0) {
      this.skillsSelected.splice(indeSkillDel, 1);
    }
    this.updateSkills();
  }

  loadColonia(colonia: IColonia) {
    this.colonias.push(colonia);
    this.editForm.get(['dirColonia']).setValue(colonia.id);
    this.municipioService
      .find(colonia.municipioId)
      .pipe(
        filter((mayBeOk: HttpResponse<IMunicipio>) => mayBeOk.ok),
        map((response: HttpResponse<IMunicipio>) => response.body)
      )
      .subscribe(
        (mun: IMunicipio) => this.loadMunicipio(mun), (mun: HttpErrorResponse) => this.onError(mun.message)
      );
  }

  loadMunicipio(municipio: IMunicipio) {
    this.municipios.push(municipio);
    this.editForm.get(['dirMunicipio']).setValue(municipio.id);
    this.estadoService
      .find(municipio.estadoId)
      .pipe(
        filter((mayBeOk: HttpResponse<IEstado>) => mayBeOk.ok),
        map((response: HttpResponse<IEstado>) => response.body)
      )
      .subscribe(
        (est: IEstado) => {
          this.estados.push(est);
          this.editForm.get(['dirEstado']).setValue(est.id);
        }, (est: HttpErrorResponse) => this.onError(est.message)
      );
  }

}
