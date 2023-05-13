import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAppUserWest } from '../app-user-west.model';
import { AppUserWestService } from '../service/app-user-west.service';

@Injectable({ providedIn: 'root' })
export class AppUserWestRoutingResolveService implements Resolve<IAppUserWest | null> {
  constructor(protected service: AppUserWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAppUserWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((appUserWest: HttpResponse<IAppUserWest>) => {
          if (appUserWest.body) {
            return of(appUserWest.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
