import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TicketWestComponent } from '../list/ticket-west.component';
import { TicketWestDetailComponent } from '../detail/ticket-west-detail.component';
import { TicketWestUpdateComponent } from '../update/ticket-west-update.component';
import { TicketWestRoutingResolveService } from './ticket-west-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const ticketWestRoute: Routes = [
  {
    path: '',
    component: TicketWestComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TicketWestDetailComponent,
    resolve: {
      ticketWest: TicketWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TicketWestUpdateComponent,
    resolve: {
      ticketWest: TicketWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TicketWestUpdateComponent,
    resolve: {
      ticketWest: TicketWestRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ticketWestRoute)],
  exports: [RouterModule],
})
export class TicketWestRoutingModule {}
