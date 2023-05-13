import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainTypeWestComponent } from './list/train-type-west.component';
import { TrainTypeWestDetailComponent } from './detail/train-type-west-detail.component';
import { TrainTypeWestUpdateComponent } from './update/train-type-west-update.component';
import { TrainTypeWestDeleteDialogComponent } from './delete/train-type-west-delete-dialog.component';
import { TrainTypeWestRoutingModule } from './route/train-type-west-routing.module';

@NgModule({
  imports: [SharedModule, TrainTypeWestRoutingModule],
  declarations: [TrainTypeWestComponent, TrainTypeWestDetailComponent, TrainTypeWestUpdateComponent, TrainTypeWestDeleteDialogComponent],
})
export class TrainTypeWestModule {}
