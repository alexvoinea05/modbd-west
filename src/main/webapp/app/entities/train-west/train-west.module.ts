import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainWestComponent } from './list/train-west.component';
import { TrainWestDetailComponent } from './detail/train-west-detail.component';
import { TrainWestUpdateComponent } from './update/train-west-update.component';
import { TrainWestDeleteDialogComponent } from './delete/train-west-delete-dialog.component';
import { TrainWestRoutingModule } from './route/train-west-routing.module';

@NgModule({
  imports: [SharedModule, TrainWestRoutingModule],
  declarations: [TrainWestComponent, TrainWestDetailComponent, TrainWestUpdateComponent, TrainWestDeleteDialogComponent],
})
export class TrainWestModule {}
