import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDistrictWest } from '../district-west.model';
import { DistrictWestService } from '../service/district-west.service';

@Injectable({ providedIn: 'root' })
export class DistrictWestRoutingResolveService implements Resolve<IDistrictWest | null> {
  constructor(protected service: DistrictWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistrictWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((districtWest: HttpResponse<IDistrictWest>) => {
          if (districtWest.body) {
            return of(districtWest.body);
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
