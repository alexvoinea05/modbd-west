import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAddressWest } from '../address-west.model';
import { AddressWestService } from '../service/address-west.service';

@Injectable({ providedIn: 'root' })
export class AddressWestRoutingResolveService implements Resolve<IAddressWest | null> {
  constructor(protected service: AddressWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddressWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((addressWest: HttpResponse<IAddressWest>) => {
          if (addressWest.body) {
            return of(addressWest.body);
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
