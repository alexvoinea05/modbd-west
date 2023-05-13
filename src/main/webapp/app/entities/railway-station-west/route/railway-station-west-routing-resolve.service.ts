import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRailwayStationWest } from '../railway-station-west.model';
import { RailwayStationWestService } from '../service/railway-station-west.service';

@Injectable({ providedIn: 'root' })
export class RailwayStationWestRoutingResolveService implements Resolve<IRailwayStationWest | null> {
  constructor(protected service: RailwayStationWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRailwayStationWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((railwayStationWest: HttpResponse<IRailwayStationWest>) => {
          if (railwayStationWest.body) {
            return of(railwayStationWest.body);
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
