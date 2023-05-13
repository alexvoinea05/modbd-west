import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FuelTypeWestComponent } from '../list/fuel-type-west.component';
import { FuelTypeWestDetailComponent } from '../detail/fuel-type-west-detail.component';
import { FuelTypeWestUpdateComponent } from '../update/fuel-type-west-update.component';
import { FuelTypeWestRoutingResolveService } from './fuel-type-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fuelTypeWestRoute: Routes = [
  {
    path: '',
    component: FuelTypeWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuelTypeWestDetailComponent,
    resolve: {
      fuelTypeWest: FuelTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuelTypeWestUpdateComponent,
    resolve: {
      fuelTypeWest: FuelTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuelTypeWestUpdateComponent,
    resolve: {
      fuelTypeWest: FuelTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fuelTypeWestRoute)],
  exports: [RouterModule],
})
export class FuelTypeWestRoutingModule {}
