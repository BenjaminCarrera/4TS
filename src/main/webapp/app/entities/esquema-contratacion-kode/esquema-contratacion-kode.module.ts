import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EsquemaContratacionKodeComponent,
  EsquemaContratacionKodeDetailComponent,
  EsquemaContratacionKodeUpdateComponent,
  EsquemaContratacionKodeDeletePopupComponent,
  EsquemaContratacionKodeDeleteDialogComponent,
  esquemaContratacionKodeRoute,
  esquemaContratacionKodePopupRoute
} from './';

const ENTITY_STATES = [...esquemaContratacionKodeRoute, ...esquemaContratacionKodePopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EsquemaContratacionKodeComponent,
    EsquemaContratacionKodeDetailComponent,
    EsquemaContratacionKodeUpdateComponent,
    EsquemaContratacionKodeDeleteDialogComponent,
    EsquemaContratacionKodeDeletePopupComponent
  ],
  entryComponents: [
    EsquemaContratacionKodeComponent,
    EsquemaContratacionKodeUpdateComponent,
    EsquemaContratacionKodeDeleteDialogComponent,
    EsquemaContratacionKodeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEsquemaContratacionKodeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
