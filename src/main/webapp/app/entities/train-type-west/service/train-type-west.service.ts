import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainTypeWest, NewTrainTypeWest } from '../train-type-west.model';

export type PartialUpdateTrainTypeWest = Partial<ITrainTypeWest> & Pick<ITrainTypeWest, 'id'>;

export type EntityResponseType = HttpResponse<ITrainTypeWest>;
export type EntityArrayResponseType = HttpResponse<ITrainTypeWest[]>;

@Injectable({ providedIn: 'root' })
export class TrainTypeWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/train-type-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainTypeWest: NewTrainTypeWest): Observable<EntityResponseType> {
    return this.http.post<ITrainTypeWest>(this.resourceUrl, trainTypeWest, { observe: 'response' });
  }

  update(trainTypeWest: ITrainTypeWest): Observable<EntityResponseType> {
    return this.http.put<ITrainTypeWest>(`${this.resourceUrl}/${this.getTrainTypeWestIdentifier(trainTypeWest)}`, trainTypeWest, {
      observe: 'response',
    });
  }

  partialUpdate(trainTypeWest: PartialUpdateTrainTypeWest): Observable<EntityResponseType> {
    return this.http.patch<ITrainTypeWest>(`${this.resourceUrl}/${this.getTrainTypeWestIdentifier(trainTypeWest)}`, trainTypeWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainTypeWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainTypeWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainTypeWestIdentifier(trainTypeWest: Pick<ITrainTypeWest, 'id'>): number {
    return trainTypeWest.id;
  }

  compareTrainTypeWest(o1: Pick<ITrainTypeWest, 'id'> | null, o2: Pick<ITrainTypeWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainTypeWestIdentifier(o1) === this.getTrainTypeWestIdentifier(o2) : o1 === o2;
  }

  addTrainTypeWestToCollectionIfMissing<Type extends Pick<ITrainTypeWest, 'id'>>(
    trainTypeWestCollection: Type[],
    ...trainTypeWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trainTypeWests: Type[] = trainTypeWestsToCheck.filter(isPresent);
    if (trainTypeWests.length > 0) {
      const trainTypeWestCollectionIdentifiers = trainTypeWestCollection.map(
        trainTypeWestItem => this.getTrainTypeWestIdentifier(trainTypeWestItem)!
      );
      const trainTypeWestsToAdd = trainTypeWests.filter(trainTypeWestItem => {
        const trainTypeWestIdentifier = this.getTrainTypeWestIdentifier(trainTypeWestItem);
        if (trainTypeWestCollectionIdentifiers.includes(trainTypeWestIdentifier)) {
          return false;
        }
        trainTypeWestCollectionIdentifiers.push(trainTypeWestIdentifier);
        return true;
      });
      return [...trainTypeWestsToAdd, ...trainTypeWestCollection];
    }
    return trainTypeWestCollection;
  }
}
