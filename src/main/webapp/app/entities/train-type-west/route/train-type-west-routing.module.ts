import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainTypeWestComponent } from '../list/train-type-west.component';
import { TrainTypeWestDetailComponent } from '../detail/train-type-west-detail.component';
import { TrainTypeWestUpdateComponent } from '../update/train-type-west-update.component';
import { TrainTypeWestRoutingResolveService } from './train-type-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainTypeWestRoute: Routes = [
  {
    path: '',
    component: TrainTypeWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainTypeWestDetailComponent,
    resolve: {
      trainTypeWest: TrainTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainTypeWestUpdateComponent,
    resolve: {
      trainTypeWest: TrainTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainTypeWestUpdateComponent,
    resolve: {
      trainTypeWest: TrainTypeWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainTypeWestRoute)],
  exports: [RouterModule],
})
export class TrainTypeWestRoutingModule {}
