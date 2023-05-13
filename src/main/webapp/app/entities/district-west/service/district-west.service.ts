import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDistrictWest, NewDistrictWest } from '../district-west.model';

export type PartialUpdateDistrictWest = Partial<IDistrictWest> & Pick<IDistrictWest, 'id'>;

export type EntityResponseType = HttpResponse<IDistrictWest>;
export type EntityArrayResponseType = HttpResponse<IDistrictWest[]>;

@Injectable({ providedIn: 'root' })
export class DistrictWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/district-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(districtWest: NewDistrictWest): Observable<EntityResponseType> {
    return this.http.post<IDistrictWest>(this.resourceUrl, districtWest, { observe: 'response' });
  }

  update(districtWest: IDistrictWest): Observable<EntityResponseType> {
    return this.http.put<IDistrictWest>(`${this.resourceUrl}/${this.getDistrictWestIdentifier(districtWest)}`, districtWest, {
      observe: 'response',
    });
  }

  partialUpdate(districtWest: PartialUpdateDistrictWest): Observable<EntityResponseType> {
    return this.http.patch<IDistrictWest>(`${this.resourceUrl}/${this.getDistrictWestIdentifier(districtWest)}`, districtWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistrictWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistrictWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDistrictWestIdentifier(districtWest: Pick<IDistrictWest, 'id'>): number {
    return districtWest.id;
  }

  compareDistrictWest(o1: Pick<IDistrictWest, 'id'> | null, o2: Pick<IDistrictWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getDistrictWestIdentifier(o1) === this.getDistrictWestIdentifier(o2) : o1 === o2;
  }

  addDistrictWestToCollectionIfMissing<Type extends Pick<IDistrictWest, 'id'>>(
    districtWestCollection: Type[],
    ...districtWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const districtWests: Type[] = districtWestsToCheck.filter(isPresent);
    if (districtWests.length > 0) {
      const districtWestCollectionIdentifiers = districtWestCollection.map(
        districtWestItem => this.getDistrictWestIdentifier(districtWestItem)!
      );
      const districtWestsToAdd = districtWests.filter(districtWestItem => {
        const districtWestIdentifier = this.getDistrictWestIdentifier(districtWestItem);
        if (districtWestCollectionIdentifiers.includes(districtWestIdentifier)) {
          return false;
        }
        districtWestCollectionIdentifiers.push(districtWestIdentifier);
        return true;
      });
      return [...districtWestsToAdd, ...districtWestCollection];
    }
    return districtWestCollection;
  }
}
