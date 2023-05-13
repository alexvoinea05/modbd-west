import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyWest } from '../company-west.model';

@Component({
  selector: 'jhi-company-west-detail',
  templateUrl: './company-west-detail.component.html',
})
export class CompanyWestDetailComponent implements OnInit {
  companyWest: ICompanyWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyWest }) => {
      this.companyWest = companyWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
