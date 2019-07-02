import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  TipoSolicitudComponent,
  TipoSolicitudDetailComponent,
  TipoSolicitudUpdateComponent,
  TipoSolicitudDeletePopupComponent,
  TipoSolicitudDeleteDialogComponent,
  tipoSolicitudRoute,
  tipoSolicitudPopupRoute
} from './';

const ENTITY_STATES = [...tipoSolicitudRoute, ...tipoSolicitudPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoSolicitudComponent,
    TipoSolicitudDetailComponent,
    TipoSolicitudUpdateComponent,
    TipoSolicitudDeleteDialogComponent,
    TipoSolicitudDeletePopupComponent
  ],
  entryComponents: [
    TipoSolicitudComponent,
    TipoSolicitudUpdateComponent,
    TipoSolicitudDeleteDialogComponent,
    TipoSolicitudDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTipoSolicitudModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
