import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  TipoPeriodoComponent,
  TipoPeriodoDetailComponent,
  TipoPeriodoUpdateComponent,
  TipoPeriodoDeletePopupComponent,
  TipoPeriodoDeleteDialogComponent,
  tipoPeriodoRoute,
  tipoPeriodoPopupRoute
} from './';

const ENTITY_STATES = [...tipoPeriodoRoute, ...tipoPeriodoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoPeriodoComponent,
    TipoPeriodoDetailComponent,
    TipoPeriodoUpdateComponent,
    TipoPeriodoDeleteDialogComponent,
    TipoPeriodoDeletePopupComponent
  ],
  entryComponents: [TipoPeriodoComponent, TipoPeriodoUpdateComponent, TipoPeriodoDeleteDialogComponent, TipoPeriodoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTipoPeriodoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
