import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { AGREG_CAND_ROUTE, AgregCandComponent } from './';
import { AgmCoreModule } from '@agm/core';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ AGREG_CAND_ROUTE ], { useHash: true }),
      AgmCoreModule.forRoot({
        apiKey: 'AIzaSyCrRv-Zq8fqhVFf02Kfg2TPrjcJsJQE0e0'
      })
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
