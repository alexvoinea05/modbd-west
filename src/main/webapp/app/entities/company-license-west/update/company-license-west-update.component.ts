import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CompanyLicenseWestFormService, CompanyLicenseWestFormGroup } from './company-license-west-form.service';
import { ICompanyLicenseWest } from '../company-license-west.model';
import { CompanyLicenseWestService } from '../service/company-license-west.service';
import { ICompanyWest } from 'app/entities/company-west/company-west.model';
import { CompanyWestService } from 'app/entities/company-west/service/company-west.service';
import { ILicenseWest } from 'app/entities/license-west/license-west.model';
import { LicenseWestService } from 'app/entities/license-west/service/license-west.service';

@Component({
  selector: 'jhi-company-license-west-update',
  templateUrl: './company-license-west-update.component.html',
})
export class CompanyLicenseWestUpdateComponent implements OnInit {
  isSaving = false;
  companyLicenseWest: ICompanyLicenseWest | null = null;

  companyWestsSharedCollection: ICompanyWest[] = [];
  licenseWestsSharedCollection: ILicenseWest[] = [];

  editForm: CompanyLicenseWestFormGroup = this.companyLicenseWestFormService.createCompanyLicenseWestFormGroup();

  constructor(
    protected companyLicenseWestService: CompanyLicenseWestService,
    protected companyLicenseWestFormService: CompanyLicenseWestFormService,
    protected companyWestService: CompanyWestService,
    protected licenseWestService: LicenseWestService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompanyWest = (o1: ICompanyWest | null, o2: ICompanyWest | null): boolean => this.companyWestService.compareCompanyWest(o1, o2);

  compareLicenseWest = (o1: ILicenseWest | null, o2: ILicenseWest | null): boolean => this.licenseWestService.compareLicenseWest(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyLicenseWest }) => {
      this.companyLicenseWest = companyLicenseWest;
      if (companyLicenseWest) {
        this.updateForm(companyLicenseWest);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyLicenseWest = this.companyLicenseWestFormService.getCompanyLicenseWest(this.editForm);
    if (companyLicenseWest.id !== null) {
      this.subscribeToSaveResponse(this.companyLicenseWestService.update(companyLicenseWest));
    } else {
      this.subscribeToSaveResponse(this.companyLicenseWestService.create(companyLicenseWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyLicenseWest>>): void {
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

  protected updateForm(companyLicenseWest: ICompanyLicenseWest): void {
    this.companyLicenseWest = companyLicenseWest;
    this.companyLicenseWestFormService.resetForm(this.editForm, companyLicenseWest);

    this.companyWestsSharedCollection = this.companyWestService.addCompanyWestToCollectionIfMissing<ICompanyWest>(
      this.companyWestsSharedCollection,
      companyLicenseWest.idCompany
    );
    this.licenseWestsSharedCollection = this.licenseWestService.addLicenseWestToCollectionIfMissing<ILicenseWest>(
      this.licenseWestsSharedCollection,
      companyLicenseWest.idLicense
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyWestService
      .query()
      .pipe(map((res: HttpResponse<ICompanyWest[]>) => res.body ?? []))
      .pipe(
        map((companyWests: ICompanyWest[]) =>
          this.companyWestService.addCompanyWestToCollectionIfMissing<ICompanyWest>(companyWests, this.companyLicenseWest?.idCompany)
        )
      )
      .subscribe((companyWests: ICompanyWest[]) => (this.companyWestsSharedCollection = companyWests));

    this.licenseWestService
      .query()
      .pipe(map((res: HttpResponse<ILicenseWest[]>) => res.body ?? []))
      .pipe(
        map((licenseWests: ILicenseWest[]) =>
          this.licenseWestService.addLicenseWestToCollectionIfMissing<ILicenseWest>(licenseWests, this.companyLicenseWest?.idLicense)
        )
      )
      .subscribe((licenseWests: ILicenseWest[]) => (this.licenseWestsSharedCollection = licenseWests));
  }
}
