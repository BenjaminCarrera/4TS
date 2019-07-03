import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { AGREG_REQ_ROUTE, AgregReqComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ AGREG_REQ_ROUTE ], { useHash: true })
    ],
    declarations: [
      AgregReqComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppAgregReqModule {}
