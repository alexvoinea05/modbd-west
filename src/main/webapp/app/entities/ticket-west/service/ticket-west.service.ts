import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITicketWest, NewTicketWest } from '../ticket-west.model';

export type PartialUpdateTicketWest = Partial<ITicketWest> & Pick<ITicketWest, 'id'>;

type RestOf<T extends ITicketWest | NewTicketWest> = Omit<T, 'time'> & {
  time?: string | null;
};

export type RestTicketWest = RestOf<ITicketWest>;

export type NewRestTicketWest = RestOf<NewTicketWest>;

export type PartialUpdateRestTicketWest = RestOf<PartialUpdateTicketWest>;

export type EntityResponseType = HttpResponse<ITicketWest>;
export type EntityArrayResponseType = HttpResponse<ITicketWest[]>;

@Injectable({ providedIn: 'root' })
export class TicketWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ticket-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(ticketWest: NewTicketWest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketWest);
    return this.http
      .post<RestTicketWest>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ticketWest: ITicketWest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketWest);
    return this.http
      .put<RestTicketWest>(`${this.resourceUrl}/${this.getTicketWestIdentifier(ticketWest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ticketWest: PartialUpdateTicketWest): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ticketWest);
    return this.http
      .patch<RestTicketWest>(`${this.resourceUrl}/${this.getTicketWestIdentifier(ticketWest)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTicketWest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTicketWest[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTicketWestIdentifier(ticketWest: Pick<ITicketWest, 'id'>): number {
    return ticketWest.id;
  }

  compareTicketWest(o1: Pick<ITicketWest, 'id'> | null, o2: Pick<ITicketWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getTicketWestIdentifier(o1) === this.getTicketWestIdentifier(o2) : o1 === o2;
  }

  addTicketWestToCollectionIfMissing<Type extends Pick<ITicketWest, 'id'>>(
    ticketWestCollection: Type[],
    ...ticketWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ticketWests: Type[] = ticketWestsToCheck.filter(isPresent);
    if (ticketWests.length > 0) {
      const ticketWestCollectionIdentifiers = ticketWestCollection.map(ticketWestItem => this.getTicketWestIdentifier(ticketWestItem)!);
      const ticketWestsToAdd = ticketWests.filter(ticketWestItem => {
        const ticketWestIdentifier = this.getTicketWestIdentifier(ticketWestItem);
        if (ticketWestCollectionIdentifiers.includes(ticketWestIdentifier)) {
          return false;
        }
        ticketWestCollectionIdentifiers.push(ticketWestIdentifier);
        return true;
      });
      return [...ticketWestsToAdd, ...ticketWestCollection];
    }
    return ticketWestCollection;
  }

  protected convertDateFromClient<T extends ITicketWest | NewTicketWest | PartialUpdateTicketWest>(ticketWest: T): RestOf<T> {
    return {
      ...ticketWest,
      time: ticketWest.time?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTicketWest: RestTicketWest): ITicketWest {
    return {
      ...restTicketWest,
      time: restTicketWest.time ? dayjs(restTicketWest.time) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTicketWest>): HttpResponse<ITicketWest> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTicketWest[]>): HttpResponse<ITicketWest[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
