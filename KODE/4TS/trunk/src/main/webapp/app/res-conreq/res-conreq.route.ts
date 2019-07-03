import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ResConreqComponent } from './res-conreq.component';

export const RES_CONREQ_ROUTE: Route = {
  path: 'res-conreq',
  component: ResConreqComponent,
  data: {
    authorities: [],
    pageTitle: 'res-conreq.title'
  },
  canActivate: [UserRouteAccessService]
};
