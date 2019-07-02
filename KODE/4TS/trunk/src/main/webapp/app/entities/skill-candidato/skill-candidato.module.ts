import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  SkillCandidatoComponent,
  SkillCandidatoDetailComponent,
  SkillCandidatoUpdateComponent,
  SkillCandidatoDeletePopupComponent,
  SkillCandidatoDeleteDialogComponent,
  skillCandidatoRoute,
  skillCandidatoPopupRoute
} from './';

const ENTITY_STATES = [...skillCandidatoRoute, ...skillCandidatoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SkillCandidatoComponent,
    SkillCandidatoDetailComponent,
    SkillCandidatoUpdateComponent,
    SkillCandidatoDeleteDialogComponent,
    SkillCandidatoDeletePopupComponent
  ],
  entryComponents: [
    SkillCandidatoComponent,
    SkillCandidatoUpdateComponent,
    SkillCandidatoDeleteDialogComponent,
    SkillCandidatoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSkillCandidatoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
