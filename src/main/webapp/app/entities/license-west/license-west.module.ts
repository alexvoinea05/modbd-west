import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { LicenseWestComponent } from './list/license-west.component';
import { LicenseWestDetailComponent } from './detail/license-west-detail.component';
import { LicenseWestUpdateComponent } from './update/license-west-update.component';
import { LicenseWestDeleteDialogComponent } from './delete/license-west-delete-dialog.component';
import { LicenseWestRoutingModule } from './route/license-west-routing.module';

@NgModule({
  imports: [SharedModule, LicenseWestRoutingModule],
  declarations: [LicenseWestComponent, LicenseWestDetailComponent, LicenseWestUpdateComponent, LicenseWestDeleteDialogComponent],
})
export class LicenseWestModule {}
