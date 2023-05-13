import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRailwayTypeWest } from '../railway-type-west.model';
import { RailwayTypeWestService } from '../service/railway-type-west.service';

@Injectable({ providedIn: 'root' })
export class RailwayTypeWestRoutingResolveService implements Resolve<IRailwayTypeWest | null> {
  constructor(protected service: RailwayTypeWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRailwayTypeWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((railwayTypeWest: HttpResponse<IRailwayTypeWest>) => {
          if (railwayTypeWest.body) {
            return of(railwayTypeWest.body);
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
