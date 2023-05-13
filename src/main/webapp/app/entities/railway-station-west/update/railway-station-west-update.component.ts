import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RailwayStationWestFormService, RailwayStationWestFormGroup } from './railway-station-west-form.service';
import { IRailwayStationWest } from '../railway-station-west.model';
import { RailwayStationWestService } from '../service/railway-station-west.service';

@Component({
  selector: 'jhi-railway-station-west-update',
  templateUrl: './railway-station-west-update.component.html',
})
export class RailwayStationWestUpdateComponent implements OnInit {
  isSaving = false;
  railwayStationWest: IRailwayStationWest | null = null;

  editForm: RailwayStationWestFormGroup = this.railwayStationWestFormService.createRailwayStationWestFormGroup();

  constructor(
    protected railwayStationWestService: RailwayStationWestService,
    protected railwayStationWestFormService: RailwayStationWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayStationWest }) => {
      this.railwayStationWest = railwayStationWest;
      if (railwayStationWest) {
        this.updateForm(railwayStationWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const railwayStationWest = this.railwayStationWestFormService.getRailwayStationWest(this.editForm);
    if (railwayStationWest.id !== null) {
      this.subscribeToSaveResponse(this.railwayStationWestService.update(railwayStationWest));
    } else {
      this.subscribeToSaveResponse(this.railwayStationWestService.create(railwayStationWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRailwayStationWest>>): void {
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

  protected updateForm(railwayStationWest: IRailwayStationWest): void {
    this.railwayStationWest = railwayStationWest;
    this.railwayStationWestFormService.resetForm(this.editForm, railwayStationWest);
  }
}
