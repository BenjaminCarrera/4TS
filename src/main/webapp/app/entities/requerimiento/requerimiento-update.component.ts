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
import { SkillApi } from '../../servicios/skill-api';

@Component({
  selector: 'jhi-requerimiento-update',
  templateUrl: './requerimiento-update.component.html',
  styleUrls: [
    '../../agreg-req/agreg-req.component.scss'
  ]
})
export class RequerimientoUpdateComponent implements OnInit {
  // Mapa
  title = 'AGM project';
  latitude: number;
  longitude: number;
  zoom: number;
  address: string;
  private geoCoder;
  @ViewChild('search', {static: false})
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
  fruitCtrl = new FormControl();
  filteredFruits: Observable<string[]>;
  Skill: any = [];
  fruits: string[] = ['PHP'];
  allFruits: any = [];
  visible2 = true;
  selectable2 = true;
  removable2 = true;
  addOnBlur2 = true;
  separatorKeysCodes2: number[] = [ENTER, COMMA];
  fruitCtrl2 = new FormControl();
  filteredFruits2: Observable<string[]>;
  fruits2: string[] = ['Java!'];
  allFruits2: string[] = ['PHP', 'Java', 'Angular', 'Python'];
  visible3 = true;
  selectable3 = true;
  removable3 = true;
  addOnBlur3 = true;
  separatorKeysCodes3: number[] = [ENTER, COMMA];
  fruitCtrl3 = new FormControl();
  filteredFruits3: Observable<string[]>;
  fruits3: string[] = ['Python!'];
  allFruits3: string[] = ['PHP', 'Java', 'Angular', 'Python'];
  @ViewChild('fruitInput3', {static: false}) fruitInput3: ElementRef<HTMLInputElement>;
  @ViewChild('auto3', {static: false}) matAutocomplete3: MatAutocomplete;
  @ViewChild('fruitInput2', {static: false}) fruitInput2: ElementRef<HTMLInputElement>;
  @ViewChild('auto2', {static: false}) matAutocomplete2: MatAutocomplete;
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

  editForm = this.fb.group({
    id: [],
    fechaAlda: [null, []],
    fechaResolucion: [],
    remplazoDe: [null, [Validators.maxLength(500)]],
    vacantesSolicitadas: [],
    proyecto: [null, [Validators.maxLength(200)]],
    nombreContacto: [null, [Validators.maxLength(100)]],
    tarifaSueldoNet: [],
    prestaciones: [null, [Validators.maxLength(500)]],
    duracionAsignacion: [],
    lugarTrabajo: [null, [Validators.maxLength(500)]],
    coorLat: [null],
    coorLong: [null],
    horario: [null, [Validators.maxLength(300)]],
    informacionExamen: [null, [Validators.maxLength(500)]],
    informacionAdicional: [null, [Validators.maxLength(1000)]],
    cuentaId: [],
    subCuentaId: [],
    usuarioCreadorId: [],
    usuarioAsignadoId: [],
    estatusRequerimientoId: [],
    prioridadId: [],
    tipoSolicitudId: [],
    tipoIngresoId: [],
    baseTarifaId: [],
    esquemaContratacionId: [],
    perfilId: [],
    nivelPerfilId: [],
    estatusReqCanId: [],
    tipoPeriodoId: []
  });

  constructor(
    public restApi: SkillApi,
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
    }

    // Codigo de la pantalla
    siguiente() {
      this.selected1.setValue(1);
    }
  selected(event: MatAutocompleteSelectedEvent): void {
    this.fruits.push(event.option.viewValue);
    this.fruitInput.nativeElement.value = '';
    this.fruitCtrl.setValue(null);
  }
  remove(fruit: string): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }
  add(event: MatChipInputEvent): void {
    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl.setValue(null);
    }
    console.log(this.fruits);
  }
  selected2(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    this.fruits2.push(event.option.viewValue);
    this.fruitInput2.nativeElement.value = '';
    this.fruitCtrl2.setValue(null);
    }
  remove2(fruit2: string): void {
    // Inicio primer chip autocompletable
    const index2 = this.fruits2.indexOf(fruit2);
    if (index2 >= 0) {
      this.fruits2.splice(index2, 1);
    }
    // Fin primer chip autocompletable
  }
  add2(event: MatChipInputEvent): void {
    // Inicio primer chip autocompletable

    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete2.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits2.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl2.setValue(null);
    }
    // Fin primer chip autocompletable
  }
  selected3(event: MatAutocompleteSelectedEvent): void {
    // Inicio primer chip autocompletable
    this.fruits3.push(event.option.viewValue);
    this.fruitInput3.nativeElement.value = '';
    this.fruitCtrl3.setValue(null);
    }
  remove3(fruit3: string): void {
    // Inicio primer chip autocompletable
    const index3 = this.fruits3.indexOf(fruit3);
    if (index3 >= 0) {
      this.fruits3.splice(index3, 1);
    }
    // Fin primer chip autocompletable
  }
  add3(event: MatChipInputEvent): void {
    // Inicio primer chip autocompletable

    // Add fruit only when MatAutocomplete is not open
    // To make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete3.isOpen) {
      const input = event.input;
      const value = event.value;

      // Add our fruit
      if ((value || '').trim()) {
        this.fruits3.push(value.trim());
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.fruitCtrl3.setValue(null);
    }
    // Fin primer chip autocompletable
  }
  // Fin Codigo de la pantalla

  ngOnInit() {
   // load Places Autocomplete
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
  this.getSkills();
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ requerimiento }) => {
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
  }

  updateForm(requerimiento: IRequerimiento) {
    this.editForm.patchValue({
      id: requerimiento.id,
      fechaAlda: requerimiento.fechaAlda != null ? requerimiento.fechaAlda.format(DATE_TIME_FORMAT) : null,
      fechaResolucion: requerimiento.fechaResolucion != null ? requerimiento.fechaResolucion.format(DATE_TIME_FORMAT) : null,
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
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const requerimiento = this.createFromForm();
    if (requerimiento.id !== undefined) {
      this.subscribeToSaveResponse(this.requerimientoService.update(requerimiento));
    } else {
      this.subscribeToSaveResponse(this.requerimientoService.create(requerimiento));
    }
  }

  private createFromForm(): IRequerimiento {
    return {
      ...new Requerimiento(),
      id: this.editForm.get(['id']).value,
      fechaAlda:
        this.editForm.get(['fechaAlda']).value != null ? moment(this.editForm.get(['fechaAlda']).value, DATE_TIME_FORMAT) : undefined,
      fechaResolucion:
        this.editForm.get(['fechaResolucion']).value != null
          ? moment(this.editForm.get(['fechaResolucion']).value, DATE_TIME_FORMAT)
          : undefined,
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
      usuarioCreadorId: this.editForm.get(['usuarioCreadorId']).value,
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
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
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
  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.allFruits.filter(fruit => fruit.toLowerCase().indexOf(filterValue) === 0);
  }
  verificarReqEstatus( status: string) {
    if ( status === 'Cerrado' ) {
      this.reqCancelado = true;
    } else {
      this.reqCancelado = false;
    }
  }
  verificarReemplazo( status: string) {
    if ( status === 'Reemplazo' ) {
       this.reemplazo = true;
    } else {
      this.reemplazo = false;
    }
  }
  // Get Current Location Coordinates
  private setCurrentLocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition( position => {
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
  getAddress( latitude: any, longitude: any) {
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
   // Get employees list
   getSkills() {
    return this.restApi.getskills().subscribe((data: {}) => {
      this.Skill = data;
    });
  }
}
