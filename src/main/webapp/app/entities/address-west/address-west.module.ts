import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AddressWestComponent } from './list/address-west.component';
import { AddressWestDetailComponent } from './detail/address-west-detail.component';
import { AddressWestUpdateComponent } from './update/address-west-update.component';
import { AddressWestDeleteDialogComponent } from './delete/address-west-delete-dialog.component';
import { AddressWestRoutingModule } from './route/address-west-routing.module';

@NgModule({
  imports: [SharedModule, AddressWestRoutingModule],
  declarations: [AddressWestComponent, AddressWestDetailComponent, AddressWestUpdateComponent, AddressWestDeleteDialogComponent],
})
export class AddressWestModule {}
