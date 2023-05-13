import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AppUserWestComponent } from './list/app-user-west.component';
import { AppUserWestDetailComponent } from './detail/app-user-west-detail.component';
import { AppUserWestUpdateComponent } from './update/app-user-west-update.component';
import { AppUserWestDeleteDialogComponent } from './delete/app-user-west-delete-dialog.component';
import { AppUserWestRoutingModule } from './route/app-user-west-routing.module';

@NgModule({
  imports: [SharedModule, AppUserWestRoutingModule],
  declarations: [AppUserWestComponent, AppUserWestDetailComponent, AppUserWestUpdateComponent, AppUserWestDeleteDialogComponent],
})
export class AppUserWestModule {}
