import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { CON_REQ_ROUTE, ConReqComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ CON_REQ_ROUTE ], { useHash: true })
    ],
    declarations: [
      ConReqComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppConReqModule {}
