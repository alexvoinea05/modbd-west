import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRailwayStationWest, NewRailwayStationWest } from '../railway-station-west.model';

export type PartialUpdateRailwayStationWest = Partial<IRailwayStationWest> & Pick<IRailwayStationWest, 'id'>;

export type EntityResponseType = HttpResponse<IRailwayStationWest>;
export type EntityArrayResponseType = HttpResponse<IRailwayStationWest[]>;

@Injectable({ providedIn: 'root' })
export class RailwayStationWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/railway-station-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(railwayStationWest: NewRailwayStationWest): Observable<EntityResponseType> {
    return this.http.post<IRailwayStationWest>(this.resourceUrl, railwayStationWest, { observe: 'response' });
  }

  update(railwayStationWest: IRailwayStationWest): Observable<EntityResponseType> {
    return this.http.put<IRailwayStationWest>(
      `${this.resourceUrl}/${this.getRailwayStationWestIdentifier(railwayStationWest)}`,
      railwayStationWest,
      { observe: 'response' }
    );
  }

  partialUpdate(railwayStationWest: PartialUpdateRailwayStationWest): Observable<EntityResponseType> {
    return this.http.patch<IRailwayStationWest>(
      `${this.resourceUrl}/${this.getRailwayStationWestIdentifier(railwayStationWest)}`,
      railwayStationWest,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRailwayStationWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRailwayStationWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRailwayStationWestIdentifier(railwayStationWest: Pick<IRailwayStationWest, 'id'>): number {
    return railwayStationWest.id;
  }

  compareRailwayStationWest(o1: Pick<IRailwayStationWest, 'id'> | null, o2: Pick<IRailwayStationWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getRailwayStationWestIdentifier(o1) === this.getRailwayStationWestIdentifier(o2) : o1 === o2;
  }

  addRailwayStationWestToCollectionIfMissing<Type extends Pick<IRailwayStationWest, 'id'>>(
    railwayStationWestCollection: Type[],
    ...railwayStationWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const railwayStationWests: Type[] = railwayStationWestsToCheck.filter(isPresent);
    if (railwayStationWests.length > 0) {
      const railwayStationWestCollectionIdentifiers = railwayStationWestCollection.map(
        railwayStationWestItem => this.getRailwayStationWestIdentifier(railwayStationWestItem)!
      );
      const railwayStationWestsToAdd = railwayStationWests.filter(railwayStationWestItem => {
        const railwayStationWestIdentifier = this.getRailwayStationWestIdentifier(railwayStationWestItem);
        if (railwayStationWestCollectionIdentifiers.includes(railwayStationWestIdentifier)) {
          return false;
        }
        railwayStationWestCollectionIdentifiers.push(railwayStationWestIdentifier);
        return true;
      });
      return [...railwayStationWestsToAdd, ...railwayStationWestCollection];
    }
    return railwayStationWestCollection;
  }
}
