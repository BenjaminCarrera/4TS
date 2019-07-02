import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  PerfilComponent,
  PerfilDetailComponent,
  PerfilUpdateComponent,
  PerfilDeletePopupComponent,
  PerfilDeleteDialogComponent,
  perfilRoute,
  perfilPopupRoute
} from './';

const ENTITY_STATES = [...perfilRoute, ...perfilPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PerfilComponent, PerfilDetailComponent, PerfilUpdateComponent, PerfilDeleteDialogComponent, PerfilDeletePopupComponent],
  entryComponents: [PerfilComponent, PerfilUpdateComponent, PerfilDeleteDialogComponent, PerfilDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppPerfilModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
