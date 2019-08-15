import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IRequerimiento, Requerimiento } from 'app/shared/model/requerimiento.model';
import { RequerimientoService } from './requerimiento.service';
import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from 'app/entities/cuenta';
import { IUser, UserService } from 'app/core';
import { IEstatusRequerimiento } from 'app/shared/model/estatus-requerimiento.model';
import { EstatusRequerimientoService } from 'app/entities/estatus-requerimiento';
import { IPrioridadReq } from 'app/shared/model/prioridad-req.model';
import { PrioridadReqService } from 'app/entities/prioridad-req';
import { ITipoSolicitud } from 'app/shared/model/tipo-solicitud.model';
import { TipoSolicitudService } from 'app/entities/tipo-solicitud';
import { ITipoIngreso } from 'app/shared/model/tipo-ingreso.model';
import { TipoIngresoService } from 'app/entities/tipo-ingreso';
import { IBaseTarifa } from 'app/shared/model/base-tarifa.model';
import { BaseTarifaService } from 'app/entities/base-tarifa';
import { IEsqContRec } from 'app/shared/model/esq-cont-rec.model';
import { EsqContRecService } from 'app/entities/esq-cont-rec';
import { IPerfil } from 'app/shared/model/perfil.model';
import { PerfilService } from 'app/entities/perfil';
import { INivelPerfil } from 'app/shared/model/nivel-perfil.model';
import { NivelPerfilService } from 'app/entities/nivel-perfil';
import { IEstReqCerrado } from 'app/shared/model/est-req-cerrado.model';
import { EstReqCerradoService } from 'app/entities/est-req-cerrado';
import { ITipoPeriodo } from 'app/shared/model/tipo-periodo.model';
import { TipoPeriodoService } from 'app/entities/tipo-periodo';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { FormControl } from '@angular/forms';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { startWith } from 'rxjs/operators';
import { MapsAPILoader, MouseEvent } from '@agm/core';
import { NgZone } from '@angular/core';
import { SkillService } from '../skill';
import { ISkill } from 'app/shared/model/skill.model';
import { ISkillRequerimiento, SkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';
import { SkillRequerimientoService } from '../skill-requerimiento';
import { SkillReqService } from '../../servicios/skill-req.service';
import {ErrorStateMatcher} from '@angular/material/core';
import {FormGroupDirective, NgForm} from '@angular/forms';
import { ALL_ITEMS } from 'app/shared';
import { AccountService, JhiLanguageHelper } from 'app/core';

  // Verificar errores en inputs
  export class MyErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
      const isSubmitted = form && form.submitted;
      return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    }
  }

@Component({
  selector: 'jhi-requerimiento-update',
  templateUrl: './requerimiento-update.component.html',
  styleUrls: [
    '../../agreg-req/agreg-req.component.scss'
  ]
})
export class RequerimientoUpdateComponent implements OnInit {

  // Verificar errores en inputs
  inputRequeridos = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);
  matcher = new MyErrorStateMatcher();
  cuentaUsuario: any;
  estReq = true;
  datos2 = true;
  datos3 = true;
  datos4 = true;
  // Cargar skills
  reqId: number;
  SkillReq: any = [];
  SkillOpc: any = [];
  SkillReq2: any = [];
  fruitInput33: any = [];
  fruitInput22: any = [];
  fruitInputt: any = [];
  fruitInput333: any = [];
  skillsOpcionalesBD: any = [];
  skillsRequeridosBD: any = [];
  skillsEscencialesBD: any = [];
  fruitInputtt: any = [];
  idre: number;
  // Skills updates
  todosSkillReq: any = [];
  actualizarReq: boolean;
  // Verificar si selecciono algun skill
  selSkillOpc: number;
  selSkillReq: number;
  selSkillEsc: number;
  // Mapa
  title = 'AGM project';
  latitude = 19.390907;
  longitude = -99.165759;
  zoom = 14;
  address: string;
  private geoCoder;
  @ViewChild('search', { static: false })
  // Mapa
  public searchElementRef: ElementRef;
  // Codigo de la pantalla
  public reqCancelado = false;
  public reemplazo = false;
  selected1 = new FormControl(0);
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  filteredFruits: Observable<string[]>;

  Skill: ISkill[];
  requeridosCtrl = new FormControl();
  FilterSkillsRequeridos: ISkill[];
  SkillRequeridosSelected: ISkill[];

  opcionalesCtrl = new FormControl();
  FilterSkillsOpcionales: ISkill[];
  SkillOpcionalesSelected: ISkill[];

  esencialesCtrl = new FormControl();
  FilterSkillsEsenciales: ISkill[];
  SkillEsencialesSelected: ISkill[];

  visible2 = true;
  selectable2 = true;
  removable2 = true;
  addOnBlur2 = true;
  separatorKeysCodes2: number[] = [ENTER, COMMA];
  fruitCtrl2 = new FormControl();
  filteredFruits2: Observable<string[]>;
  visible3 = true;
  selectable3 = true;
  removable3 = true;
  addOnBlur3 = true;
  separatorKeysCodes3: number[] = [ENTER, COMMA];
  fruitCtrl3 = new FormControl();
  filteredFruits3: Observable<string[]>;
  @ViewChild('fruitInput3', { static: false }) fruitInput3;
  @ViewChild('auto3', { static: false }) matAutocomplete3: MatAutocomplete;
  @ViewChild('fruitInput2', { static: false }) fruitInput2: ElementRef<HTMLInputElement>;
  @ViewChild('auto2', { static: false }) matAutocomplete2: MatAutocomplete;
  @ViewChild('fruitInput', { static: false }) fruitInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', { static: false }) matAutocomplete: MatAutocomplete;

  // Fin de la pantalla
  isSaving: boolean;

  cuentas: ICuenta[];

  users: IUser[];

  estatusrequerimientos: IEstatusRequerimiento[];

  prioridadreqs: IPrioridadReq[];

  tiposolicituds: ITipoSolicitud[];

  tipoingresos: ITipoIngreso[];

  basetarifas: IBaseTarifa[];

  esqcontrecs: IEsqContRec[];

  perfils: IPerfil[];

  nivelperfils: INivelPerfil[];

  estreqcerrados: IEstReqCerrado[];

  tipoperiodos: ITipoPeriodo[];

  currentDate: moment.Moment;

  editForm = this.fb.group({
    id: [],
    fechaAlda: [],
    fechaResolucion: [],
    remplazoDe: [null, [Validators.maxLength(500)]],
    vacantesSolicitadas: ['', Validators.required],
    proyecto: [null, [Validators.maxLength(200)]],
    nombreContacto: [null, [Validators.maxLength(100), Validators.required]],
    tarifaSueldoNet: ['', Validators.required],
    prestaciones: [null, [Validators.maxLength(500), Validators.required]],
    duracionAsignacion: ['', Validators.required],
    lugarTrabajo: [null, [Validators.maxLength(500), Validators.required]],
    coorLat: [null],
    coorLong: [null],
    horario: [null, [Validators.maxLength(300)]],
    informacionExamen: [null, [Validators.maxLength(500)]],
    informacionAdicional: [null, [Validators.maxLength(1000)]],
    cuentaId: ['', Validators.required],
    subCuentaId: [],
    usuarioCreadorId: [],
    usuarioAsignadoId: ['', Validators.required],
    estatusRequerimientoId: ['', Validators.required],
    prioridadId: ['', Validators.required],
    tipoSolicitudId: ['', Validators.required],
    tipoIngresoId: ['', Validators.required],
    baseTarifaId: ['', Validators.required],
    esquemaContratacionId: ['', Validators.required],
    perfilId: ['', Validators.required],
    nivelPerfilId: ['', Validators.required],
    estatusReqCanId: [],
    tipoPeriodoId: ['', Validators.required]
  });
  actualizacion: boolean;

  constructor(
    private accountService: AccountService,
    public restApi: SkillReqService,
    protected skillRequerimientoService: SkillRequerimientoService,
    protected skillService: SkillService,
    protected skillSkillReqService: SkillReqService,
    protected jhiAlertService: JhiAlertService,
    protected requerimientoService: RequerimientoService,
    protected cuentaService: CuentaService,
    protected userService: UserService,
    protected estatusRequerimientoService: EstatusRequerimientoService,
    protected prioridadReqService: PrioridadReqService,
    protected tipoSolicitudService: TipoSolicitudService,
    protected tipoIngresoService: TipoIngresoService,
    protected baseTarifaService: BaseTarifaService,
    protected esqContRecService: EsqContRecService,
    protected perfilService: PerfilService,
    protected nivelPerfilService: NivelPerfilService,
    protected estReqCerradoService: EstReqCerradoService,
    protected tipoPeriodoService: TipoPeriodoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private mapsAPILoader: MapsAPILoader,
    private ngZone: NgZone
  ) {
    // Codigo de la pantalla
    this.requeridosCtrl.valueChanges.subscribe(newValue => {
      this.FilterSkillsRequeridos = this._filter(newValue);
    });
    // Inicio Segundo chip autocompletable
    this.opcionalesCtrl.valueChanges.subscribe(newValue => {
      this.FilterSkillsOpcionales = this._filter2(newValue);
    });
    // Fin Segundo chip autocompletable
    // Inicio Segundo chip autocompletable
    this.esencialesCtrl.valueChanges.subscribe(newValue => {
      this.FilterSkillsEsenciales = this._filter3(newValue);
    });
    // Fin Segundo chip autocompletable
    // Fin de la pantalla
  }

  // Codigo de la pantalla
  siguiente() {
    this.selected1.setValue(1);
  }
  selected(event: MatAutocompleteSelectedEvent): void {
    const SkillsSelectedRequerido: ISkill[] = this.Skill.filter( s => (s.id === event.option.value));
    const SkillRequeridosSelectedRequerido: ISkill = SkillsSelectedRequerido.shift();
    this.SkillRequeridosSelected.push(SkillRequeridosSelectedRequerido);
    this.fruitInput.nativeElement.value = '';
    this.requeridosCtrl.setValue(null);
    this.fruitInputt.push(SkillRequeridosSelectedRequerido);
  }
  remove(requerido: ISkill): void {
    const index = this.SkillRequeridosSelected.indexOf(requerido);
    if (index >= 0) {
      this.SkillRequeridosSelected.splice(index, 1);
      this.fruitInputt.splice(index, 1);
    }
    this.selSkillReq ++ ;
  }
  add(event: MatChipInputEvent): void {
    // No se permite agregar elementos que no esten en la base de datos
  }
  selected2(event: MatAutocompleteSelectedEvent): void {
    const SkillsSelectedOpcional: ISkill[] = this.Skill.filter( s => (s.id === event.option.value));
    const SkillOpcionalesSelectedRequerido: ISkill = SkillsSelectedOpcional.shift();
    this.SkillOpcionalesSelected.push(SkillOpcionalesSelectedRequerido);
    this.fruitInput2.nativeElement.value = '';
    this.opcionalesCtrl.setValue(null);
    this.fruitInput22.push(SkillOpcionalesSelectedRequerido);
  }
  private _filter2(value: string): ISkill[] {
    const tempOpcionales: ISkill[] = this.Skill.slice(0);
    this.SkillOpcionalesSelected.forEach(opcional => {
      const index = tempOpcionales.indexOf(opcional);
      if (index >= 0) {
        tempOpcionales.splice(index, 1);
      }
    });
    return tempOpcionales.filter( s => new RegExp(value, 'gi').test(s.nombre));
  }
  remove2(opcionales: ISkill): void {
    const index = this.SkillOpcionalesSelected.indexOf(opcionales);
    if (index >= 0) {
      this.SkillOpcionalesSelected.splice(index, 1);
      this.fruitInput22.splice(index, 1);
    }
  }
  add2(event: MatChipInputEvent): void {
    // No se permite agregar elementos que no esten en la base de datos
  }
  selected3(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    const SkillsSelectedEsencial: ISkill[] = this.Skill.filter( s => (s.id === event.option.value));
    const SkillEsencialesSelectedEscencial: ISkill = SkillsSelectedEsencial.shift();
    this.SkillEsencialesSelected.push(SkillEsencialesSelectedEscencial);
    this.fruitInput3.nativeElement.value = '';
    this.esencialesCtrl.setValue(null);
    this.fruitInput33.push(SkillEsencialesSelectedEscencial);
  }
  _filter3(value: string): ISkill[] {
    const tempEscenciales: ISkill[] = this.Skill.slice(0);
    this.SkillEsencialesSelected.forEach(esencial => {
      const index = tempEscenciales.indexOf(esencial);
      if (index >= 0) {
        tempEscenciales.splice(index, 1);
      }
    });
    return tempEscenciales.filter( s => new RegExp(value, 'gi').test(s.nombre));
  }
  remove3(escencial: ISkill): void {
    const index = this.SkillEsencialesSelected.indexOf(escencial);
    if (index >= 0) {
      this.SkillEsencialesSelected.splice(index, 1);
      this.fruitInput33.splice(index, 1);
    }
    // Fin primer chip autocompletable
  }
  add3(event: MatChipInputEvent): void {
    // No se permite agregar elementos que no esten en la base de datos
  }
  // Fin Codigo de la pantalla

  ngOnInit() {
    this.accountService.identity().then(account => {
      this.cuentaUsuario = account;
    });
    // load Places Autocomplete

    this.SkillRequeridosSelected = [];
    this.SkillOpcionalesSelected = [];
    this.SkillEsencialesSelected = [];
    this.mapsAPILoader.load().then(() => {
      this.setCurrentLocation();
      this.geoCoder = new google.maps.Geocoder;
      const autocomplete = new google.maps.places.Autocomplete(this.searchElementRef.nativeElement, {
        types: ['address']
      });
      autocomplete.addListener('place_changed', () => {
        this.ngZone.run(() => {
          // get the place result
          const place: google.maps.places.PlaceResult = autocomplete.getPlace();
          // verify result
          if (place.geometry === undefined || place.geometry === null) {
            return;
          }
          // set latitude, longitude and zoom
          this.latitude = place.geometry.location.lat();
          this.longitude = place.geometry.location.lng();
          this.zoom = 50;
        });
      });
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
        (res: ISkill[]) => this.setSkills(res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
      this.skillRequerimientoService
        .query({
          size: 999999
        })
        .pipe(
          filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
          map((response: HttpResponse<IUser[]>) => response.body)
        )
        .subscribe(
          (res: ISkillRequerimiento[]) => this.setSkillsReq(res),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
      if (requerimiento.fechaAlta ) {
        this.currentDate = requerimiento.fechaAlta;
      } else {
        this.currentDate = moment();
      }
      this.updateForm(requerimiento);
    });
    this.cuentaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICuenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICuenta[]>) => response.body)
      )
      .subscribe((res: ICuenta[]) => (this.cuentas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.estatusRequerimientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstatusRequerimiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstatusRequerimiento[]>) => response.body)
      )
      .subscribe(
        (res: IEstatusRequerimiento[]) => (this.estatusrequerimientos = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.prioridadReqService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPrioridadReq[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPrioridadReq[]>) => response.body)
      )
      .subscribe((res: IPrioridadReq[]) => (this.prioridadreqs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoSolicitudService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoSolicitud[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoSolicitud[]>) => response.body)
      )
      .subscribe((res: ITipoSolicitud[]) => (this.tiposolicituds = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoIngresoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoIngreso[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoIngreso[]>) => response.body)
      )
      .subscribe((res: ITipoIngreso[]) => (this.tipoingresos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.baseTarifaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IBaseTarifa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IBaseTarifa[]>) => response.body)
      )
      .subscribe((res: IBaseTarifa[]) => (this.basetarifas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.esqContRecService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEsqContRec[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEsqContRec[]>) => response.body)
      )
      .subscribe((res: IEsqContRec[]) => (this.esqcontrecs = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    this.estReqCerradoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEstReqCerrado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstReqCerrado[]>) => response.body)
      )
      .subscribe((res: IEstReqCerrado[]) => (this.estreqcerrados = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoPeriodoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoPeriodo[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoPeriodo[]>) => response.body)
      )
      .subscribe((res: ITipoPeriodo[]) => (this.tipoperiodos = res), (res: HttpErrorResponse) => this.onError(res.message));
      if (this.editForm.get(['id']).value != null) {
        this.actualizarReq = true;
      } else {
        this.actualizarReq = false;
      }
      // console.log('------------------------');
      // console.log(this.actualizarReq);
      // console.log('------------------------');
     console.log('-----------', this.reqCancelado);
  }

  updateForm(requerimiento: IRequerimiento) {
    this.editForm.patchValue({
      id: requerimiento.id,
      // fechaAlda: requerimiento.fechaAlda,
      // fechaResolucion: requerimiento.fechaResolucion,
      remplazoDe: requerimiento.remplazoDe,
      vacantesSolicitadas: requerimiento.vacantesSolicitadas,
      proyecto: requerimiento.proyecto,
      nombreContacto: requerimiento.nombreContacto,
      tarifaSueldoNet: requerimiento.tarifaSueldoNet,
      prestaciones: requerimiento.prestaciones,
      duracionAsignacion: requerimiento.duracionAsignacion,
      lugarTrabajo: requerimiento.lugarTrabajo,
      coorLat: requerimiento.coorLat,
      coorLong: requerimiento.coorLong,
      horario: requerimiento.horario,
      informacionExamen: requerimiento.informacionExamen,
      informacionAdicional: requerimiento.informacionAdicional,
      cuentaId: requerimiento.cuentaId,
      subCuentaId: requerimiento.subCuentaId,
      usuarioCreadorId: requerimiento.usuarioCreadorId,
      usuarioAsignadoId: requerimiento.usuarioAsignadoId,
      estatusRequerimientoId: requerimiento.estatusRequerimientoId,
      prioridadId: requerimiento.prioridadId,
      tipoSolicitudId: requerimiento.tipoSolicitudId,
      tipoIngresoId: requerimiento.tipoIngresoId,
      baseTarifaId: requerimiento.baseTarifaId,
      esquemaContratacionId: requerimiento.esquemaContratacionId,
      perfilId: requerimiento.perfilId,
      nivelPerfilId: requerimiento.nivelPerfilId,
      estatusReqCanId: requerimiento.estatusReqCanId,
      tipoPeriodoId: requerimiento.tipoPeriodoId
    });
    if (requerimiento.tipoIngresoTipo === 'Reemplazo') {
      this.reemplazo = true;
    }
    if (requerimiento.estatusRequerimientoEstatus === 'Cerrado') {
      this.reqCancelado = true;
    }
  }

  previousState() {
    window.history.back();
  }
  verificacion() {

  }
  save() {
    this.isSaving = true;
    const requerimiento = this.createFromForm();
    // Si actualiza
    if (this.actualizarReq === true) {
      console.log('esta actualizando');
      if (this.editForm.get(['vacantesSolicitadas']).value !== undefined && this.editForm.get(['vacantesSolicitadas']).value !== null &&
      this.editForm.get(['nombreContacto']).value !== undefined && this.editForm.get(['nombreContacto']).value !== null &&
      this.editForm.get(['tarifaSueldoNet']).value !== undefined && this.editForm.get(['tarifaSueldoNet']).value !== null &&
      this.editForm.get(['prestaciones']).value !== undefined && this.editForm.get(['prestaciones']).value  !== null &&
      this.editForm.get(['duracionAsignacion']).value !== undefined && this.editForm.get(['duracionAsignacion']).value !== null &&
      this.editForm.get(['lugarTrabajo']).value !== undefined && this.editForm.get(['lugarTrabajo']).value !== null &&
      this.editForm.get(['cuentaId']).value !== undefined && this.editForm.get(['cuentaId']).value !== null &&
      this.editForm.get(['usuarioAsignadoId']).value !== undefined && this.editForm.get(['usuarioAsignadoId']).value !== null &&
      this.editForm.get(['prioridadId']).value !== undefined &&  this.editForm.get(['prioridadId']).value !== null &&
      this.editForm.get(['esquemaContratacionId']).value !== undefined && this.editForm.get(['esquemaContratacionId']).value !== null &&
      this.editForm.get(['baseTarifaId']).value !== undefined && this.editForm.get(['baseTarifaId']).value !== null &&
      this.editForm.get(['perfilId']).value !== undefined && this.editForm.get(['perfilId']).value !== null &&
      this.editForm.get(['nivelPerfilId']).value !== undefined && this.editForm.get(['nivelPerfilId']).value !== null &&
      this.editForm.get(['tipoPeriodoId']).value !== undefined && this.editForm.get(['tipoPeriodoId']).value !== null)  {
        let reqCerrado = false;
        let reqReemplazo = false;
        // Estatus del requerimiento
        if (this.reqCancelado === true && requerimiento.estatusReqCanId === null ) {
          console.log('El requerimiento es cerrado y no tiene un motivo');
          this.editForm.get(['estatusReqCanId']).setErrors({'incorrect': true});
          this.selected1.setValue(0);
        } else if (this.reqCancelado === true && requerimiento.estatusReqCanId !== null ) {
          console.log('El requerimiento es cerrado y tiene un motivo');
          reqCerrado = true;
        } else {
          console.log('El requerimiento no es cerrado');
        }
        // Tipo de ingreso
        if (this.reemplazo === true && requerimiento.remplazoDe === null ) {
          console.log('El ingreso es reemplazo y no tiene un motivo');
          this.editForm.get(['remplazoDe']).setErrors({'incorrect': true});
          this.selected1.setValue(0);
        } else if (this.reemplazo === true && requerimiento.remplazoDe !== null ) {
          console.log('El ingreso es reemplazo y tiene un motivo');
          reqReemplazo = true;
        } else {
          this.editForm.get(['remplazoDe']).reset();
        }
        console.log(this.reqCancelado, reqCerrado, this.reemplazo, reqReemplazo);
        // if (this.reqCancelado === true && requerimiento.estatusReqCanId === null ) {
        //   console.log('El requerimiento es cerrado y no tiene un motivo');
        //   this.editForm.get(['estatusReqCanId']).setErrors({'incorrect': true});
        // } else if (this.reqCancelado === true && requerimiento.estatusReqCanId !== null ) {
        //   console.log('El requerimiento es cerrado y tiene un motivo');
        //   reqCerrado = true;
        // } else {
        //   this.selected1.setValue(0);
        // }

        // if (this.reemplazo === true && requerimiento.remplazoDe !== null) {
        //   console.log('El requerimiento es reemplazo y tiene un reemplazo, nada mas que hacer');
        //   console.log(this.editForm.get(['remplazoDe']).value);
        //   reqReemplazo = true;
        // } else {
        //   console.log('El requerimiento es reemplazo y no tiene un reemplazo');
        //   this.editForm.get(['remplazoDe']).setErrors({'incorrect': true});
        //   this.selected1.setValue(0);
        // }
        // // console.log(this.editForm.get(['estatusRequerimientoId']).value);
        // if (this.editForm.get(['tipoIngresoId']).value === 2 &&  this.editForm.get(['remplazoDe']).value === '') {
        //   this.editForm.get(['remplazoDe']).setErrors({'incorrect': true});
        //   this.selected1.setValue(0);
        // }
        // if (this.editForm.get(['estatusRequerimientoId']).value === 2 &&  this.editForm.get(['estatusReqCanId']).value === '') {
        //   this.editForm.get(['estatusReqCanId']).setErrors({'incorrect': true});
        //   this.selected1.setValue(0);
        // }
        // --------------------------------------------
        if (this.reqCancelado === true && reqCerrado === true && this.reemplazo === true && reqReemplazo === true || this.reqCancelado === false && reqCerrado === false && this.reemplazo === false && reqReemplazo === false || this.reqCancelado === true && reqCerrado === true && this.reemplazo === false && reqReemplazo === false  || this.reqCancelado === false && reqCerrado === false && this.reemplazo === true && reqReemplazo === true ) {
          if (requerimiento.id !== undefined) {
            this.subscribeToSaveResponse(this.requerimientoService.update(requerimiento));
          } else {
            this.subscribeToSaveResponse(this.requerimientoService.create(requerimiento));
          }
        }
      } else {
        console.log('No tiene datos en actualizar');
        this.selected1.setValue(0);
        console.log(this.editForm.get(['tipoIngresoId']).value, this.editForm.get(['remplazoDe']).value);
        }
    } else {
      // Si agrega
      console.log('esta agregando');
      if (requerimiento.vacantesSolicitadas !== undefined && requerimiento.estatusRequerimientoId !== undefined
        && requerimiento.prioridadId !== undefined && requerimiento.tipoSolicitudId !== undefined &&
        requerimiento.tipoIngresoId !== undefined && requerimiento.vacantesSolicitadas !== undefined && requerimiento.cuentaId !== undefined
        && requerimiento.nombreContacto !== undefined && requerimiento.tarifaSueldoNet !== undefined && requerimiento.baseTarifaId !== undefined
        && requerimiento.esquemaContratacionId !== undefined && requerimiento.prestaciones !== undefined)  {
        let reqCerrado = false;
        let reqReemplazo = false;
        if (this.reqCancelado === true && requerimiento.estatusReqCanId !== undefined ) {
          console.log('El requerimiento es cerrado y tiene un motivo');
          reqCerrado = true;
        } else {
          console.log('El requerimiento es cerrado y no tiene un motivo');
          this.editForm.get(['estatusReqCanId']).setErrors({'incorrect': true});
          this.selected1.setValue(0);
        }
        if (this.reemplazo === true && requerimiento.remplazoDe !== undefined) {
          console.log('El requerimiento es reemplazo y tiene un reemplazo');
          reqReemplazo = true;
        } else {
          console.log('El requerimiento es reemplazo y no tiene un reemplazo');
          this.editForm.get(['remplazoDe']).setErrors({'incorrect': true});
          this.selected1.setValue(0);
        }
        if (this.reqCancelado === true && reqCerrado === true && this.reemplazo === true && reqReemplazo === true || this.reqCancelado === false && reqCerrado === false && this.reemplazo === false && reqReemplazo === false ) {
          console.log('tiene datos');
          if (requerimiento.id !== undefined) {
            this.subscribeToSaveResponse(this.requerimientoService.update(requerimiento));
          } else {
            this.subscribeToSaveResponse(this.requerimientoService.create(requerimiento));
          }
        }
      } else {
        console.log('No tiene datos en agregar');
        this.selected1.setValue(0);
        this.estReq = false;
        this.datos2 = false;
        this.datos3 = false;
      }
    }
  }

  private createFromForm(): IRequerimiento {
    return {
      ...new Requerimiento(),
      id: this.editForm.get(['id']).value,
      fechaAlda: this.currentDate,
      remplazoDe: this.editForm.get(['remplazoDe']).value,
      vacantesSolicitadas: this.editForm.get(['vacantesSolicitadas']).value,
      proyecto: this.editForm.get(['proyecto']).value,
      nombreContacto: this.editForm.get(['nombreContacto']).value,
      tarifaSueldoNet: this.editForm.get(['tarifaSueldoNet']).value,
      prestaciones: this.editForm.get(['prestaciones']).value,
      duracionAsignacion: this.editForm.get(['duracionAsignacion']).value,
      lugarTrabajo: this.editForm.get(['lugarTrabajo']).value,
      coorLat: this.latitude,
      coorLong: this.longitude,
      horario: this.editForm.get(['horario']).value,
      informacionExamen: this.editForm.get(['informacionExamen']).value,
      informacionAdicional: this.editForm.get(['informacionAdicional']).value,
      cuentaId: this.editForm.get(['cuentaId']).value,
      subCuentaId: this.editForm.get(['subCuentaId']).value,
      usuarioCreadorId: this.cuentaUsuario.id,
      usuarioAsignadoId: this.editForm.get(['usuarioAsignadoId']).value,
      estatusRequerimientoId: this.editForm.get(['estatusRequerimientoId']).value,
      prioridadId: this.editForm.get(['prioridadId']).value,
      tipoSolicitudId: this.editForm.get(['tipoSolicitudId']).value,
      tipoIngresoId: this.editForm.get(['tipoIngresoId']).value,
      baseTarifaId: this.editForm.get(['baseTarifaId']).value,
      esquemaContratacionId: this.editForm.get(['esquemaContratacionId']).value,
      perfilId: this.editForm.get(['perfilId']).value,
      nivelPerfilId: this.editForm.get(['nivelPerfilId']).value,
      estatusReqCanId: this.editForm.get(['estatusReqCanId']).value,
      tipoPeriodoId: this.editForm.get(['tipoPeriodoId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequerimiento>>) {
    result.subscribe(r => this.onSaveSuccess(r), () => this.onSaveError());
  }

  protected subscribeToSaveResponseSkills(result: Observable<HttpResponse<any>>) {
    result.subscribe(r => this.onSaveSuccessFinal(r), () => this.onSaveError());
  }

  protected onSaveSuccessFinal(r: HttpResponse<any>) {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveSuccess(r: HttpResponse<IRequerimiento>) {
    const idRequerimiento = r.body.id;
    const addTempReqSkill: any[] = [];
    if (this.actualizarReq !== true) {
      // Si la pantalla es de registrar un nuevo requerimiento, debe de guardar sin condiciones los skills
      this.SkillRequeridosSelected.forEach(requeridos => {
        const tempAddRequerido = new SkillRequerimiento();
        tempAddRequerido.idRequerimientoId = idRequerimiento;
        tempAddRequerido.idSkillId = requeridos.id;
        tempAddRequerido.tipoSkillId = 2;
        addTempReqSkill.push(tempAddRequerido);
      });
      this.SkillOpcionalesSelected.forEach(opcionals => {
        const tempAddOpcional = new SkillRequerimiento();
        tempAddOpcional.idRequerimientoId = idRequerimiento;
        tempAddOpcional.idSkillId = opcionals.id;
        tempAddOpcional.tipoSkillId = 3;
        addTempReqSkill.push(tempAddOpcional);
      });
      this.SkillEsencialesSelected.forEach(esencials => {
        const tempAddEsencial = new SkillRequerimiento();
        tempAddEsencial.idRequerimientoId = idRequerimiento;
        tempAddEsencial.idSkillId = esencials.id;
        tempAddEsencial.tipoSkillId = 1;
        addTempReqSkill.push(tempAddEsencial);
      });
    } else {
      console.log('Es la pantalla de actualizar requerimiento');
      // Si la pantalla es de actualizar requerimiento, debe de verificar los skills
      // Verificamos si hay diferencias en el input de skills opcionales
      const opcional = this.fruitInput22.filter(item =>  this.skillsOpcionalesBD.indexOf(item) < 0);
      console.log(this.fruitInput22);
      console.log(this.skillsOpcionalesBD);
      console.log(opcional);
      if (opcional.length > 0 || opcional.length <= 0 ) {
        // Si hay diferencias borramos todo
          // console.log('Si hubo cambios');
          // console.log(this.skillsOpcionalesBD);
          this.skillsOpcionalesBD.forEach(element => {
            this.buscaryBorrarSkillsOpcionales(element.id);
          });
          // console.log(this.SkillOpcionalesSelected);
        this.SkillOpcionalesSelected.forEach(opcionall => {
          const tempAddOpcional = new SkillRequerimiento();
          tempAddOpcional.idRequerimientoId = idRequerimiento;
          tempAddOpcional.idSkillId = opcionall.id;
          tempAddOpcional.tipoSkillId = 3;
          addTempReqSkill.push(tempAddOpcional);
        });
        console.log('Se borraron los skills solo de este requerimiento');
        console.log('Se insertaron los skills de acueredp a los valores finales del input');
      } else {
        console.log('No hubo cambios en opcionales');
      }
      const requeridos = this.fruitInputt.filter(item =>  this.skillsRequeridosBD.indexOf(item) < 0);
      console.log(this.fruitInputt);
      console.log(this.skillsRequeridosBD);
      console.log(requeridos);
      if (requeridos.length > 0 || requeridos.length <= 0 ) {
        this.skillsRequeridosBD.forEach(element => {
          this.buscaryBorrarSkillsRequeridos(element.id);
        });
        this.SkillRequeridosSelected.forEach(sr => {
          const tempAddRequerido = new SkillRequerimiento();
          tempAddRequerido.idRequerimientoId = idRequerimiento;
          tempAddRequerido.idSkillId = sr.id;
          tempAddRequerido.tipoSkillId = 2;
          addTempReqSkill.push(tempAddRequerido);
        });
      } else {
        console.log('No hubo cambios en requeridos');
      }
      const escencial = this.fruitInput33.filter(item =>  this.skillsEscencialesBD.indexOf(item) < 0);
      console.log(this.fruitInput33);
      console.log(this.skillsEscencialesBD);
      console.log(escencial);
      if (escencial.length > 0 || escencial.length <= 0 ) {
        this.skillsEscencialesBD.forEach(element => {
          this.buscaryBorrarSkillsEscenciales(element.id);
        });
        this.SkillEsencialesSelected.forEach(esencial => {
          const tempAddEsencial = new SkillRequerimiento();
          tempAddEsencial.idRequerimientoId = idRequerimiento;
          tempAddEsencial.idSkillId = esencial.id;
          tempAddEsencial.tipoSkillId = 1;
          addTempReqSkill.push(tempAddEsencial);
        });
      } else {
        console.log('No hubo cambios en escenciales');
      }
    }
    // ---------------------------------------------------
    // ------------------------------------------------------
    // ------------------------------------------------------
    const updateKillReq = { lista: addTempReqSkill };
    this.subscribeToSaveResponseSkills(this.skillRequerimientoService.patch(updateKillReq));
  }

  protected onSaveError() {
    this.selected1.setValue(0);
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCuentaById(index: number, item: ICuenta) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackEstatusRequerimientoById(index: number, item: IEstatusRequerimiento) {
    return item.id;
  }

  trackPrioridadReqById(index: number, item: IPrioridadReq) {
    return item.id;
  }

  trackTipoSolicitudById(index: number, item: ITipoSolicitud) {
    return item.id;
  }

  trackTipoIngresoById(index: number, item: ITipoIngreso) {
    return item.id;
  }

  trackBaseTarifaById(index: number, item: IBaseTarifa) {
    return item.id;
  }

  trackEsqContRecById(index: number, item: IEsqContRec) {
    return item.id;
  }

  trackPerfilById(index: number, item: IPerfil) {
    return item.id;
  }

  trackNivelPerfilById(index: number, item: INivelPerfil) {
    return item.id;
  }

  trackEstReqCerradoById(index: number, item: IEstReqCerrado) {
    return item.id;
  }

  trackTipoPeriodoById(index: number, item: ITipoPeriodo) {
    return item.id;
  }

  trackSkillById(index: number, item: ICuenta) {
    return item.id;
  }
  private _filter(value: string): ISkill[] {
    const temp: ISkill[] = this.Skill.slice(0);
    this.SkillRequeridosSelected.forEach(requerido => {
      const index = temp.indexOf(requerido);
      if (index >= 0) {
        temp.splice(index, 1);
      }
    });
    return temp.filter( s => new RegExp(value, 'gi').test(s.nombre));
  }
  verificarReqEstatus(status: string) {
    if (status === 'Cerrado') {
      this.reqCancelado = true;
      this.estReq = true;
    } else {
      this.reqCancelado = false;
      this.estReq = true;
      this.editForm.get(['estatusReqCanId']).reset();
    }
  }
  verificarSolicitud(status: string) {
    this.datos2 = true;
  }
  verificarReemplazo(status: string) {
    if (status === 'Reemplazo') {
      this.reemplazo = true;
    } else {
      this.reemplazo = false;
      this.editForm.get(['remplazoDe']).reset();
    }
    this.datos3 = true;
    return this.reemplazo;
  }
  // Get Current Location Coordinates
  private setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(position => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
        this.zoom = 50;
        this.getAddress(this.latitude, this.longitude);
      });
    }
  }
  markerDragEnd($event: MouseEvent) {
    this.latitude = $event.coords.lat;
    this.longitude = $event.coords.lng;
    this.getAddress(this.latitude, this.longitude);
    const clickBot = document.querySelector('#coorLat');
    alert(clickBot);
  }
  getAddress(latitude: any, longitude: any) {
    this.geoCoder.geocode({ 'location': { lat: latitude, lng: longitude } }, (results, status) => {
      if (status === 'OK') {
        if (results[0]) {
          this.zoom = 50;
          this.address = results[0].formatted_address;
        } else {
          window.alert('No hay resultados disponibles');
        }
      } else {
        window.alert('La geolocalizacion fallo: ' + status);
      }

    });
  }
  setSkills(res: ISkill[]) {
    this.Skill = res;
    this.FilterSkillsRequeridos = res;
    this.FilterSkillsOpcionales = res;
    this.FilterSkillsEsenciales = res;
  }
  setSkillsReq(res: ISkillRequerimiento[]) {
    this.SkillReq = res;
    console.log('todos los skill');
    console.log(this.SkillReq);
    for (const clave of this.SkillReq) {
      if ( clave.idRequerimientoId === this.editForm.get(['id']).value) {
          this.todosSkillReq.push(clave);
        if ( clave.tipoSkillId === 1) {
          // console.log('Es escencial' + clave.id);
          const SkillsSelectedEsencial: ISkill[] = this.Skill.filter( s => (s.id === clave.idSkillId));
          const SkillEsencialesSelectedEscencial: ISkill = SkillsSelectedEsencial.shift();
          this.SkillEsencialesSelected.push(SkillEsencialesSelectedEscencial);
          this.fruitInput3.nativeElement.value = '';
          this.esencialesCtrl.setValue(null);
          const SkillsSelectedEsencial2: ISkill[] = this.Skill.filter( s => (s.id === clave.idSkillId));
          const SkillEsencialesSelectedEscencial2: ISkill = SkillsSelectedEsencial2.shift();
          this.fruitInput33.push(SkillEsencialesSelectedEscencial2);
          this.skillsEscencialesBD.push(SkillEsencialesSelectedEscencial2);
        } else if ( clave.tipoSkillId === 2) {
          // console.log('Es requerido' + clave.id);
          const SkillsSelectedRequerido: ISkill[] = this.Skill.filter( s => (s.id === clave.idSkillId));
          const SkillRequeridosSelectedRequerido: ISkill = SkillsSelectedRequerido.shift();
          this.SkillRequeridosSelected.push(SkillRequeridosSelectedRequerido);
          this.fruitInput.nativeElement.value = '';
          this.requeridosCtrl.setValue(null);
          const SkillsSelectedRequerido2: ISkill[] = this.Skill.filter( s => (s.id === clave.idSkillId));
          const SkillRequeridosSelectedRequerido2: ISkill = SkillsSelectedRequerido2.shift();
          this.fruitInputt.push(SkillRequeridosSelectedRequerido2);
          this.skillsRequeridosBD.push(SkillRequeridosSelectedRequerido2);
        } else if ( clave.tipoSkillId === 3) {
          // console.log('Es opcional' + clave.id);
          const SkillsSelectedOpcional: ISkill[] = this.Skill.filter( s => (s.id === clave.idSkillId));
          const SkillOpcionalesSelectedRequerido: ISkill = SkillsSelectedOpcional.shift();
          this.SkillOpcionalesSelected.push(SkillOpcionalesSelectedRequerido);
          this.fruitInput2.nativeElement.value = '';
          this.opcionalesCtrl.setValue(null);
          const SkillsSelectedOpcional2: ISkill[] = this.Skill.filter( s => (s.id === clave.idSkillId));
          const SkillOpcionalesSelectedRequerido2: ISkill = SkillsSelectedOpcional2.shift();
          this.fruitInput22.push(SkillOpcionalesSelectedRequerido2);
          this.skillsOpcionalesBD.push(SkillOpcionalesSelectedRequerido2);
        } else {
          console.log('Error');
        }
      }
    }
    // console.log('input de escenciales');
    // console.log(this.fruitInput33);
    // console.log('input de opcionales');
    // console.log(this.fruitInput22);
    // console.log('input de requeridos');
    // console.log(this.fruitInputt);
  }

  buscaryBorrarSkillsOpcionales(id) {
    for (const clave of this.SkillReq) {
      if ( clave.idRequerimientoId === this.editForm.get(['id']).value) {
        if ( clave.idSkillId === id && clave.tipoSkillId === 3) {
          // console.log('Se busco entre todos los skills, el skill requerimiento opcional y es: ');
          // console.log(clave);
          this.skillRequerimientoService.delete(clave.id).subscribe(response => {
          });
        } else {
          console.log('Error');
        }
      }
    }
  }
  buscaryBorrarSkillsRequeridos(id) {
    for (const clave of this.SkillReq) {
      if ( clave.idRequerimientoId === this.editForm.get(['id']).value) {
        if ( clave.idSkillId === id && clave.tipoSkillId === 2) {
          // console.log('Se busco entre todos los skills, el skill requerimiento  requerido y es: ');
          // console.log(clave);
          this.skillRequerimientoService.delete(clave.id).subscribe(response => {
          });
        } else {
          console.log('Error');
        }
      }
    }
  }
  buscaryBorrarSkillsEscenciales(id) {
    for (const clave of this.SkillReq) {
      if ( clave.idRequerimientoId === this.editForm.get(['id']).value) {
        if ( clave.idSkillId === id && clave.tipoSkillId === 1) {
          // console.log('Se busco entre todos los skills, el skill requerimiento escencial y es: ');
          // console.log(clave);
          this.skillRequerimientoService.delete(clave.id).subscribe(response => {
          });
        } else {
          console.log('Error');
        }
      }
    }
  }

}
