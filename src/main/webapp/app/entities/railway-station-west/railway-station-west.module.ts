import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RailwayStationWestComponent } from './list/railway-station-west.component';
import { RailwayStationWestDetailComponent } from './detail/railway-station-west-detail.component';
import { RailwayStationWestUpdateComponent } from './update/railway-station-west-update.component';
import { RailwayStationWestDeleteDialogComponent } from './delete/railway-station-west-delete-dialog.component';
import { RailwayStationWestRoutingModule } from './route/railway-station-west-routing.module';

@NgModule({
  imports: [SharedModule, RailwayStationWestRoutingModule],
  declarations: [
    RailwayStationWestComponent,
    RailwayStationWestDetailComponent,
    RailwayStationWestUpdateComponent,
    RailwayStationWestDeleteDialogComponent,
  ],
})
export class RailwayStationWestModule {}
