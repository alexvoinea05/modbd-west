import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITicketWest } from '../ticket-west.model';
import { TicketWestService } from '../service/ticket-west.service';

@Injectable({ providedIn: 'root' })
export class TicketWestRoutingResolveService implements Resolve<ITicketWest | null> {
  constructor(protected service: TicketWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITicketWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ticketWest: HttpResponse<ITicketWest>) => {
          if (ticketWest.body) {
            return of(ticketWest.body);
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
