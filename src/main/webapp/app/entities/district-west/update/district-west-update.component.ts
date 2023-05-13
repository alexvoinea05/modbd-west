import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { DistrictWestFormService, DistrictWestFormGroup } from './district-west-form.service';
import { IDistrictWest } from '../district-west.model';
import { DistrictWestService } from '../service/district-west.service';

@Component({
  selector: 'jhi-district-west-update',
  templateUrl: './district-west-update.component.html',
})
export class DistrictWestUpdateComponent implements OnInit {
  isSaving = false;
  districtWest: IDistrictWest | null = null;

  editForm: DistrictWestFormGroup = this.districtWestFormService.createDistrictWestFormGroup();

  constructor(
    protected districtWestService: DistrictWestService,
    protected districtWestFormService: DistrictWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ districtWest }) => {
      this.districtWest = districtWest;
      if (districtWest) {
        this.updateForm(districtWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const districtWest = this.districtWestFormService.getDistrictWest(this.editForm);
    if (districtWest.id !== null) {
      this.subscribeToSaveResponse(this.districtWestService.update(districtWest));
    } else {
      this.subscribeToSaveResponse(this.districtWestService.create(districtWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistrictWest>>): void {
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

  protected updateForm(districtWest: IDistrictWest): void {
    this.districtWest = districtWest;
    this.districtWestFormService.resetForm(this.editForm, districtWest);
  }
}
