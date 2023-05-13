import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistrictWest } from '../district-west.model';
import { DistrictWestService } from '../service/district-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './district-west-delete-dialog.component.html',
})
export class DistrictWestDeleteDialogComponent {
  districtWest?: IDistrictWest;

  constructor(protected districtWestService: DistrictWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.districtWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
