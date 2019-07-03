import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { AgregCandComponent } from './agreg-cand.component';

export const AGREG_CAND_ROUTE: Route = {
  path: 'agreg-cand',
  component: AgregCandComponent,
  data: {
    authorities: [],
    pageTitle: 'agreg-cand.title'
  },
  canActivate: [UserRouteAccessService]
};
