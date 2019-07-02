import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusLaboralComponent,
  EstatusLaboralDetailComponent,
  EstatusLaboralUpdateComponent,
  EstatusLaboralDeletePopupComponent,
  EstatusLaboralDeleteDialogComponent,
  estatusLaboralRoute,
  estatusLaboralPopupRoute
} from './';

const ENTITY_STATES = [...estatusLaboralRoute, ...estatusLaboralPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusLaboralComponent,
    EstatusLaboralDetailComponent,
    EstatusLaboralUpdateComponent,
    EstatusLaboralDeleteDialogComponent,
    EstatusLaboralDeletePopupComponent
  ],
  entryComponents: [
    EstatusLaboralComponent,
    EstatusLaboralUpdateComponent,
    EstatusLaboralDeleteDialogComponent,
    EstatusLaboralDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusLaboralModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
