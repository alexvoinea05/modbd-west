import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CityWestFormService, CityWestFormGroup } from './city-west-form.service';
import { ICityWest } from '../city-west.model';
import { CityWestService } from '../service/city-west.service';

@Component({
  selector: 'jhi-city-west-update',
  templateUrl: './city-west-update.component.html',
})
export class CityWestUpdateComponent implements OnInit {
  isSaving = false;
  cityWest: ICityWest | null = null;

  editForm: CityWestFormGroup = this.cityWestFormService.createCityWestFormGroup();

  constructor(
    protected cityWestService: CityWestService,
    protected cityWestFormService: CityWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cityWest }) => {
      this.cityWest = cityWest;
      if (cityWest) {
        this.updateForm(cityWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cityWest = this.cityWestFormService.getCityWest(this.editForm);
    if (cityWest.id !== null) {
      this.subscribeToSaveResponse(this.cityWestService.update(cityWest));
    } else {
      this.subscribeToSaveResponse(this.cityWestService.create(cityWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICityWest>>): void {
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

  protected updateForm(cityWest: ICityWest): void {
    this.cityWest = cityWest;
    this.cityWestFormService.resetForm(this.editForm, cityWest);
  }
}
