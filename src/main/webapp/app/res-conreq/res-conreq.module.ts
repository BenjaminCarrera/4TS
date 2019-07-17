import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../shared';
import { AgmCoreModule } from '@agm/core';

import { RES_CONREQ_ROUTE, ResConreqComponent } from './';

@NgModule({
    imports: [
      AppSharedModule,
      RouterModule.forRoot([ RES_CONREQ_ROUTE ], { useHash: true }),
      AgmCoreModule.forRoot({
        apiKey: 'AIzaSyCrRv-Zq8fqhVFf02Kfg2TPrjcJsJQE0e0'
      })
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
