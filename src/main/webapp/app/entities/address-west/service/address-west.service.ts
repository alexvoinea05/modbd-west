import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAddressWest, NewAddressWest } from '../address-west.model';

export type PartialUpdateAddressWest = Partial<IAddressWest> & Pick<IAddressWest, 'id'>;

export type EntityResponseType = HttpResponse<IAddressWest>;
export type EntityArrayResponseType = HttpResponse<IAddressWest[]>;

@Injectable({ providedIn: 'root' })
export class AddressWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/address-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(addressWest: NewAddressWest): Observable<EntityResponseType> {
    return this.http.post<IAddressWest>(this.resourceUrl, addressWest, { observe: 'response' });
  }

  update(addressWest: IAddressWest): Observable<EntityResponseType> {
    return this.http.put<IAddressWest>(`${this.resourceUrl}/${this.getAddressWestIdentifier(addressWest)}`, addressWest, {
      observe: 'response',
    });
  }

  partialUpdate(addressWest: PartialUpdateAddressWest): Observable<EntityResponseType> {
    return this.http.patch<IAddressWest>(`${this.resourceUrl}/${this.getAddressWestIdentifier(addressWest)}`, addressWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAddressWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAddressWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAddressWestIdentifier(addressWest: Pick<IAddressWest, 'id'>): number {
    return addressWest.id;
  }

  compareAddressWest(o1: Pick<IAddressWest, 'id'> | null, o2: Pick<IAddressWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getAddressWestIdentifier(o1) === this.getAddressWestIdentifier(o2) : o1 === o2;
  }

  addAddressWestToCollectionIfMissing<Type extends Pick<IAddressWest, 'id'>>(
    addressWestCollection: Type[],
    ...addressWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const addressWests: Type[] = addressWestsToCheck.filter(isPresent);
    if (addressWests.length > 0) {
      const addressWestCollectionIdentifiers = addressWestCollection.map(
        addressWestItem => this.getAddressWestIdentifier(addressWestItem)!
      );
      const addressWestsToAdd = addressWests.filter(addressWestItem => {
        const addressWestIdentifier = this.getAddressWestIdentifier(addressWestItem);
        if (addressWestCollectionIdentifiers.includes(addressWestIdentifier)) {
          return false;
        }
        addressWestCollectionIdentifiers.push(addressWestIdentifier);
        return true;
      });
      return [...addressWestsToAdd, ...addressWestCollection];
    }
    return addressWestCollection;
  }
}
