import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { RES_CONCAND_ROUTE, ResConcandComponent } from './';
import { AgmCoreModule } from '@agm/core';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ RES_CONCAND_ROUTE ], { useHash: true }),
      AgmCoreModule.forRoot({
        apiKey: 'AIzaSyCrRv-Zq8fqhVFf02Kfg2TPrjcJsJQE0e0'
      })
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
