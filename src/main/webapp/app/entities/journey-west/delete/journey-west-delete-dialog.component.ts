import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJourneyWest } from '../journey-west.model';
import { JourneyWestService } from '../service/journey-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './journey-west-delete-dialog.component.html',
})
export class JourneyWestDeleteDialogComponent {
  journeyWest?: IJourneyWest;

  constructor(protected journeyWestService: JourneyWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.journeyWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
