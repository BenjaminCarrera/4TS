import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ReferenciasLaboralesComponent,
  ReferenciasLaboralesDetailComponent,
  ReferenciasLaboralesUpdateComponent,
  ReferenciasLaboralesDeletePopupComponent,
  ReferenciasLaboralesDeleteDialogComponent,
  referenciasLaboralesRoute,
  referenciasLaboralesPopupRoute
} from './';

const ENTITY_STATES = [...referenciasLaboralesRoute, ...referenciasLaboralesPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReferenciasLaboralesComponent,
    ReferenciasLaboralesDetailComponent,
    ReferenciasLaboralesUpdateComponent,
    ReferenciasLaboralesDeleteDialogComponent,
    ReferenciasLaboralesDeletePopupComponent
  ],
  entryComponents: [
    ReferenciasLaboralesComponent,
    ReferenciasLaboralesUpdateComponent,
    ReferenciasLaboralesDeleteDialogComponent,
    ReferenciasLaboralesDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppReferenciasLaboralesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
