import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppUserWest } from '../app-user-west.model';

@Component({
  selector: 'jhi-app-user-west-detail',
  templateUrl: './app-user-west-detail.component.html',
})
export class AppUserWestDetailComponent implements OnInit {
  appUserWest: IAppUserWest | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appUserWest }) => {
      this.appUserWest = appUserWest;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
