import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainTypeWest } from '../train-type-west.model';

@Component({
  selector: 'jhi-train-type-west-detail',
  templateUrl: './train-type-west-detail.component.html',
})
export class TrainTypeWestDetailComponent implements OnInit {
  trainTypeWest: ITrainTypeWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainTypeWest }) => {
      this.trainTypeWest = trainTypeWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
