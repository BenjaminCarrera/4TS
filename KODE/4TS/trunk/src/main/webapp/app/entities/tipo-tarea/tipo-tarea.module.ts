import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  TipoTareaComponent,
  TipoTareaDetailComponent,
  TipoTareaUpdateComponent,
  TipoTareaDeletePopupComponent,
  TipoTareaDeleteDialogComponent,
  tipoTareaRoute,
  tipoTareaPopupRoute
} from './';

const ENTITY_STATES = [...tipoTareaRoute, ...tipoTareaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoTareaComponent,
    TipoTareaDetailComponent,
    TipoTareaUpdateComponent,
    TipoTareaDeleteDialogComponent,
    TipoTareaDeletePopupComponent
  ],
  entryComponents: [TipoTareaComponent, TipoTareaUpdateComponent, TipoTareaDeleteDialogComponent, TipoTareaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTipoTareaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
