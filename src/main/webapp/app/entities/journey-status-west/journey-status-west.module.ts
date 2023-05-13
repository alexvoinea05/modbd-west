import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JourneyStatusWestComponent } from './list/journey-status-west.component';
import { JourneyStatusWestDetailComponent } from './detail/journey-status-west-detail.component';
import { JourneyStatusWestUpdateComponent } from './update/journey-status-west-update.component';
import { JourneyStatusWestDeleteDialogComponent } from './delete/journey-status-west-delete-dialog.component';
import { JourneyStatusWestRoutingModule } from './route/journey-status-west-routing.module';

@NgModule({
  imports: [SharedModule, JourneyStatusWestRoutingModule],
  declarations: [
    JourneyStatusWestComponent,
    JourneyStatusWestDetailComponent,
    JourneyStatusWestUpdateComponent,
    JourneyStatusWestDeleteDialogComponent,
  ],
})
export class JourneyStatusWestModule {}
