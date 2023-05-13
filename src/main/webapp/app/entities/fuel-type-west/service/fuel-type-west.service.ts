import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelTypeWest, NewFuelTypeWest } from '../fuel-type-west.model';

export type PartialUpdateFuelTypeWest = Partial<IFuelTypeWest> & Pick<IFuelTypeWest, 'id'>;

export type EntityResponseType = HttpResponse<IFuelTypeWest>;
export type EntityArrayResponseType = HttpResponse<IFuelTypeWest[]>;

@Injectable({ providedIn: 'root' })
export class FuelTypeWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-type-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fuelTypeWest: NewFuelTypeWest): Observable<EntityResponseType> {
    return this.http.post<IFuelTypeWest>(this.resourceUrl, fuelTypeWest, { observe: 'response' });
  }

  update(fuelTypeWest: IFuelTypeWest): Observable<EntityResponseType> {
    return this.http.put<IFuelTypeWest>(`${this.resourceUrl}/${this.getFuelTypeWestIdentifier(fuelTypeWest)}`, fuelTypeWest, {
      observe: 'response',
    });
  }

  partialUpdate(fuelTypeWest: PartialUpdateFuelTypeWest): Observable<EntityResponseType> {
    return this.http.patch<IFuelTypeWest>(`${this.resourceUrl}/${this.getFuelTypeWestIdentifier(fuelTypeWest)}`, fuelTypeWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelTypeWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelTypeWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTypeWestIdentifier(fuelTypeWest: Pick<IFuelTypeWest, 'id'>): number {
    return fuelTypeWest.id;
  }

  compareFuelTypeWest(o1: Pick<IFuelTypeWest, 'id'> | null, o2: Pick<IFuelTypeWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelTypeWestIdentifier(o1) === this.getFuelTypeWestIdentifier(o2) : o1 === o2;
  }

  addFuelTypeWestToCollectionIfMissing<Type extends Pick<IFuelTypeWest, 'id'>>(
    fuelTypeWestCollection: Type[],
    ...fuelTypeWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelTypeWests: Type[] = fuelTypeWestsToCheck.filter(isPresent);
    if (fuelTypeWests.length > 0) {
      const fuelTypeWestCollectionIdentifiers = fuelTypeWestCollection.map(
        fuelTypeWestItem => this.getFuelTypeWestIdentifier(fuelTypeWestItem)!
      );
      const fuelTypeWestsToAdd = fuelTypeWests.filter(fuelTypeWestItem => {
        const fuelTypeWestIdentifier = this.getFuelTypeWestIdentifier(fuelTypeWestItem);
        if (fuelTypeWestCollectionIdentifiers.includes(fuelTypeWestIdentifier)) {
          return false;
        }
        fuelTypeWestCollectionIdentifiers.push(fuelTypeWestIdentifier);
        return true;
      });
      return [...fuelTypeWestsToAdd, ...fuelTypeWestCollection];
    }
    return fuelTypeWestCollection;
  }
}
