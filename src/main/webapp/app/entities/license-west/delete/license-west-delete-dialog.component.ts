import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILicenseWest } from '../license-west.model';
import { LicenseWestService } from '../service/license-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './license-west-delete-dialog.component.html',
})
export class LicenseWestDeleteDialogComponent {
  licenseWest?: ILicenseWest;

  constructor(protected licenseWestService: LicenseWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.licenseWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
