import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  FuenteReclutamientoComponent,
  FuenteReclutamientoDetailComponent,
  FuenteReclutamientoUpdateComponent,
  FuenteReclutamientoDeletePopupComponent,
  FuenteReclutamientoDeleteDialogComponent,
  fuenteReclutamientoRoute,
  fuenteReclutamientoPopupRoute
} from './';

const ENTITY_STATES = [...fuenteReclutamientoRoute, ...fuenteReclutamientoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FuenteReclutamientoComponent,
    FuenteReclutamientoDetailComponent,
    FuenteReclutamientoUpdateComponent,
    FuenteReclutamientoDeleteDialogComponent,
    FuenteReclutamientoDeletePopupComponent
  ],
  entryComponents: [
    FuenteReclutamientoComponent,
    FuenteReclutamientoUpdateComponent,
    FuenteReclutamientoDeleteDialogComponent,
    FuenteReclutamientoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppFuenteReclutamientoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
