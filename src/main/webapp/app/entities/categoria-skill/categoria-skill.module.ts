import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  CategoriaSkillComponent,
  CategoriaSkillDetailComponent,
  CategoriaSkillUpdateComponent,
  CategoriaSkillDeletePopupComponent,
  CategoriaSkillDeleteDialogComponent,
  categoriaSkillRoute,
  categoriaSkillPopupRoute
} from './';

const ENTITY_STATES = [...categoriaSkillRoute, ...categoriaSkillPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategoriaSkillComponent,
    CategoriaSkillDetailComponent,
    CategoriaSkillUpdateComponent,
    CategoriaSkillDeleteDialogComponent,
    CategoriaSkillDeletePopupComponent
  ],
  entryComponents: [
    CategoriaSkillComponent,
    CategoriaSkillUpdateComponent,
    CategoriaSkillDeleteDialogComponent,
    CategoriaSkillDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCategoriaSkillModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
