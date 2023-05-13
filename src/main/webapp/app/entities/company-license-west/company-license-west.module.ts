import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompanyLicenseWestComponent } from './list/company-license-west.component';
import { CompanyLicenseWestDetailComponent } from './detail/company-license-west-detail.component';
import { CompanyLicenseWestUpdateComponent } from './update/company-license-west-update.component';
import { CompanyLicenseWestDeleteDialogComponent } from './delete/company-license-west-delete-dialog.component';
import { CompanyLicenseWestRoutingModule } from './route/company-license-west-routing.module';

@NgModule({
  imports: [SharedModule, CompanyLicenseWestRoutingModule],
  declarations: [
    CompanyLicenseWestComponent,
    CompanyLicenseWestDetailComponent,
    CompanyLicenseWestUpdateComponent,
    CompanyLicenseWestDeleteDialogComponent,
  ],
})
export class CompanyLicenseWestModule {}
