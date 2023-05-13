import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyLicenseWest } from '../company-license-west.model';

@Component({
  selector: 'jhi-company-license-west-detail',
  templateUrl: './company-license-west-detail.component.html',
})
export class CompanyLicenseWestDetailComponent implements OnInit {
  companyLicenseWest: ICompanyLicenseWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyLicenseWest }) => {
      this.companyLicenseWest = companyLicenseWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
