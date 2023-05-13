import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { UserTypeWestFormService, UserTypeWestFormGroup } from './user-type-west-form.service';
import { IUserTypeWest } from '../user-type-west.model';
import { UserTypeWestService } from '../service/user-type-west.service';

@Component({
  selector: 'jhi-user-type-west-update',
  templateUrl: './user-type-west-update.component.html',
})
export class UserTypeWestUpdateComponent implements OnInit {
  isSaving = false;
  userTypeWest: IUserTypeWest | null = null;

  editForm: UserTypeWestFormGroup = this.userTypeWestFormService.createUserTypeWestFormGroup();

  constructor(
    protected userTypeWestService: UserTypeWestService,
    protected userTypeWestFormService: UserTypeWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userTypeWest }) => {
      this.userTypeWest = userTypeWest;
      if (userTypeWest) {
        this.updateForm(userTypeWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userTypeWest = this.userTypeWestFormService.getUserTypeWest(this.editForm);
    if (userTypeWest.id !== null) {
      this.subscribeToSaveResponse(this.userTypeWestService.update(userTypeWest));
    } else {
      this.subscribeToSaveResponse(this.userTypeWestService.create(userTypeWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserTypeWest>>): void {
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

  protected updateForm(userTypeWest: IUserTypeWest): void {
    this.userTypeWest = userTypeWest;
    this.userTypeWestFormService.resetForm(this.editForm, userTypeWest);
  }
}
