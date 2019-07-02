import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusCandidatoComponent,
  EstatusCandidatoDetailComponent,
  EstatusCandidatoUpdateComponent,
  EstatusCandidatoDeletePopupComponent,
  EstatusCandidatoDeleteDialogComponent,
  estatusCandidatoRoute,
  estatusCandidatoPopupRoute
} from './';

const ENTITY_STATES = [...estatusCandidatoRoute, ...estatusCandidatoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusCandidatoComponent,
    EstatusCandidatoDetailComponent,
    EstatusCandidatoUpdateComponent,
    EstatusCandidatoDeleteDialogComponent,
    EstatusCandidatoDeletePopupComponent
  ],
  entryComponents: [
    EstatusCandidatoComponent,
    EstatusCandidatoUpdateComponent,
    EstatusCandidatoDeleteDialogComponent,
    EstatusCandidatoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusCandidatoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
