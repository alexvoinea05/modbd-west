import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserTypeWest } from '../user-type-west.model';
import { UserTypeWestService } from '../service/user-type-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './user-type-west-delete-dialog.component.html',
})
export class UserTypeWestDeleteDialogComponent {
  userTypeWest?: IUserTypeWest;

  constructor(protected userTypeWestService: UserTypeWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userTypeWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
