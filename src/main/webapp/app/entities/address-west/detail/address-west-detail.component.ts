import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAddressWest } from '../address-west.model';

@Component({
  selector: 'jhi-address-west-detail',
  templateUrl: './address-west-detail.component.html',
})
export class AddressWestDetailComponent implements OnInit {
  addressWest: IAddressWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ addressWest }) => {
      this.addressWest = addressWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
