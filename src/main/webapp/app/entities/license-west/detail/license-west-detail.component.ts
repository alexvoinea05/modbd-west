import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILicenseWest } from '../license-west.model';

@Component({
  selector: 'jhi-license-west-detail',
  templateUrl: './license-west-detail.component.html',
})
export class LicenseWestDetailComponent implements OnInit {
  licenseWest: ILicenseWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licenseWest }) => {
      this.licenseWest = licenseWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
