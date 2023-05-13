import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CityWestComponent } from '../list/city-west.component';
import { CityWestDetailComponent } from '../detail/city-west-detail.component';
import { CityWestUpdateComponent } from '../update/city-west-update.component';
import { CityWestRoutingResolveService } from './city-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const cityWestRoute: Routes = [
  {
    path: '',
    component: CityWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CityWestDetailComponent,
    resolve: {
      cityWest: CityWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CityWestUpdateComponent,
    resolve: {
      cityWest: CityWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CityWestUpdateComponent,
    resolve: {
      cityWest: CityWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(cityWestRoute)],
  exports: [RouterModule],
})
export class CityWestRoutingModule {}
