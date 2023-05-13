import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { CompanyWestFormService, CompanyWestFormGroup } from './company-west-form.service';
import { ICompanyWest } from '../company-west.model';
import { CompanyWestService } from '../service/company-west.service';

@Component({
  selector: 'jhi-company-west-update',
  templateUrl: './company-west-update.component.html',
})
export class CompanyWestUpdateComponent implements OnInit {
  isSaving = false;
  companyWest: ICompanyWest | null = null;

  editForm: CompanyWestFormGroup = this.companyWestFormService.createCompanyWestFormGroup();

  constructor(
    protected companyWestService: CompanyWestService,
    protected companyWestFormService: CompanyWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyWest }) => {
      this.companyWest = companyWest;
      if (companyWest) {
        this.updateForm(companyWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyWest = this.companyWestFormService.getCompanyWest(this.editForm);
    if (companyWest.id !== null) {
      this.subscribeToSaveResponse(this.companyWestService.update(companyWest));
    } else {
      this.subscribeToSaveResponse(this.companyWestService.create(companyWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyWest>>): void {
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

  protected updateForm(companyWest: ICompanyWest): void {
    this.companyWest = companyWest;
    this.companyWestFormService.resetForm(this.editForm, companyWest);
  }
}
