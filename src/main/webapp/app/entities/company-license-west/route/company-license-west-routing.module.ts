import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompanyLicenseWestComponent } from '../list/company-license-west.component';
import { CompanyLicenseWestDetailComponent } from '../detail/company-license-west-detail.component';
import { CompanyLicenseWestUpdateComponent } from '../update/company-license-west-update.component';
import { CompanyLicenseWestRoutingResolveService } from './company-license-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const companyLicenseWestRoute: Routes = [
  {
    path: '',
    component: CompanyLicenseWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyLicenseWestDetailComponent,
    resolve: {
      companyLicenseWest: CompanyLicenseWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyLicenseWestUpdateComponent,
    resolve: {
      companyLicenseWest: CompanyLicenseWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyLicenseWestUpdateComponent,
    resolve: {
      companyLicenseWest: CompanyLicenseWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companyLicenseWestRoute)],
  exports: [RouterModule],
})
export class CompanyLicenseWestRoutingModule {}
