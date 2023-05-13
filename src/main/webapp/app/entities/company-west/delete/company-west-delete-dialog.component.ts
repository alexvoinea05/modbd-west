import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyWest } from '../company-west.model';
import { CompanyWestService } from '../service/company-west.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './company-west-delete-dialog.component.html',
})
export class CompanyWestDeleteDialogComponent {
  companyWest?: ICompanyWest;

  constructor(protected companyWestService: CompanyWestService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyWestService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
