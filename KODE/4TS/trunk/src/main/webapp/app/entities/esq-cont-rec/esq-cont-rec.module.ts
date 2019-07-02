import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EsqContRecComponent,
  EsqContRecDetailComponent,
  EsqContRecUpdateComponent,
  EsqContRecDeletePopupComponent,
  EsqContRecDeleteDialogComponent,
  esqContRecRoute,
  esqContRecPopupRoute
} from './';

const ENTITY_STATES = [...esqContRecRoute, ...esqContRecPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EsqContRecComponent,
    EsqContRecDetailComponent,
    EsqContRecUpdateComponent,
    EsqContRecDeleteDialogComponent,
    EsqContRecDeletePopupComponent
  ],
  entryComponents: [EsqContRecComponent, EsqContRecUpdateComponent, EsqContRecDeleteDialogComponent, EsqContRecDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEsqContRecModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
