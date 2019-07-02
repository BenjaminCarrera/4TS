import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  NivelPerfilComponent,
  NivelPerfilDetailComponent,
  NivelPerfilUpdateComponent,
  NivelPerfilDeletePopupComponent,
  NivelPerfilDeleteDialogComponent,
  nivelPerfilRoute,
  nivelPerfilPopupRoute
} from './';

const ENTITY_STATES = [...nivelPerfilRoute, ...nivelPerfilPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NivelPerfilComponent,
    NivelPerfilDetailComponent,
    NivelPerfilUpdateComponent,
    NivelPerfilDeleteDialogComponent,
    NivelPerfilDeletePopupComponent
  ],
  entryComponents: [NivelPerfilComponent, NivelPerfilUpdateComponent, NivelPerfilDeleteDialogComponent, NivelPerfilDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppNivelPerfilModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
