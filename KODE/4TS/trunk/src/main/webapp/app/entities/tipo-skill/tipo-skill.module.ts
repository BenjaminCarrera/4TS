import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  TipoSkillComponent,
  TipoSkillDetailComponent,
  TipoSkillUpdateComponent,
  TipoSkillDeletePopupComponent,
  TipoSkillDeleteDialogComponent,
  tipoSkillRoute,
  tipoSkillPopupRoute
} from './';

const ENTITY_STATES = [...tipoSkillRoute, ...tipoSkillPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoSkillComponent,
    TipoSkillDetailComponent,
    TipoSkillUpdateComponent,
    TipoSkillDeleteDialogComponent,
    TipoSkillDeletePopupComponent
  ],
  entryComponents: [TipoSkillComponent, TipoSkillUpdateComponent, TipoSkillDeleteDialogComponent, TipoSkillDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppTipoSkillModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
