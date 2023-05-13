import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourneyWest } from '../journey-west.model';

@Component({
  selector: 'jhi-journey-west-detail',
  templateUrl: './journey-west-detail.component.html',
})
export class JourneyWestDetailComponent implements OnInit {
  journeyWest: IJourneyWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyWest }) => {
      this.journeyWest = journeyWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
