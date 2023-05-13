import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelTypeWest } from '../fuel-type-west.model';
import { FuelTypeWestService } from '../service/fuel-type-west.service';

@Injectable({ providedIn: 'root' })
export class FuelTypeWestRoutingResolveService implements Resolve<IFuelTypeWest | null> {
  constructor(protected service: FuelTypeWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFuelTypeWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fuelTypeWest: HttpResponse<IFuelTypeWest>) => {
          if (fuelTypeWest.body) {
            return of(fuelTypeWest.body);
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
