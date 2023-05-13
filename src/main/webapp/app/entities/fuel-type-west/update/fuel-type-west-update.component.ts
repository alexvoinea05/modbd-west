import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FuelTypeWestFormService, FuelTypeWestFormGroup } from './fuel-type-west-form.service';
import { IFuelTypeWest } from '../fuel-type-west.model';
import { FuelTypeWestService } from '../service/fuel-type-west.service';

@Component({
  selector: 'jhi-fuel-type-west-update',
  templateUrl: './fuel-type-west-update.component.html',
})
export class FuelTypeWestUpdateComponent implements OnInit {
  isSaving = false;
  fuelTypeWest: IFuelTypeWest | null = null;

  editForm: FuelTypeWestFormGroup = this.fuelTypeWestFormService.createFuelTypeWestFormGroup();

  constructor(
    protected fuelTypeWestService: FuelTypeWestService,
    protected fuelTypeWestFormService: FuelTypeWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelTypeWest }) => {
      this.fuelTypeWest = fuelTypeWest;
      if (fuelTypeWest) {
        this.updateForm(fuelTypeWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelTypeWest = this.fuelTypeWestFormService.getFuelTypeWest(this.editForm);
    if (fuelTypeWest.id !== null) {
      this.subscribeToSaveResponse(this.fuelTypeWestService.update(fuelTypeWest));
    } else {
      this.subscribeToSaveResponse(this.fuelTypeWestService.create(fuelTypeWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelTypeWest>>): void {
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

  protected updateForm(fuelTypeWest: IFuelTypeWest): void {
    this.fuelTypeWest = fuelTypeWest;
    this.fuelTypeWestFormService.resetForm(this.editForm, fuelTypeWest);
  }
}
