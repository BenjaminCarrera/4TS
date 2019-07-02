import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  BaseTarifaComponent,
  BaseTarifaDetailComponent,
  BaseTarifaUpdateComponent,
  BaseTarifaDeletePopupComponent,
  BaseTarifaDeleteDialogComponent,
  baseTarifaRoute,
  baseTarifaPopupRoute
} from './';

const ENTITY_STATES = [...baseTarifaRoute, ...baseTarifaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BaseTarifaComponent,
    BaseTarifaDetailComponent,
    BaseTarifaUpdateComponent,
    BaseTarifaDeleteDialogComponent,
    BaseTarifaDeletePopupComponent
  ],
  entryComponents: [BaseTarifaComponent, BaseTarifaUpdateComponent, BaseTarifaDeleteDialogComponent, BaseTarifaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppBaseTarifaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
