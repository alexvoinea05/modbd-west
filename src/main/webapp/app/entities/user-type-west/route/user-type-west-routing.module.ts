import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UserTypeWestComponent } from '../list/user-type-west.component';
import { UserTypeWestDetailComponent } from '../detail/user-type-west-detail.component';
import { UserTypeWestUpdateComponent } from '../update/user-type-west-update.component';
import { UserTypeWestRoutingResolveService } from './user-type-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const userTypeWestRoute: Routes = [
  {
    path: '',
    component: UserTypeWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserTypeWestDetailComponent,
    resolve: {
      userTypeWest: UserTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserTypeWestUpdateComponent,
    resolve: {
      userTypeWest: UserTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserTypeWestUpdateComponent,
    resolve: {
      userTypeWest: UserTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(userTypeWestRoute)],
  exports: [RouterModule],
})
export class UserTypeWestRoutingModule {}
