import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  InstitucionAcademicaComponent,
  InstitucionAcademicaDetailComponent,
  InstitucionAcademicaUpdateComponent,
  InstitucionAcademicaDeletePopupComponent,
  InstitucionAcademicaDeleteDialogComponent,
  institucionAcademicaRoute,
  institucionAcademicaPopupRoute
} from './';

const ENTITY_STATES = [...institucionAcademicaRoute, ...institucionAcademicaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InstitucionAcademicaComponent,
    InstitucionAcademicaDetailComponent,
    InstitucionAcademicaUpdateComponent,
    InstitucionAcademicaDeleteDialogComponent,
    InstitucionAcademicaDeletePopupComponent
  ],
  entryComponents: [
    InstitucionAcademicaComponent,
    InstitucionAcademicaUpdateComponent,
    InstitucionAcademicaDeleteDialogComponent,
    InstitucionAcademicaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppInstitucionAcademicaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
