import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRailwayTypeWest } from '../railway-type-west.model';

@Component({
  selector: 'jhi-railway-type-west-detail',
  templateUrl: './railway-type-west-detail.component.html',
})
export class RailwayTypeWestDetailComponent implements OnInit {
  railwayTypeWest: IRailwayTypeWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayTypeWest }) => {
      this.railwayTypeWest = railwayTypeWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
