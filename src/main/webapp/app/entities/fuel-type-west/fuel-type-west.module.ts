import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FuelTypeWestComponent } from './list/fuel-type-west.component';
import { FuelTypeWestDetailComponent } from './detail/fuel-type-west-detail.component';
import { FuelTypeWestUpdateComponent } from './update/fuel-type-west-update.component';
import { FuelTypeWestDeleteDialogComponent } from './delete/fuel-type-west-delete-dialog.component';
import { FuelTypeWestRoutingModule } from './route/fuel-type-west-routing.module';

@NgModule({
  imports: [SharedModule, FuelTypeWestRoutingModule],
  declarations: [FuelTypeWestComponent, FuelTypeWestDetailComponent, FuelTypeWestUpdateComponent, FuelTypeWestDeleteDialogComponent],
})
export class FuelTypeWestModule {}
