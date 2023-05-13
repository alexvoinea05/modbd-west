import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUserTypeWest } from '../user-type-west.model';
import { UserTypeWestService } from '../service/user-type-west.service';

@Injectable({ providedIn: 'root' })
export class UserTypeWestRoutingResolveService implements Resolve<IUserTypeWest | null> {
  constructor(protected service: UserTypeWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserTypeWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((userTypeWest: HttpResponse<IUserTypeWest>) => {
          if (userTypeWest.body) {
            return of(userTypeWest.body);
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
