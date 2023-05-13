import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourneyStatusWest, NewJourneyStatusWest } from '../journey-status-west.model';

export type PartialUpdateJourneyStatusWest = Partial<IJourneyStatusWest> & Pick<IJourneyStatusWest, 'id'>;

export type EntityResponseType = HttpResponse<IJourneyStatusWest>;
export type EntityArrayResponseType = HttpResponse<IJourneyStatusWest[]>;

@Injectable({ providedIn: 'root' })
export class JourneyStatusWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/journey-status-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(journeyStatusWest: NewJourneyStatusWest): Observable<EntityResponseType> {
    return this.http.post<IJourneyStatusWest>(this.resourceUrl, journeyStatusWest, { observe: 'response' });
  }

  update(journeyStatusWest: IJourneyStatusWest): Observable<EntityResponseType> {
    return this.http.put<IJourneyStatusWest>(
      `${this.resourceUrl}/${this.getJourneyStatusWestIdentifier(journeyStatusWest)}`,
      journeyStatusWest,
      { observe: 'response' }
    );
  }

  partialUpdate(journeyStatusWest: PartialUpdateJourneyStatusWest): Observable<EntityResponseType> {
    return this.http.patch<IJourneyStatusWest>(
      `${this.resourceUrl}/${this.getJourneyStatusWestIdentifier(journeyStatusWest)}`,
      journeyStatusWest,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJourneyStatusWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJourneyStatusWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourneyStatusWestIdentifier(journeyStatusWest: Pick<IJourneyStatusWest, 'id'>): number {
    return journeyStatusWest.id;
  }

  compareJourneyStatusWest(o1: Pick<IJourneyStatusWest, 'id'> | null, o2: Pick<IJourneyStatusWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getJourneyStatusWestIdentifier(o1) === this.getJourneyStatusWestIdentifier(o2) : o1 === o2;
  }

  addJourneyStatusWestToCollectionIfMissing<Type extends Pick<IJourneyStatusWest, 'id'>>(
    journeyStatusWestCollection: Type[],
    ...journeyStatusWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const journeyStatusWests: Type[] = journeyStatusWestsToCheck.filter(isPresent);
    if (journeyStatusWests.length > 0) {
      const journeyStatusWestCollectionIdentifiers = journeyStatusWestCollection.map(
        journeyStatusWestItem => this.getJourneyStatusWestIdentifier(journeyStatusWestItem)!
      );
      const journeyStatusWestsToAdd = journeyStatusWests.filter(journeyStatusWestItem => {
        const journeyStatusWestIdentifier = this.getJourneyStatusWestIdentifier(journeyStatusWestItem);
        if (journeyStatusWestCollectionIdentifiers.includes(journeyStatusWestIdentifier)) {
          return false;
        }
        journeyStatusWestCollectionIdentifiers.push(journeyStatusWestIdentifier);
        return true;
      });
      return [...journeyStatusWestsToAdd, ...journeyStatusWestCollection];
    }
    return journeyStatusWestCollection;
  }
}
