import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserTypeWest } from '../user-type-west.model';

@Component({
  selector: 'jhi-user-type-west-detail',
  templateUrl: './user-type-west-detail.component.html',
})
export class UserTypeWestDetailComponent implements OnInit {
  userTypeWest: IUserTypeWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userTypeWest }) => {
      this.userTypeWest = userTypeWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
