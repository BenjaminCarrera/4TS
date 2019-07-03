import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { AgregReqComponent } from './agreg-req.component';

export const AGREG_REQ_ROUTE: Route = {
  path: 'agreg-req',
  component: AgregReqComponent,
  data: {
    authorities: [],
    pageTitle: 'agreg-req.title'
  },
  canActivate: [UserRouteAccessService]
};
