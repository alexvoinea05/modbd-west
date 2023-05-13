import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyLicenseWest } from '../company-license-west.model';
import { CompanyLicenseWestService } from '../service/company-license-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './company-license-west-delete-dialog.component.html',
})
export class CompanyLicenseWestDeleteDialogComponent {
  companyLicenseWest?: ICompanyLicenseWest;

  constructor(protected companyLicenseWestService: CompanyLicenseWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyLicenseWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
