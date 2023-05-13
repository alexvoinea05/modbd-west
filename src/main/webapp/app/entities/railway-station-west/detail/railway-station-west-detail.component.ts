import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRailwayStationWest } from '../railway-station-west.model';

@Component({
  selector: 'jhi-railway-station-west-detail',
  templateUrl: './railway-station-west-detail.component.html',
})
export class RailwayStationWestDetailComponent implements OnInit {
  railwayStationWest: IRailwayStationWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayStationWest }) => {
      this.railwayStationWest = railwayStationWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
