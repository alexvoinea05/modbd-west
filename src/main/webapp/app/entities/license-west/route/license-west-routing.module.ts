import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LicenseWestComponent } from '../list/license-west.component';
import { LicenseWestDetailComponent } from '../detail/license-west-detail.component';
import { LicenseWestUpdateComponent } from '../update/license-west-update.component';
import { LicenseWestRoutingResolveService } from './license-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const licenseWestRoute: Routes = [
  {
    path: '',
    component: LicenseWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LicenseWestDetailComponent,
    resolve: {
      licenseWest: LicenseWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LicenseWestUpdateComponent,
    resolve: {
      licenseWest: LicenseWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LicenseWestUpdateComponent,
    resolve: {
      licenseWest: LicenseWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(licenseWestRoute)],
  exports: [RouterModule],
})
export class LicenseWestRoutingModule {}
