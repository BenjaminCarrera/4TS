import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ResConcandComponent } from './res-concand.component';

export const RES_CONCAND_ROUTE: Route = {
  path: 'res-concand',
  component: ResConcandComponent,
  data: {
    authorities: [],
    pageTitle: 'res-concand.title'
  },
  canActivate: [UserRouteAccessService]
};
