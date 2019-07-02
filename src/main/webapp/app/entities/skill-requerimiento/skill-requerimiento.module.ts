import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  SkillRequerimientoComponent,
  SkillRequerimientoDetailComponent,
  SkillRequerimientoUpdateComponent,
  SkillRequerimientoDeletePopupComponent,
  SkillRequerimientoDeleteDialogComponent,
  skillRequerimientoRoute,
  skillRequerimientoPopupRoute
} from './';

const ENTITY_STATES = [...skillRequerimientoRoute, ...skillRequerimientoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SkillRequerimientoComponent,
    SkillRequerimientoDetailComponent,
    SkillRequerimientoUpdateComponent,
    SkillRequerimientoDeleteDialogComponent,
    SkillRequerimientoDeletePopupComponent
  ],
  entryComponents: [
    SkillRequerimientoComponent,
    SkillRequerimientoUpdateComponent,
    SkillRequerimientoDeleteDialogComponent,
    SkillRequerimientoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSkillRequerimientoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
