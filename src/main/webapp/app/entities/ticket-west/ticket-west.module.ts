import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TicketWestComponent } from './list/ticket-west.component';
import { TicketWestDetailComponent } from './detail/ticket-west-detail.component';
import { TicketWestUpdateComponent } from './update/ticket-west-update.component';
import { TicketWestDeleteDialogComponent } from './delete/ticket-west-delete-dialog.component';
import { TicketWestRoutingModule } from './route/ticket-west-routing.module';

@NgModule({
  imports: [SharedModule, TicketWestRoutingModule],
  declarations: [TicketWestComponent, TicketWestDetailComponent, TicketWestUpdateComponent, TicketWestDeleteDialogComponent],
})
export class TicketWestModule {}
