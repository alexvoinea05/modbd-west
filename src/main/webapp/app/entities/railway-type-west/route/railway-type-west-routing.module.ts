import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RailwayTypeWestComponent } from '../list/railway-type-west.component';
import { RailwayTypeWestDetailComponent } from '../detail/railway-type-west-detail.component';
import { RailwayTypeWestUpdateComponent } from '../update/railway-type-west-update.component';
import { RailwayTypeWestRoutingResolveService } from './railway-type-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const railwayTypeWestRoute: Routes = [
  {
    path: '',
    component: RailwayTypeWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RailwayTypeWestDetailComponent,
    resolve: {
      railwayTypeWest: RailwayTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RailwayTypeWestUpdateComponent,
    resolve: {
      railwayTypeWest: RailwayTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RailwayTypeWestUpdateComponent,
    resolve: {
      railwayTypeWest: RailwayTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(railwayTypeWestRoute)],
  exports: [RouterModule],
})
export class RailwayTypeWestRoutingModule {}
