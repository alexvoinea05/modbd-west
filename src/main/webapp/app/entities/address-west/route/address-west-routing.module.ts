import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AddressWestComponent } from '../list/address-west.component';
import { AddressWestDetailComponent } from '../detail/address-west-detail.component';
import { AddressWestUpdateComponent } from '../update/address-west-update.component';
import { AddressWestRoutingResolveService } from './address-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const addressWestRoute: Routes = [
  {
    path: '',
    component: AddressWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddressWestDetailComponent,
    resolve: {
      addressWest: AddressWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddressWestUpdateComponent,
    resolve: {
      addressWest: AddressWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddressWestUpdateComponent,
    resolve: {
      addressWest: AddressWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(addressWestRoute)],
  exports: [RouterModule],
})
export class AddressWestRoutingModule {}
