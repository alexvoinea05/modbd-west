import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CityWestComponent } from './list/city-west.component';
import { CityWestDetailComponent } from './detail/city-west-detail.component';
import { CityWestUpdateComponent } from './update/city-west-update.component';
import { CityWestDeleteDialogComponent } from './delete/city-west-delete-dialog.component';
import { CityWestRoutingModule } from './route/city-west-routing.module';

@NgModule({
  imports: [SharedModule, CityWestRoutingModule],
  declarations: [CityWestComponent, CityWestDetailComponent, CityWestUpdateComponent, CityWestDeleteDialogComponent],
})
export class CityWestModule {}
