import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  PermisoAuthorityComponent,
  PermisoAuthorityDetailComponent,
  PermisoAuthorityUpdateComponent,
  PermisoAuthorityDeletePopupComponent,
  PermisoAuthorityDeleteDialogComponent,
  permisoAuthorityRoute,
  permisoAuthorityPopupRoute
} from './';

const ENTITY_STATES = [...permisoAuthorityRoute, ...permisoAuthorityPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PermisoAuthorityComponent,
    PermisoAuthorityDetailComponent,
    PermisoAuthorityUpdateComponent,
    PermisoAuthorityDeleteDialogComponent,
    PermisoAuthorityDeletePopupComponent
  ],
  entryComponents: [
    PermisoAuthorityComponent,
    PermisoAuthorityUpdateComponent,
    PermisoAuthorityDeleteDialogComponent,
    PermisoAuthorityDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppPermisoAuthorityModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
