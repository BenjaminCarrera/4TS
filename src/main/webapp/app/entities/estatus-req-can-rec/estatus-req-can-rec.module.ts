import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusReqCanRecComponent,
  EstatusReqCanRecDetailComponent,
  EstatusReqCanRecUpdateComponent,
  EstatusReqCanRecDeletePopupComponent,
  EstatusReqCanRecDeleteDialogComponent,
  estatusReqCanRecRoute,
  estatusReqCanRecPopupRoute
} from './';

const ENTITY_STATES = [...estatusReqCanRecRoute, ...estatusReqCanRecPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusReqCanRecComponent,
    EstatusReqCanRecDetailComponent,
    EstatusReqCanRecUpdateComponent,
    EstatusReqCanRecDeleteDialogComponent,
    EstatusReqCanRecDeletePopupComponent
  ],
  entryComponents: [
    EstatusReqCanRecComponent,
    EstatusReqCanRecUpdateComponent,
    EstatusReqCanRecDeleteDialogComponent,
    EstatusReqCanRecDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusReqCanRecModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
