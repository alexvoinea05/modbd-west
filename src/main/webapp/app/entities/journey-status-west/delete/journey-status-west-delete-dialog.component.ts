import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJourneyStatusWest } from '../journey-status-west.model';
import { JourneyStatusWestService } from '../service/journey-status-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './journey-status-west-delete-dialog.component.html',
})
export class JourneyStatusWestDeleteDialogComponent {
  journeyStatusWest?: IJourneyStatusWest;

  constructor(protected journeyStatusWestService: JourneyStatusWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.journeyStatusWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
