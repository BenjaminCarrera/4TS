import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstReqCerradoComponent,
  EstReqCerradoDetailComponent,
  EstReqCerradoUpdateComponent,
  EstReqCerradoDeletePopupComponent,
  EstReqCerradoDeleteDialogComponent,
  estReqCerradoRoute,
  estReqCerradoPopupRoute
} from './';

const ENTITY_STATES = [...estReqCerradoRoute, ...estReqCerradoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstReqCerradoComponent,
    EstReqCerradoDetailComponent,
    EstReqCerradoUpdateComponent,
    EstReqCerradoDeleteDialogComponent,
    EstReqCerradoDeletePopupComponent
  ],
  entryComponents: [
    EstReqCerradoComponent,
    EstReqCerradoUpdateComponent,
    EstReqCerradoDeleteDialogComponent,
    EstReqCerradoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstReqCerradoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
