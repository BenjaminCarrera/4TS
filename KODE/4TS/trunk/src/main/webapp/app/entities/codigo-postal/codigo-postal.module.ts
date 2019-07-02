import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  CodigoPostalComponent,
  CodigoPostalDetailComponent,
  CodigoPostalUpdateComponent,
  CodigoPostalDeletePopupComponent,
  CodigoPostalDeleteDialogComponent,
  codigoPostalRoute,
  codigoPostalPopupRoute
} from './';

const ENTITY_STATES = [...codigoPostalRoute, ...codigoPostalPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CodigoPostalComponent,
    CodigoPostalDetailComponent,
    CodigoPostalUpdateComponent,
    CodigoPostalDeleteDialogComponent,
    CodigoPostalDeletePopupComponent
  ],
  entryComponents: [
    CodigoPostalComponent,
    CodigoPostalUpdateComponent,
    CodigoPostalDeleteDialogComponent,
    CodigoPostalDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCodigoPostalModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
