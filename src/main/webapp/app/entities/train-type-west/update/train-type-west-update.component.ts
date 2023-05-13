import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrainTypeWestFormService, TrainTypeWestFormGroup } from './train-type-west-form.service';
import { ITrainTypeWest } from '../train-type-west.model';
import { TrainTypeWestService } from '../service/train-type-west.service';

@Component({
  selector: 'jhi-train-type-west-update',
  templateUrl: './train-type-west-update.component.html',
})
export class TrainTypeWestUpdateComponent implements OnInit {
  isSaving = false;
  trainTypeWest: ITrainTypeWest | null = null;

  editForm: TrainTypeWestFormGroup = this.trainTypeWestFormService.createTrainTypeWestFormGroup();

  constructor(
    protected trainTypeWestService: TrainTypeWestService,
    protected trainTypeWestFormService: TrainTypeWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainTypeWest }) => {
      this.trainTypeWest = trainTypeWest;
      if (trainTypeWest) {
        this.updateForm(trainTypeWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainTypeWest = this.trainTypeWestFormService.getTrainTypeWest(this.editForm);
    if (trainTypeWest.id !== null) {
      this.subscribeToSaveResponse(this.trainTypeWestService.update(trainTypeWest));
    } else {
      this.subscribeToSaveResponse(this.trainTypeWestService.create(trainTypeWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainTypeWest>>): void {
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

  protected updateForm(trainTypeWest: ITrainTypeWest): void {
    this.trainTypeWest = trainTypeWest;
    this.trainTypeWestFormService.resetForm(this.editForm, trainTypeWest);
  }
}
