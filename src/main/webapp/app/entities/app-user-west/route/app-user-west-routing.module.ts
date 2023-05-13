import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AppUserWestComponent } from '../list/app-user-west.component';
import { AppUserWestDetailComponent } from '../detail/app-user-west-detail.component';
import { AppUserWestUpdateComponent } from '../update/app-user-west-update.component';
import { AppUserWestRoutingResolveService } from './app-user-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const appUserWestRoute: Routes = [
  {
    path: '',
    component: AppUserWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AppUserWestDetailComponent,
    resolve: {
      appUserWest: AppUserWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AppUserWestUpdateComponent,
    resolve: {
      appUserWest: AppUserWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AppUserWestUpdateComponent,
    resolve: {
      appUserWest: AppUserWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(appUserWestRoute)],
  exports: [RouterModule],
})
export class AppUserWestRoutingModule {}
