import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFuelTypeWest } from '../fuel-type-west.model';
import { FuelTypeWestService } from '../service/fuel-type-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fuel-type-west-delete-dialog.component.html',
})
export class FuelTypeWestDeleteDialogComponent {
  fuelTypeWest?: IFuelTypeWest;

  constructor(protected fuelTypeWestService: FuelTypeWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelTypeWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
