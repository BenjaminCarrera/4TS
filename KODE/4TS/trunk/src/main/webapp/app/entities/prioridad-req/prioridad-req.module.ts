import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  PrioridadReqComponent,
  PrioridadReqDetailComponent,
  PrioridadReqUpdateComponent,
  PrioridadReqDeletePopupComponent,
  PrioridadReqDeleteDialogComponent,
  prioridadReqRoute,
  prioridadReqPopupRoute
} from './';

const ENTITY_STATES = [...prioridadReqRoute, ...prioridadReqPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PrioridadReqComponent,
    PrioridadReqDetailComponent,
    PrioridadReqUpdateComponent,
    PrioridadReqDeleteDialogComponent,
    PrioridadReqDeletePopupComponent
  ],
  entryComponents: [
    PrioridadReqComponent,
    PrioridadReqUpdateComponent,
    PrioridadReqDeleteDialogComponent,
    PrioridadReqDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppPrioridadReqModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
