import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RailwayStationWestComponent } from '../list/railway-station-west.component';
import { RailwayStationWestDetailComponent } from '../detail/railway-station-west-detail.component';
import { RailwayStationWestUpdateComponent } from '../update/railway-station-west-update.component';
import { RailwayStationWestRoutingResolveService } from './railway-station-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const railwayStationWestRoute: Routes = [
  {
    path: '',
    component: RailwayStationWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RailwayStationWestDetailComponent,
    resolve: {
      railwayStationWest: RailwayStationWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RailwayStationWestUpdateComponent,
    resolve: {
      railwayStationWest: RailwayStationWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RailwayStationWestUpdateComponent,
    resolve: {
      railwayStationWest: RailwayStationWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(railwayStationWestRoute)],
  exports: [RouterModule],
})
export class RailwayStationWestRoutingModule {}
