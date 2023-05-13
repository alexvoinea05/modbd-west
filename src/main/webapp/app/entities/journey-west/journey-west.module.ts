import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JourneyWestComponent } from './list/journey-west.component';
import { JourneyWestDetailComponent } from './detail/journey-west-detail.component';
import { JourneyWestUpdateComponent } from './update/journey-west-update.component';
import { JourneyWestDeleteDialogComponent } from './delete/journey-west-delete-dialog.component';
import { JourneyWestRoutingModule } from './route/journey-west-routing.module';

@NgModule({
  imports: [SharedModule, JourneyWestRoutingModule],
  declarations: [JourneyWestComponent, JourneyWestDetailComponent, JourneyWestUpdateComponent, JourneyWestDeleteDialogComponent],
})
export class JourneyWestModule {}
