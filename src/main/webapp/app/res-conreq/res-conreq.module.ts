import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { RES_CONREQ_ROUTE, ResConreqComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ RES_CONREQ_ROUTE ], { useHash: true })
    ],
    declarations: [
      ResConreqComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppResConreqModule {}
