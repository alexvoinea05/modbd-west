import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RailwayTypeWestFormService, RailwayTypeWestFormGroup } from './railway-type-west-form.service';
import { IRailwayTypeWest } from '../railway-type-west.model';
import { RailwayTypeWestService } from '../service/railway-type-west.service';

@Component({
  selector: 'jhi-railway-type-west-update',
  templateUrl: './railway-type-west-update.component.html',
})
export class RailwayTypeWestUpdateComponent implements OnInit {
  isSaving = false;
  railwayTypeWest: IRailwayTypeWest | null = null;

  editForm: RailwayTypeWestFormGroup = this.railwayTypeWestFormService.createRailwayTypeWestFormGroup();

  constructor(
    protected railwayTypeWestService: RailwayTypeWestService,
    protected railwayTypeWestFormService: RailwayTypeWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayTypeWest }) => {
      this.railwayTypeWest = railwayTypeWest;
      if (railwayTypeWest) {
        this.updateForm(railwayTypeWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const railwayTypeWest = this.railwayTypeWestFormService.getRailwayTypeWest(this.editForm);
    if (railwayTypeWest.id !== null) {
      this.subscribeToSaveResponse(this.railwayTypeWestService.update(railwayTypeWest));
    } else {
      this.subscribeToSaveResponse(this.railwayTypeWestService.create(railwayTypeWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRailwayTypeWest>>): void {
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

  protected updateForm(railwayTypeWest: IRailwayTypeWest): void {
    this.railwayTypeWest = railwayTypeWest;
    this.railwayTypeWestFormService.resetForm(this.editForm, railwayTypeWest);
  }
}
