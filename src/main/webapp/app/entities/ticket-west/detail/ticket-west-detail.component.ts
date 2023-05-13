import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITicketWest } from '../ticket-west.model';

@Component({
  selector: 'jhi-ticket-west-detail',
  templateUrl: './ticket-west-detail.component.html',
})
export class TicketWestDetailComponent implements OnInit {
  ticketWest: ITicketWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ticketWest }) => {
      this.ticketWest = ticketWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
