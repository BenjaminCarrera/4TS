import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  PermisoComponent,
  PermisoDetailComponent,
  PermisoUpdateComponent,
  PermisoDeletePopupComponent,
  PermisoDeleteDialogComponent,
  permisoRoute,
  permisoPopupRoute
} from './';

const ENTITY_STATES = [...permisoRoute, ...permisoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PermisoComponent,
    PermisoDetailComponent,
    PermisoUpdateComponent,
    PermisoDeleteDialogComponent,
    PermisoDeletePopupComponent
  ],
  entryComponents: [PermisoComponent, PermisoUpdateComponent, PermisoDeleteDialogComponent, PermisoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppPermisoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
