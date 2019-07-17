import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';

import { AGREG_REQ_ROUTE, AgregReqComponent } from './';
import { AgmCoreModule } from '@agm/core';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ AGREG_REQ_ROUTE ], { useHash: true }),
      AgmCoreModule.forRoot({
        apiKey: 'AIzaSyCrRv-Zq8fqhVFf02Kfg2TPrjcJsJQE0e0'
      })
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
