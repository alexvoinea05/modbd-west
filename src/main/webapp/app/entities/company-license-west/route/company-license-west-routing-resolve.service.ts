import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanyLicenseWest } from '../company-license-west.model';
import { CompanyLicenseWestService } from '../service/company-license-west.service';

@Injectable({ providedIn: 'root' })
export class CompanyLicenseWestRoutingResolveService implements Resolve<ICompanyLicenseWest | null> {
  constructor(protected service: CompanyLicenseWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyLicenseWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companyLicenseWest: HttpResponse<ICompanyLicenseWest>) => {
          if (companyLicenseWest.body) {
            return of(companyLicenseWest.body);
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
