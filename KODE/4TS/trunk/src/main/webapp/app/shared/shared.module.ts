import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

import {JhMaterialModule} from 'app/shared/jh-material.module';
@NgModule({
  imports: [JhMaterialModule, AppSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JhMaterialModule, AppSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppSharedModule {
  static forRoot() {
    return {
      ngModule: AppSharedModule
    };
  }
}
