import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AppUserWestFormService, AppUserWestFormGroup } from './app-user-west-form.service';
import { IAppUserWest } from '../app-user-west.model';
import { AppUserWestService } from '../service/app-user-west.service';

@Component({
  selector: 'jhi-app-user-west-update',
  templateUrl: './app-user-west-update.component.html',
})
export class AppUserWestUpdateComponent implements OnInit {
  isSaving = false;
  appUserWest: IAppUserWest | null = null;

  editForm: AppUserWestFormGroup = this.appUserWestFormService.createAppUserWestFormGroup();

  constructor(
    protected appUserWestService: AppUserWestService,
    protected appUserWestFormService: AppUserWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appUserWest }) => {
      this.appUserWest = appUserWest;
      if (appUserWest) {
        this.updateForm(appUserWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appUserWest = this.appUserWestFormService.getAppUserWest(this.editForm);
    if (appUserWest.id !== null) {
      this.subscribeToSaveResponse(this.appUserWestService.update(appUserWest));
    } else {
      this.subscribeToSaveResponse(this.appUserWestService.create(appUserWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppUserWest>>): void {
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

  protected updateForm(appUserWest: IAppUserWest): void {
    this.appUserWest = appUserWest;
    this.appUserWestFormService.resetForm(this.editForm, appUserWest);
  }
}
