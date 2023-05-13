import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRailwayTypeWest, NewRailwayTypeWest } from '../railway-type-west.model';

export type PartialUpdateRailwayTypeWest = Partial<IRailwayTypeWest> & Pick<IRailwayTypeWest, 'id'>;

export type EntityResponseType = HttpResponse<IRailwayTypeWest>;
export type EntityArrayResponseType = HttpResponse<IRailwayTypeWest[]>;

@Injectable({ providedIn: 'root' })
export class RailwayTypeWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/railway-type-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(railwayTypeWest: NewRailwayTypeWest): Observable<EntityResponseType> {
    return this.http.post<IRailwayTypeWest>(this.resourceUrl, railwayTypeWest, { observe: 'response' });
  }

  update(railwayTypeWest: IRailwayTypeWest): Observable<EntityResponseType> {
    return this.http.put<IRailwayTypeWest>(`${this.resourceUrl}/${this.getRailwayTypeWestIdentifier(railwayTypeWest)}`, railwayTypeWest, {
      observe: 'response',
    });
  }

  partialUpdate(railwayTypeWest: PartialUpdateRailwayTypeWest): Observable<EntityResponseType> {
    return this.http.patch<IRailwayTypeWest>(`${this.resourceUrl}/${this.getRailwayTypeWestIdentifier(railwayTypeWest)}`, railwayTypeWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRailwayTypeWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRailwayTypeWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRailwayTypeWestIdentifier(railwayTypeWest: Pick<IRailwayTypeWest, 'id'>): number {
    return railwayTypeWest.id;
  }

  compareRailwayTypeWest(o1: Pick<IRailwayTypeWest, 'id'> | null, o2: Pick<IRailwayTypeWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getRailwayTypeWestIdentifier(o1) === this.getRailwayTypeWestIdentifier(o2) : o1 === o2;
  }

  addRailwayTypeWestToCollectionIfMissing<Type extends Pick<IRailwayTypeWest, 'id'>>(
    railwayTypeWestCollection: Type[],
    ...railwayTypeWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const railwayTypeWests: Type[] = railwayTypeWestsToCheck.filter(isPresent);
    if (railwayTypeWests.length > 0) {
      const railwayTypeWestCollectionIdentifiers = railwayTypeWestCollection.map(
        railwayTypeWestItem => this.getRailwayTypeWestIdentifier(railwayTypeWestItem)!
      );
      const railwayTypeWestsToAdd = railwayTypeWests.filter(railwayTypeWestItem => {
        const railwayTypeWestIdentifier = this.getRailwayTypeWestIdentifier(railwayTypeWestItem);
        if (railwayTypeWestCollectionIdentifiers.includes(railwayTypeWestIdentifier)) {
          return false;
        }
        railwayTypeWestCollectionIdentifiers.push(railwayTypeWestIdentifier);
        return true;
      });
      return [...railwayTypeWestsToAdd, ...railwayTypeWestCollection];
    }
    return railwayTypeWestCollection;
  }
}
