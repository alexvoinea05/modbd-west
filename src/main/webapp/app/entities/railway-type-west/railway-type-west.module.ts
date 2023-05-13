import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RailwayTypeWestComponent } from './list/railway-type-west.component';
import { RailwayTypeWestDetailComponent } from './detail/railway-type-west-detail.component';
import { RailwayTypeWestUpdateComponent } from './update/railway-type-west-update.component';
import { RailwayTypeWestDeleteDialogComponent } from './delete/railway-type-west-delete-dialog.component';
import { RailwayTypeWestRoutingModule } from './route/railway-type-west-routing.module';

@NgModule({
  imports: [SharedModule, RailwayTypeWestRoutingModule],
  declarations: [
    RailwayTypeWestComponent,
    RailwayTypeWestDetailComponent,
    RailwayTypeWestUpdateComponent,
    RailwayTypeWestDeleteDialogComponent,
  ],
})
export class RailwayTypeWestModule {}
