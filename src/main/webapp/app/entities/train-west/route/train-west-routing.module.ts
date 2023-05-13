import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainWestComponent } from '../list/train-west.component';
import { TrainWestDetailComponent } from '../detail/train-west-detail.component';
import { TrainWestUpdateComponent } from '../update/train-west-update.component';
import { TrainWestRoutingResolveService } from './train-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainWestRoute: Routes = [
  {
    path: '',
    component: TrainWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainWestDetailComponent,
    resolve: {
      trainWest: TrainWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainWestUpdateComponent,
    resolve: {
      trainWest: TrainWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainWestUpdateComponent,
    resolve: {
      trainWest: TrainWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainWestRoute)],
  exports: [RouterModule],
})
export class TrainWestRoutingModule {}
