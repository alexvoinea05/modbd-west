import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRailwayTypeWest } from '../railway-type-west.model';
import { RailwayTypeWestService } from '../service/railway-type-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './railway-type-west-delete-dialog.component.html',
})
export class RailwayTypeWestDeleteDialogComponent {
  railwayTypeWest?: IRailwayTypeWest;

  constructor(protected railwayTypeWestService: RailwayTypeWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.railwayTypeWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
