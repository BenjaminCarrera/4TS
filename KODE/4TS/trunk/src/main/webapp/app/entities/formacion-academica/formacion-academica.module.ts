import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  FormacionAcademicaComponent,
  FormacionAcademicaDetailComponent,
  FormacionAcademicaUpdateComponent,
  FormacionAcademicaDeletePopupComponent,
  FormacionAcademicaDeleteDialogComponent,
  formacionAcademicaRoute,
  formacionAcademicaPopupRoute
} from './';

const ENTITY_STATES = [...formacionAcademicaRoute, ...formacionAcademicaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FormacionAcademicaComponent,
    FormacionAcademicaDetailComponent,
    FormacionAcademicaUpdateComponent,
    FormacionAcademicaDeleteDialogComponent,
    FormacionAcademicaDeletePopupComponent
  ],
  entryComponents: [
    FormacionAcademicaComponent,
    FormacionAcademicaUpdateComponent,
    FormacionAcademicaDeleteDialogComponent,
    FormacionAcademicaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppFormacionAcademicaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
