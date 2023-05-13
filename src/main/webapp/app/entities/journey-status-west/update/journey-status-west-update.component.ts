import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JourneyStatusWestFormService, JourneyStatusWestFormGroup } from './journey-status-west-form.service';
import { IJourneyStatusWest } from '../journey-status-west.model';
import { JourneyStatusWestService } from '../service/journey-status-west.service';

@Component({
  selector: 'jhi-journey-status-west-update',
  templateUrl: './journey-status-west-update.component.html',
})
export class JourneyStatusWestUpdateComponent implements OnInit {
  isSaving = false;
  journeyStatusWest: IJourneyStatusWest | null = null;

  editForm: JourneyStatusWestFormGroup = this.journeyStatusWestFormService.createJourneyStatusWestFormGroup();

  constructor(
    protected journeyStatusWestService: JourneyStatusWestService,
    protected journeyStatusWestFormService: JourneyStatusWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyStatusWest }) => {
      this.journeyStatusWest = journeyStatusWest;
      if (journeyStatusWest) {
        this.updateForm(journeyStatusWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journeyStatusWest = this.journeyStatusWestFormService.getJourneyStatusWest(this.editForm);
    if (journeyStatusWest.id !== null) {
      this.subscribeToSaveResponse(this.journeyStatusWestService.update(journeyStatusWest));
    } else {
      this.subscribeToSaveResponse(this.journeyStatusWestService.create(journeyStatusWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourneyStatusWest>>): void {
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

  protected updateForm(journeyStatusWest: IJourneyStatusWest): void {
    this.journeyStatusWest = journeyStatusWest;
    this.journeyStatusWestFormService.resetForm(this.editForm, journeyStatusWest);
  }
}
