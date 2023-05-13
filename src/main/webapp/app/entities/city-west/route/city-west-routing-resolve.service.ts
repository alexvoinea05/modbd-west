import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICityWest } from '../city-west.model';
import { CityWestService } from '../service/city-west.service';

@Injectable({ providedIn: 'root' })
export class CityWestRoutingResolveService implements Resolve<ICityWest | null> {
  constructor(protected service: CityWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICityWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((cityWest: HttpResponse<ICityWest>) => {
          if (cityWest.body) {
            return of(cityWest.body);
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
