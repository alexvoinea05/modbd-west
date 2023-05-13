import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DistrictWestComponent } from './list/district-west.component';
import { DistrictWestDetailComponent } from './detail/district-west-detail.component';
import { DistrictWestUpdateComponent } from './update/district-west-update.component';
import { DistrictWestDeleteDialogComponent } from './delete/district-west-delete-dialog.component';
import { DistrictWestRoutingModule } from './route/district-west-routing.module';

@NgModule({
  imports: [SharedModule, DistrictWestRoutingModule],
  declarations: [DistrictWestComponent, DistrictWestDetailComponent, DistrictWestUpdateComponent, DistrictWestDeleteDialogComponent],
})
export class DistrictWestModule {}
