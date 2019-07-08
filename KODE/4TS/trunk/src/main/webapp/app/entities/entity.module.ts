import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'candidato',
        loadChildren: './candidato/candidato.module#AppCandidatoModule'
      },
      {
        path: 'documento',
        loadChildren: './documento/documento.module#AppDocumentoModule'
      },
      {
        path: 'formacion-academica',
        loadChildren: './formacion-academica/formacion-academica.module#AppFormacionAcademicaModule'
      },
      {
        path: 'colonia',
        loadChildren: './colonia/colonia.module#AppColoniaModule'
      },
      {
        path: 'municipio',
        loadChildren: './municipio/municipio.module#AppMunicipioModule'
      },
      {
        path: 'estado',
        loadChildren: './estado/estado.module#AppEstadoModule'
      },
      {
        path: 'codigo-postal',
        loadChildren: './codigo-postal/codigo-postal.module#AppCodigoPostalModule'
      },
      {
        path: 'perfil',
        loadChildren: './perfil/perfil.module#AppPerfilModule'
      },
      {
        path: 'institucion-academica',
        loadChildren: './institucion-academica/institucion-academica.module#AppInstitucionAcademicaModule'
      },
      {
        path: 'estatus-candidato',
        loadChildren: './estatus-candidato/estatus-candidato.module#AppEstatusCandidatoModule'
      },
      {
        path: 'est-can-inactivo',
        loadChildren: './est-can-inactivo/est-can-inactivo.module#AppEstCanInactivoModule'
      },
      {
        path: 'tipo-periodo',
        loadChildren: './tipo-periodo/tipo-periodo.module#AppTipoPeriodoModule'
      },
      {
        path: 'nivel-perfil',
        loadChildren: './nivel-perfil/nivel-perfil.module#AppNivelPerfilModule'
      },
      {
        path: 'referencias-laborales',
        loadChildren: './referencias-laborales/referencias-laborales.module#AppReferenciasLaboralesModule'
      },
      {
        path: 'estatus-academico',
        loadChildren: './estatus-academico/estatus-academico.module#AppEstatusAcademicoModule'
      },
      {
        path: 'fuente-reclutamiento',
        loadChildren: './fuente-reclutamiento/fuente-reclutamiento.module#AppFuenteReclutamientoModule'
      },
      {
        path: 'esquema-contratacion-kode',
        loadChildren: './esquema-contratacion-kode/esquema-contratacion-kode.module#AppEsquemaContratacionKodeModule'
      },
      {
        path: 'esq-cont-rec',
        loadChildren: './esq-cont-rec/esq-cont-rec.module#AppEsqContRecModule'
      },
      {
        path: 'estatus-laboral',
        loadChildren: './estatus-laboral/estatus-laboral.module#AppEstatusLaboralModule'
      },
      {
        path: 'cuenta',
        loadChildren: './cuenta/cuenta.module#AppCuentaModule'
      },
      {
        path: 'skill',
        loadChildren: './skill/skill.module#AppSkillModule'
      },
      {
        path: 'tipo-skill',
        loadChildren: './tipo-skill/tipo-skill.module#AppTipoSkillModule'
      },
      {
        path: 'categoria-skill',
        loadChildren: './categoria-skill/categoria-skill.module#AppCategoriaSkillModule'
      },
      {
        path: 'skill-candidato',
        loadChildren: './skill-candidato/skill-candidato.module#AppSkillCandidatoModule'
      },
      {
        path: 'dominio-skill',
        loadChildren: './dominio-skill/dominio-skill.module#AppDominioSkillModule'
      },
      {
        path: 'requerimiento',
        loadChildren: './requerimiento/requerimiento.module#AppRequerimientoModule'
      },
      {
        path: 'estatus-requerimiento',
        loadChildren: './estatus-requerimiento/estatus-requerimiento.module#AppEstatusRequerimientoModule'
      },
      {
        path: 'est-req-cerrado',
        loadChildren: './est-req-cerrado/est-req-cerrado.module#AppEstReqCerradoModule'
      },
      {
        path: 'prioridad-req',
        loadChildren: './prioridad-req/prioridad-req.module#AppPrioridadReqModule'
      },
      {
        path: 'tipo-solicitud',
        loadChildren: './tipo-solicitud/tipo-solicitud.module#AppTipoSolicitudModule'
      },
      {
        path: 'tipo-ingreso',
        loadChildren: './tipo-ingreso/tipo-ingreso.module#AppTipoIngresoModule'
      },
      {
        path: 'base-tarifa',
        loadChildren: './base-tarifa/base-tarifa.module#AppBaseTarifaModule'
      },
      {
        path: 'skill-requerimiento',
        loadChildren: './skill-requerimiento/skill-requerimiento.module#AppSkillRequerimientoModule'
      },
      {
        path: 'estatus-req-can',
        loadChildren: './estatus-req-can/estatus-req-can.module#AppEstatusReqCanModule'
      },
      {
        path: 'estatus-req-can-rec',
        loadChildren: './estatus-req-can-rec/estatus-req-can-rec.module#AppEstatusReqCanRecModule'
      },
      {
        path: 'tarea',
        loadChildren: './tarea/tarea.module#AppTareaModule'
      },
      {
        path: 'estatus-tarea',
        loadChildren: './estatus-tarea/estatus-tarea.module#AppEstatusTareaModule'
      },
      {
        path: 'tipo-tarea',
        loadChildren: './tipo-tarea/tipo-tarea.module#AppTipoTareaModule'
      },
      {
        path: 'bitacora',
        loadChildren: './bitacora/bitacora.module#AppBitacoraModule'
      },
      {
        path: 'permiso',
        loadChildren: './permiso/permiso.module#AppPermisoModule'
      },
      {
        path: 'permiso-authority',
        loadChildren: './permiso-authority/permiso-authority.module#AppPermisoAuthorityModule'
      },
      {
        path: 'req-can',
        loadChildren: './req-can/req-can.module#AppReqCanModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEntityModule {}
