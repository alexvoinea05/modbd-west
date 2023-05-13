import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { LicenseWestFormService, LicenseWestFormGroup } from './license-west-form.service';
import { ILicenseWest } from '../license-west.model';
import { LicenseWestService } from '../service/license-west.service';

@Component({
  selector: 'jhi-license-west-update',
  templateUrl: './license-west-update.component.html',
})
export class LicenseWestUpdateComponent implements OnInit {
  isSaving = false;
  licenseWest: ILicenseWest | null = null;

  editForm: LicenseWestFormGroup = this.licenseWestFormService.createLicenseWestFormGroup();

  constructor(
    protected licenseWestService: LicenseWestService,
    protected licenseWestFormService: LicenseWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ licenseWest }) => {
      this.licenseWest = licenseWest;
      if (licenseWest) {
        this.updateForm(licenseWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const licenseWest = this.licenseWestFormService.getLicenseWest(this.editForm);
    if (licenseWest.id !== null) {
      this.subscribeToSaveResponse(this.licenseWestService.update(licenseWest));
    } else {
      this.subscribeToSaveResponse(this.licenseWestService.create(licenseWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILicenseWest>>): void {
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

  protected updateForm(licenseWest: ILicenseWest): void {
    this.licenseWest = licenseWest;
    this.licenseWestFormService.resetForm(this.editForm, licenseWest);
  }
}
