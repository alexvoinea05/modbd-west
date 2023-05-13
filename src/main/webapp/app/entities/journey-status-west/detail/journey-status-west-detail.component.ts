import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourneyStatusWest } from '../journey-status-west.model';

@Component({
  selector: 'jhi-journey-status-west-detail',
  templateUrl: './journey-status-west-detail.component.html',
})
export class JourneyStatusWestDetailComponent implements OnInit {
  journeyStatusWest: IJourneyStatusWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyStatusWest }) => {
      this.journeyStatusWest = journeyStatusWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
