import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ColoniaComponent,
  ColoniaDetailComponent,
  ColoniaUpdateComponent,
  ColoniaDeletePopupComponent,
  ColoniaDeleteDialogComponent,
  coloniaRoute,
  coloniaPopupRoute
} from './';

const ENTITY_STATES = [...coloniaRoute, ...coloniaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ColoniaComponent,
    ColoniaDetailComponent,
    ColoniaUpdateComponent,
    ColoniaDeleteDialogComponent,
    ColoniaDeletePopupComponent
  ],
  entryComponents: [ColoniaComponent, ColoniaUpdateComponent, ColoniaDeleteDialogComponent, ColoniaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppColoniaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
