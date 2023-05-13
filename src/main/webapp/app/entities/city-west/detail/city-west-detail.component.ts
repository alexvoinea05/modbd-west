import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICityWest } from '../city-west.model';

@Component({
  selector: 'jhi-city-west-detail',
  templateUrl: './city-west-detail.component.html',
})
export class CityWestDetailComponent implements OnInit {
  cityWest: ICityWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cityWest }) => {
      this.cityWest = cityWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
