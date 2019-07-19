import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { AgmCoreModule } from '@agm/core';
import { AppSharedModule } from 'app/shared';
import {
  RequerimientoComponent,
  RequerimientoDetailComponent,
  RequerimientoUpdateComponent,
  RequerimientoDeletePopupComponent,
  RequerimientoDeleteDialogComponent,
  requerimientoRoute,
  requerimientoPopupRoute
} from './';

const ENTITY_STATES = [...requerimientoRoute, ...requerimientoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyCrRv-Zq8fqhVFf02Kfg2TPrjcJsJQE0e0',
      libraries: ['places']
    })],
  declarations: [
    RequerimientoComponent,
    RequerimientoDetailComponent,
    RequerimientoUpdateComponent,
    RequerimientoDeleteDialogComponent,
    RequerimientoDeletePopupComponent
  ],
  entryComponents: [
    RequerimientoComponent,
    RequerimientoUpdateComponent,
    RequerimientoDeleteDialogComponent,
    RequerimientoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppRequerimientoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
