import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { UserTypeWestComponent } from './list/user-type-west.component';
import { UserTypeWestDetailComponent } from './detail/user-type-west-detail.component';
import { UserTypeWestUpdateComponent } from './update/user-type-west-update.component';
import { UserTypeWestDeleteDialogComponent } from './delete/user-type-west-delete-dialog.component';
import { UserTypeWestRoutingModule } from './route/user-type-west-routing.module';

@NgModule({
  imports: [SharedModule, UserTypeWestRoutingModule],
  declarations: [UserTypeWestComponent, UserTypeWestDetailComponent, UserTypeWestUpdateComponent, UserTypeWestDeleteDialogComponent],
})
export class UserTypeWestModule {}
