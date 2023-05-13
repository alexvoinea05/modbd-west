import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainWest, NewTrainWest } from '../train-west.model';

export type PartialUpdateTrainWest = Partial<ITrainWest> & Pick<ITrainWest, 'id'>;

export type EntityResponseType = HttpResponse<ITrainWest>;
export type EntityArrayResponseType = HttpResponse<ITrainWest[]>;

@Injectable({ providedIn: 'root' })
export class TrainWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/train-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainWest: NewTrainWest): Observable<EntityResponseType> {
    return this.http.post<ITrainWest>(this.resourceUrl, trainWest, { observe: 'response' });
  }

  update(trainWest: ITrainWest): Observable<EntityResponseType> {
    return this.http.put<ITrainWest>(`${this.resourceUrl}/${this.getTrainWestIdentifier(trainWest)}`, trainWest, { observe: 'response' });
  }

  partialUpdate(trainWest: PartialUpdateTrainWest): Observable<EntityResponseType> {
    return this.http.patch<ITrainWest>(`${this.resourceUrl}/${this.getTrainWestIdentifier(trainWest)}`, trainWest, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainWestIdentifier(trainWest: Pick<ITrainWest, 'id'>): number {
    return trainWest.id;
  }

  compareTrainWest(o1: Pick<ITrainWest, 'id'> | null, o2: Pick<ITrainWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainWestIdentifier(o1) === this.getTrainWestIdentifier(o2) : o1 === o2;
  }

  addTrainWestToCollectionIfMissing<Type extends Pick<ITrainWest, 'id'>>(
    trainWestCollection: Type[],
    ...trainWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trainWests: Type[] = trainWestsToCheck.filter(isPresent);
    if (trainWests.length > 0) {
      const trainWestCollectionIdentifiers = trainWestCollection.map(trainWestItem => this.getTrainWestIdentifier(trainWestItem)!);
      const trainWestsToAdd = trainWests.filter(trainWestItem => {
        const trainWestIdentifier = this.getTrainWestIdentifier(trainWestItem);
        if (trainWestCollectionIdentifiers.includes(trainWestIdentifier)) {
          return false;
        }
        trainWestCollectionIdentifiers.push(trainWestIdentifier);
        return true;
      });
      return [...trainWestsToAdd, ...trainWestCollection];
    }
    return trainWestCollection;
  }
}
