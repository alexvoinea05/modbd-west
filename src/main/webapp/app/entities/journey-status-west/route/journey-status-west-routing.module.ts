import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JourneyStatusWestComponent } from '../list/journey-status-west.component';
import { JourneyStatusWestDetailComponent } from '../detail/journey-status-west-detail.component';
import { JourneyStatusWestUpdateComponent } from '../update/journey-status-west-update.component';
import { JourneyStatusWestRoutingResolveService } from './journey-status-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const journeyStatusWestRoute: Routes = [
  {
    path: '',
    component: JourneyStatusWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JourneyStatusWestDetailComponent,
    resolve: {
      journeyStatusWest: JourneyStatusWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JourneyStatusWestUpdateComponent,
    resolve: {
      journeyStatusWest: JourneyStatusWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JourneyStatusWestUpdateComponent,
    resolve: {
      journeyStatusWest: JourneyStatusWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(journeyStatusWestRoute)],
  exports: [RouterModule],
})
export class JourneyStatusWestRoutingModule {}
