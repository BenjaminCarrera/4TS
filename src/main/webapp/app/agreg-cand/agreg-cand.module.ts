import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { AGREG_CAND_ROUTE, AgregCandComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ AGREG_CAND_ROUTE ], { useHash: true })
    ],
    declarations: [
      AgregCandComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppAgregCandModule {}
