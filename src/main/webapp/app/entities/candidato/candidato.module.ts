import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { AgmCoreModule } from '@agm/core';
import { AppSharedModule } from 'app/shared';
import {
  CandidatoComponent,
  CandidatoDetailComponent,
  CandidatoUpdateComponent,
  CandidatoDeletePopupComponent,
  CandidatoDeleteDialogComponent,
  candidatoRoute,
  candidatoPopupRoute
} from './';
import { MAPS_API_KEY } from 'app/shared/constants/goole.constants';

const ENTITY_STATES = [...candidatoRoute, ...candidatoPopupRoute];

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(ENTITY_STATES),
  AgmCoreModule.forRoot({
    apiKey: MAPS_API_KEY,
    libraries: ['places']
  })],
  declarations: [
    CandidatoComponent,
    CandidatoDetailComponent,
    CandidatoUpdateComponent,
    CandidatoDeleteDialogComponent,
    CandidatoDeletePopupComponent
  ],
  entryComponents: [CandidatoComponent, CandidatoUpdateComponent, CandidatoDeleteDialogComponent, CandidatoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppCandidatoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
