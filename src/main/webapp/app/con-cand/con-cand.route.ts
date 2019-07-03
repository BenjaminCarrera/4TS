import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ConCandComponent } from './con-cand.component';

export const CON_CAND_ROUTE: Route = {
  path: 'con-cand',
  component: ConCandComponent,
  data: {
    authorities: [],
    pageTitle: 'con-cand.title'
  },
  canActivate: [UserRouteAccessService]
};
