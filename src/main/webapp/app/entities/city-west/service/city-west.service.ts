import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICityWest, NewCityWest } from '../city-west.model';

export type PartialUpdateCityWest = Partial<ICityWest> & Pick<ICityWest, 'id'>;

export type EntityResponseType = HttpResponse<ICityWest>;
export type EntityArrayResponseType = HttpResponse<ICityWest[]>;

@Injectable({ providedIn: 'root' })
export class CityWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/city-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(cityWest: NewCityWest): Observable<EntityResponseType> {
    return this.http.post<ICityWest>(this.resourceUrl, cityWest, { observe: 'response' });
  }

  update(cityWest: ICityWest): Observable<EntityResponseType> {
    return this.http.put<ICityWest>(`${this.resourceUrl}/${this.getCityWestIdentifier(cityWest)}`, cityWest, { observe: 'response' });
  }

  partialUpdate(cityWest: PartialUpdateCityWest): Observable<EntityResponseType> {
    return this.http.patch<ICityWest>(`${this.resourceUrl}/${this.getCityWestIdentifier(cityWest)}`, cityWest, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICityWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICityWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCityWestIdentifier(cityWest: Pick<ICityWest, 'id'>): number {
    return cityWest.id;
  }

  compareCityWest(o1: Pick<ICityWest, 'id'> | null, o2: Pick<ICityWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getCityWestIdentifier(o1) === this.getCityWestIdentifier(o2) : o1 === o2;
  }

  addCityWestToCollectionIfMissing<Type extends Pick<ICityWest, 'id'>>(
    cityWestCollection: Type[],
    ...cityWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cityWests: Type[] = cityWestsToCheck.filter(isPresent);
    if (cityWests.length > 0) {
      const cityWestCollectionIdentifiers = cityWestCollection.map(cityWestItem => this.getCityWestIdentifier(cityWestItem)!);
      const cityWestsToAdd = cityWests.filter(cityWestItem => {
        const cityWestIdentifier = this.getCityWestIdentifier(cityWestItem);
        if (cityWestCollectionIdentifiers.includes(cityWestIdentifier)) {
          return false;
        }
        cityWestCollectionIdentifiers.push(cityWestIdentifier);
        return true;
      });
      return [...cityWestsToAdd, ...cityWestCollection];
    }
    return cityWestCollection;
  }
}
