import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainTypeWest } from '../train-type-west.model';
import { TrainTypeWestService } from '../service/train-type-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './train-type-west-delete-dialog.component.html',
})
export class TrainTypeWestDeleteDialogComponent {
  trainTypeWest?: ITrainTypeWest;

  constructor(protected trainTypeWestService: TrainTypeWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainTypeWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
