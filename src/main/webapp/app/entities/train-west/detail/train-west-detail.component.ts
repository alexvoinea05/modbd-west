import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainWest } from '../train-west.model';

@Component({
  selector: 'jhi-train-west-detail',
  templateUrl: './train-west-detail.component.html',
})
export class TrainWestDetailComponent implements OnInit {
  trainWest: ITrainWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainWest }) => {
      this.trainWest = trainWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
