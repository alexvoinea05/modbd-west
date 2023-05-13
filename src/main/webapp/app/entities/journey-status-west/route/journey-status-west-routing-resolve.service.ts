import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourneyStatusWest } from '../journey-status-west.model';
import { JourneyStatusWestService } from '../service/journey-status-west.service';

@Injectable({ providedIn: 'root' })
export class JourneyStatusWestRoutingResolveService implements Resolve<IJourneyStatusWest | null> {
  constructor(protected service: JourneyStatusWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourneyStatusWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((journeyStatusWest: HttpResponse<IJourneyStatusWest>) => {
          if (journeyStatusWest.body) {
            return of(journeyStatusWest.body);
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
