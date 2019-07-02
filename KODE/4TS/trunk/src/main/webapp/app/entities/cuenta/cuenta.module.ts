import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  CuentaComponent,
  CuentaDetailComponent,
  CuentaUpdateComponent,
  CuentaDeletePopupComponent,
  CuentaDeleteDialogComponent,
  cuentaRoute,
  cuentaPopupRoute
} from './';

const ENTITY_STATES = [...cuentaRoute, ...cuentaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CuentaComponent, CuentaDetailComponent, CuentaUpdateComponent, CuentaDeleteDialogComponent, CuentaDeletePopupComponent],
  entryComponents: [CuentaComponent, CuentaUpdateComponent, CuentaDeleteDialogComponent, CuentaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCuentaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
