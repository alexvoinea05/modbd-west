import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRailwayStationWest } from '../railway-station-west.model';
import { RailwayStationWestService } from '../service/railway-station-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './railway-station-west-delete-dialog.component.html',
})
export class RailwayStationWestDeleteDialogComponent {
  railwayStationWest?: IRailwayStationWest;

  constructor(protected railwayStationWestService: RailwayStationWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.railwayStationWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
