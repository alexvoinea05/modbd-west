import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompanyWestComponent } from './list/company-west.component';
import { CompanyWestDetailComponent } from './detail/company-west-detail.component';
import { CompanyWestUpdateComponent } from './update/company-west-update.component';
import { CompanyWestDeleteDialogComponent } from './delete/company-west-delete-dialog.component';
import { CompanyWestRoutingModule } from './route/company-west-routing.module';

@NgModule({
  imports: [SharedModule, CompanyWestRoutingModule],
  declarations: [CompanyWestComponent, CompanyWestDetailComponent, CompanyWestUpdateComponent, CompanyWestDeleteDialogComponent],
})
export class CompanyWestModule {}
