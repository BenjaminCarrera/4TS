import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AppSharedModule } from 'app/shared';
import {
  EstatusTareaComponent,
  EstatusTareaDetailComponent,
  EstatusTareaUpdateComponent,
  EstatusTareaDeletePopupComponent,
  EstatusTareaDeleteDialogComponent,
  estatusTareaRoute,
  estatusTareaPopupRoute
} from './';

const ENTITY_STATES = [...estatusTareaRoute, ...estatusTareaPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EstatusTareaComponent,
    EstatusTareaDetailComponent,
    EstatusTareaUpdateComponent,
    EstatusTareaDeleteDialogComponent,
    EstatusTareaDeletePopupComponent
  ],
  entryComponents: [
    EstatusTareaComponent,
    EstatusTareaUpdateComponent,
    EstatusTareaDeleteDialogComponent,
    EstatusTareaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppEstatusTareaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
