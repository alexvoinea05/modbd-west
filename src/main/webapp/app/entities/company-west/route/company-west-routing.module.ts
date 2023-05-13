import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompanyWestComponent } from '../list/company-west.component';
import { CompanyWestDetailComponent } from '../detail/company-west-detail.component';
import { CompanyWestUpdateComponent } from '../update/company-west-update.component';
import { CompanyWestRoutingResolveService } from './company-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const companyWestRoute: Routes = [
  {
    path: '',
    component: CompanyWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyWestDetailComponent,
    resolve: {
      companyWest: CompanyWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyWestUpdateComponent,
    resolve: {
      companyWest: CompanyWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyWestUpdateComponent,
    resolve: {
      companyWest: CompanyWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companyWestRoute)],
  exports: [RouterModule],
})
export class CompanyWestRoutingModule {}
