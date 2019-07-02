import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusAcademicoComponent,
  EstatusAcademicoDetailComponent,
  EstatusAcademicoUpdateComponent,
  EstatusAcademicoDeletePopupComponent,
  EstatusAcademicoDeleteDialogComponent,
  estatusAcademicoRoute,
  estatusAcademicoPopupRoute
} from './';

const ENTITY_STATES = [...estatusAcademicoRoute, ...estatusAcademicoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusAcademicoComponent,
    EstatusAcademicoDetailComponent,
    EstatusAcademicoUpdateComponent,
    EstatusAcademicoDeleteDialogComponent,
    EstatusAcademicoDeletePopupComponent
  ],
  entryComponents: [
    EstatusAcademicoComponent,
    EstatusAcademicoUpdateComponent,
    EstatusAcademicoDeleteDialogComponent,
    EstatusAcademicoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusAcademicoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
