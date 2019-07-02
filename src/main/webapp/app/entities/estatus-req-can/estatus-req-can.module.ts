import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusReqCanComponent,
  EstatusReqCanDetailComponent,
  EstatusReqCanUpdateComponent,
  EstatusReqCanDeletePopupComponent,
  EstatusReqCanDeleteDialogComponent,
  estatusReqCanRoute,
  estatusReqCanPopupRoute
} from './';

const ENTITY_STATES = [...estatusReqCanRoute, ...estatusReqCanPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusReqCanComponent,
    EstatusReqCanDetailComponent,
    EstatusReqCanUpdateComponent,
    EstatusReqCanDeleteDialogComponent,
    EstatusReqCanDeletePopupComponent
  ],
  entryComponents: [
    EstatusReqCanComponent,
    EstatusReqCanUpdateComponent,
    EstatusReqCanDeleteDialogComponent,
    EstatusReqCanDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusReqCanModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
