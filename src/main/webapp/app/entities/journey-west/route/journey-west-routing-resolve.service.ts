import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourneyWest } from '../journey-west.model';
import { JourneyWestService } from '../service/journey-west.service';

@Injectable({ providedIn: 'root' })
export class JourneyWestRoutingResolveService implements Resolve<IJourneyWest | null> {
  constructor(protected service: JourneyWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourneyWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((journeyWest: HttpResponse<IJourneyWest>) => {
          if (journeyWest.body) {
            return of(journeyWest.body);
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
