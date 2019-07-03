import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ConReqComponent } from './con-req.component';

export const CON_REQ_ROUTE: Route = {
  path: 'con-req',
  component: ConReqComponent,
  data: {
    authorities: [],
    pageTitle: 'con-req.title'
  },
  canActivate: [UserRouteAccessService]
};
