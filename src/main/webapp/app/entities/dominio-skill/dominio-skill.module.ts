import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  DominioSkillComponent,
  DominioSkillDetailComponent,
  DominioSkillUpdateComponent,
  DominioSkillDeletePopupComponent,
  DominioSkillDeleteDialogComponent,
  dominioSkillRoute,
  dominioSkillPopupRoute
} from './';

const ENTITY_STATES = [...dominioSkillRoute, ...dominioSkillPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DominioSkillComponent,
    DominioSkillDetailComponent,
    DominioSkillUpdateComponent,
    DominioSkillDeleteDialogComponent,
    DominioSkillDeletePopupComponent
  ],
  entryComponents: [
    DominioSkillComponent,
    DominioSkillUpdateComponent,
    DominioSkillDeleteDialogComponent,
    DominioSkillDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDominioSkillModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
