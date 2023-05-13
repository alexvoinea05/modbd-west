import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TicketWestFormService, TicketWestFormGroup } from './ticket-west-form.service';
import { ITicketWest } from '../ticket-west.model';
import { TicketWestService } from '../service/ticket-west.service';

@Component({
  selector: 'jhi-ticket-west-update',
  templateUrl: './ticket-west-update.component.html',
})
export class TicketWestUpdateComponent implements OnInit {
  isSaving = false;
  ticketWest: ITicketWest | null = null;

  editForm: TicketWestFormGroup = this.ticketWestFormService.createTicketWestFormGroup();

  constructor(
    protected ticketWestService: TicketWestService,
    protected ticketWestFormService: TicketWestFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticketWest }) => {
      this.ticketWest = ticketWest;
      if (ticketWest) {
        this.updateForm(ticketWest);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ticketWest = this.ticketWestFormService.getTicketWest(this.editForm);
    if (ticketWest.id !== null) {
      this.subscribeToSaveResponse(this.ticketWestService.update(ticketWest));
    } else {
      this.subscribeToSaveResponse(this.ticketWestService.create(ticketWest));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITicketWest>>): void {
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

  protected updateForm(ticketWest: ITicketWest): void {
    this.ticketWest = ticketWest;
    this.ticketWestFormService.resetForm(this.editForm, ticketWest);
  }
}
