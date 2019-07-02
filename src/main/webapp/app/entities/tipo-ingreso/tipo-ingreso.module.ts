import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  TipoIngresoComponent,
  TipoIngresoDetailComponent,
  TipoIngresoUpdateComponent,
  TipoIngresoDeletePopupComponent,
  TipoIngresoDeleteDialogComponent,
  tipoIngresoRoute,
  tipoIngresoPopupRoute
} from './';

const ENTITY_STATES = [...tipoIngresoRoute, ...tipoIngresoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoIngresoComponent,
    TipoIngresoDetailComponent,
    TipoIngresoUpdateComponent,
    TipoIngresoDeleteDialogComponent,
    TipoIngresoDeletePopupComponent
  ],
  entryComponents: [TipoIngresoComponent, TipoIngresoUpdateComponent, TipoIngresoDeleteDialogComponent, TipoIngresoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTipoIngresoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
