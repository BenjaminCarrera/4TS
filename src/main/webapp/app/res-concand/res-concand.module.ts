import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { RES_CONCAND_ROUTE, ResConcandComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ RES_CONCAND_ROUTE ], { useHash: true })
    ],
    declarations: [
      ResConcandComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppResConcandModule {}
