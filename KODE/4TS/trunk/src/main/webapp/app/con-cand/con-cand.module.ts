import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { CON_CAND_ROUTE, ConCandComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ CON_CAND_ROUTE ], { useHash: true })
    ],
    declarations: [
      ConCandComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppConCandModule {}
