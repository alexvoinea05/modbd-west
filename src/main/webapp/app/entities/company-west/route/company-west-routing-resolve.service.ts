import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanyWest } from '../company-west.model';
import { CompanyWestService } from '../service/company-west.service';

@Injectable({ providedIn: 'root' })
export class CompanyWestRoutingResolveService implements Resolve<ICompanyWest | null> {
  constructor(protected service: CompanyWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companyWest: HttpResponse<ICompanyWest>) => {
          if (companyWest.body) {
            return of(companyWest.body);
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
