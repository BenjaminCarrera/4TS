import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstCanInactivoComponent,
  EstCanInactivoDetailComponent,
  EstCanInactivoUpdateComponent,
  EstCanInactivoDeletePopupComponent,
  EstCanInactivoDeleteDialogComponent,
  estCanInactivoRoute,
  estCanInactivoPopupRoute
} from './';

const ENTITY_STATES = [...estCanInactivoRoute, ...estCanInactivoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstCanInactivoComponent,
    EstCanInactivoDetailComponent,
    EstCanInactivoUpdateComponent,
    EstCanInactivoDeleteDialogComponent,
    EstCanInactivoDeletePopupComponent
  ],
  entryComponents: [
    EstCanInactivoComponent,
    EstCanInactivoUpdateComponent,
    EstCanInactivoDeleteDialogComponent,
    EstCanInactivoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstCanInactivoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
