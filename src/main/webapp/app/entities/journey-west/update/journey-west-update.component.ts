import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JourneyWestFormService, JourneyWestFormGroup } from './journey-west-form.service';
import { IJourneyWest } from '../journey-west.model';
import { JourneyWestService } from '../service/journey-west.service';

@Component({
  selector: 'jhi-journey-west-update',
  templateUrl: './journey-west-update.component.html',
})
export class JourneyWestUpdateComponent implements OnInit {
  isSaving = false;
  journeyWest: IJourneyWest | null = null;

  editForm: JourneyWestFormGroup = this.journeyWestFormService.createJourneyWestFormGroup();

  constructor(
    protected journeyWestService: JourneyWestService,
    protected journeyWestFormService: JourneyWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyWest }) => {
      this.journeyWest = journeyWest;
      if (journeyWest) {
        this.updateForm(journeyWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journeyWest = this.journeyWestFormService.getJourneyWest(this.editForm);
    if (journeyWest.id !== null) {
      this.subscribeToSaveResponse(this.journeyWestService.update(journeyWest));
    } else {
      this.subscribeToSaveResponse(this.journeyWestService.create(journeyWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourneyWest>>): void {
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

  protected updateForm(journeyWest: IJourneyWest): void {
    this.journeyWest = journeyWest;
    this.journeyWestFormService.resetForm(this.editForm, journeyWest);
  }
}
