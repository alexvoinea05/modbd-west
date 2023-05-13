import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistrictWest } from '../district-west.model';

@Component({
  selector: 'jhi-district-west-detail',
  templateUrl: './district-west-detail.component.html',
})
export class DistrictWestDetailComponent implements OnInit {
  districtWest: IDistrictWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtWest }) => {
      this.districtWest = districtWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
