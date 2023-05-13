import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrainWestFormService, TrainWestFormGroup } from './train-west-form.service';
import { ITrainWest } from '../train-west.model';
import { TrainWestService } from '../service/train-west.service';

@Component({
  selector: 'jhi-train-west-update',
  templateUrl: './train-west-update.component.html',
})
export class TrainWestUpdateComponent implements OnInit {
  isSaving = false;
  trainWest: ITrainWest | null = null;

  editForm: TrainWestFormGroup = this.trainWestFormService.createTrainWestFormGroup();

  constructor(
    protected trainWestService: TrainWestService,
    protected trainWestFormService: TrainWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainWest }) => {
      this.trainWest = trainWest;
      if (trainWest) {
        this.updateForm(trainWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainWest = this.trainWestFormService.getTrainWest(this.editForm);
    if (trainWest.id !== null) {
      this.subscribeToSaveResponse(this.trainWestService.update(trainWest));
    } else {
      this.subscribeToSaveResponse(this.trainWestService.create(trainWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainWest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(trainWest: ITrainWest): void {
    this.trainWest = trainWest;
    this.trainWestFormService.resetForm(this.editForm, trainWest);
  }
}
