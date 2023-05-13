import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DistrictWestComponent } from '../list/district-west.component';
import { DistrictWestDetailComponent } from '../detail/district-west-detail.component';
import { DistrictWestUpdateComponent } from '../update/district-west-update.component';
import { DistrictWestRoutingResolveService } from './district-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const districtWestRoute: Routes = [
  {
    path: '',
    component: DistrictWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistrictWestDetailComponent,
    resolve: {
      districtWest: DistrictWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistrictWestUpdateComponent,
    resolve: {
      districtWest: DistrictWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistrictWestUpdateComponent,
    resolve: {
      districtWest: DistrictWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(districtWestRoute)],
  exports: [RouterModule],
})
export class DistrictWestRoutingModule {}
