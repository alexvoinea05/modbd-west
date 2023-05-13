import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { AddressWestFormService, AddressWestFormGroup } from './address-west-form.service';
import { IAddressWest } from '../address-west.model';
import { AddressWestService } from '../service/address-west.service';

@Component({
  selector: 'jhi-address-west-update',
  templateUrl: './address-west-update.component.html',
})
export class AddressWestUpdateComponent implements OnInit {
  isSaving = false;
  addressWest: IAddressWest | null = null;

  editForm: AddressWestFormGroup = this.addressWestFormService.createAddressWestFormGroup();

  constructor(
    protected addressWestService: AddressWestService,
    protected addressWestFormService: AddressWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addressWest }) => {
      this.addressWest = addressWest;
      if (addressWest) {
        this.updateForm(addressWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const addressWest = this.addressWestFormService.getAddressWest(this.editForm);
    if (addressWest.id !== null) {
      this.subscribeToSaveResponse(this.addressWestService.update(addressWest));
    } else {
      this.subscribeToSaveResponse(this.addressWestService.create(addressWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressWest>>): void {
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

  protected updateForm(addressWest: IAddressWest): void {
    this.addressWest = addressWest;
    this.addressWestFormService.resetForm(this.editForm, addressWest);
  }
}
