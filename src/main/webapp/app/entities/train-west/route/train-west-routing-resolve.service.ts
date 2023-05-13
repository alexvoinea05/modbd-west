import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainWest } from '../train-west.model';
import { TrainWestService } from '../service/train-west.service';

@Injectable({ providedIn: 'root' })
export class TrainWestRoutingResolveService implements Resolve<ITrainWest | null> {
  constructor(protected service: TrainWestService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainWest | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainWest: HttpResponse<ITrainWest>) => {
          if (trainWest.body) {
            return of(trainWest.body);
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
