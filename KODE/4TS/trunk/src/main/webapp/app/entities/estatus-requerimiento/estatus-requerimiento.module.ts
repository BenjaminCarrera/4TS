import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusRequerimientoComponent,
  EstatusRequerimientoDetailComponent,
  EstatusRequerimientoUpdateComponent,
  EstatusRequerimientoDeletePopupComponent,
  EstatusRequerimientoDeleteDialogComponent,
  estatusRequerimientoRoute,
  estatusRequerimientoPopupRoute
} from './';

const ENTITY_STATES = [...estatusRequerimientoRoute, ...estatusRequerimientoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusRequerimientoComponent,
    EstatusRequerimientoDetailComponent,
    EstatusRequerimientoUpdateComponent,
    EstatusRequerimientoDeleteDialogComponent,
    EstatusRequerimientoDeletePopupComponent
  ],
  entryComponents: [
    EstatusRequerimientoComponent,
    EstatusRequerimientoUpdateComponent,
    EstatusRequerimientoDeleteDialogComponent,
    EstatusRequerimientoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusRequerimientoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
