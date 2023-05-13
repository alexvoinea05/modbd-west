import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITicketWest } from '../ticket-west.model';
import { TicketWestService } from '../service/ticket-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './ticket-west-delete-dialog.component.html',
})
export class TicketWestDeleteDialogComponent {
  ticketWest?: ITicketWest;

  constructor(protected ticketWestService: TicketWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ticketWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
