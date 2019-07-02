import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  ReqCanComponent,
  ReqCanDetailComponent,
  ReqCanUpdateComponent,
  ReqCanDeletePopupComponent,
  ReqCanDeleteDialogComponent,
  reqCanRoute,
  reqCanPopupRoute
} from './';

const ENTITY_STATES = [...reqCanRoute, ...reqCanPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ReqCanComponent, ReqCanDetailComponent, ReqCanUpdateComponent, ReqCanDeleteDialogComponent, ReqCanDeletePopupComponent],
  entryComponents: [ReqCanComponent, ReqCanUpdateComponent, ReqCanDeleteDialogComponent, ReqCanDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppReqCanModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
