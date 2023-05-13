import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuelTypeWest } from '../fuel-type-west.model';

@Component({
  selector: 'jhi-fuel-type-west-detail',
  templateUrl: './fuel-type-west-detail.component.html',
})
export class FuelTypeWestDetailComponent implements OnInit {
  fuelTypeWest: IFuelTypeWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTypeWest }) => {
      this.fuelTypeWest = fuelTypeWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
