import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JourneyWestComponent } from '../list/journey-west.component';
import { JourneyWestDetailComponent } from '../detail/journey-west-detail.component';
import { JourneyWestUpdateComponent } from '../update/journey-west-update.component';
import { JourneyWestRoutingResolveService } from './journey-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const journeyWestRoute: Routes = [
  {
    path: '',
    component: JourneyWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JourneyWestDetailComponent,
    resolve: {
      journeyWest: JourneyWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JourneyWestUpdateComponent,
    resolve: {
      journeyWest: JourneyWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JourneyWestUpdateComponent,
    resolve: {
      journeyWest: JourneyWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(journeyWestRoute)],
  exports: [RouterModule],
})
export class JourneyWestRoutingModule {}
