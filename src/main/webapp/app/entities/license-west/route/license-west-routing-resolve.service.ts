import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILicenseWest } from '../license-west.model';
import { LicenseWestService } from '../service/license-west.service';

@Injectable({ providedIn: 'root' })
export class LicenseWestRoutingResolveService implements Resolve<ILicenseWest | null> {
  constructor(protected service: LicenseWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILicenseWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((licenseWest: HttpResponse<ILicenseWest>) => {
          if (licenseWest.body) {
            return of(licenseWest.body);
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
