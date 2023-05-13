import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourneyWest, NewJourneyWest } from '../journey-west.model';

export type PartialUpdateJourneyWest = Partial<IJourneyWest> & Pick<IJourneyWest, 'id'>;

type RestOf<T extends IJourneyWest | NewJourneyWest> = Omit<
  T,
  'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
> & {
  actualDepartureTime?: string | null;
  plannedDepartureTime?: string | null;
  actualArrivalTime?: string | null;
  plannedArrivalTime?: string | null;
};

export type RestJourneyWest = RestOf<IJourneyWest>;

export type NewRestJourneyWest = RestOf<NewJourneyWest>;

export type PartialUpdateRestJourneyWest = RestOf<PartialUpdateJourneyWest>;

export type EntityResponseType = HttpResponse<IJourneyWest>;
export type EntityArrayResponseType = HttpResponse<IJourneyWest[]>;

@Injectable({ providedIn: 'root' })
export class JourneyWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/journey-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(journeyWest: NewJourneyWest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journeyWest);
    return this.http
      .post<RestJourneyWest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(journeyWest: IJourneyWest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journeyWest);
    return this.http
      .put<RestJourneyWest>(`${this.resourceUrl}/${this.getJourneyWestIdentifier(journeyWest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(journeyWest: PartialUpdateJourneyWest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journeyWest);
    return this.http
      .patch<RestJourneyWest>(`${this.resourceUrl}/${this.getJourneyWestIdentifier(journeyWest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestJourneyWest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestJourneyWest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourneyWestIdentifier(journeyWest: Pick<IJourneyWest, 'id'>): number {
    return journeyWest.id;
  }

  compareJourneyWest(o1: Pick<IJourneyWest, 'id'> | null, o2: Pick<IJourneyWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getJourneyWestIdentifier(o1) === this.getJourneyWestIdentifier(o2) : o1 === o2;
  }

  addJourneyWestToCollectionIfMissing<Type extends Pick<IJourneyWest, 'id'>>(
    journeyWestCollection: Type[],
    ...journeyWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const journeyWests: Type[] = journeyWestsToCheck.filter(isPresent);
    if (journeyWests.length > 0) {
      const journeyWestCollectionIdentifiers = journeyWestCollection.map(
        journeyWestItem => this.getJourneyWestIdentifier(journeyWestItem)!
      );
      const journeyWestsToAdd = journeyWests.filter(journeyWestItem => {
        const journeyWestIdentifier = this.getJourneyWestIdentifier(journeyWestItem);
        if (journeyWestCollectionIdentifiers.includes(journeyWestIdentifier)) {
          return false;
        }
        journeyWestCollectionIdentifiers.push(journeyWestIdentifier);
        return true;
      });
      return [...journeyWestsToAdd, ...journeyWestCollection];
    }
    return journeyWestCollection;
  }

  protected convertDateFromClient<T extends IJourneyWest | NewJourneyWest | PartialUpdateJourneyWest>(journeyWest: T): RestOf<T> {
    return {
      ...journeyWest,
      actualDepartureTime: journeyWest.actualDepartureTime?.toJSON() ?? null,
      plannedDepartureTime: journeyWest.plannedDepartureTime?.toJSON() ?? null,
      actualArrivalTime: journeyWest.actualArrivalTime?.toJSON() ?? null,
      plannedArrivalTime: journeyWest.plannedArrivalTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restJourneyWest: RestJourneyWest): IJourneyWest {
    return {
      ...restJourneyWest,
      actualDepartureTime: restJourneyWest.actualDepartureTime ? dayjs(restJourneyWest.actualDepartureTime) : undefined,
      plannedDepartureTime: restJourneyWest.plannedDepartureTime ? dayjs(restJourneyWest.plannedDepartureTime) : undefined,
      actualArrivalTime: restJourneyWest.actualArrivalTime ? dayjs(restJourneyWest.actualArrivalTime) : undefined,
      plannedArrivalTime: restJourneyWest.plannedArrivalTime ? dayjs(restJourneyWest.plannedArrivalTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestJourneyWest>): HttpResponse<IJourneyWest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestJourneyWest[]>): HttpResponse<IJourneyWest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
