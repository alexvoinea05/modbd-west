import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILicenseWest, NewLicenseWest } from '../license-west.model';

export type PartialUpdateLicenseWest = Partial<ILicenseWest> & Pick<ILicenseWest, 'id'>;

export type EntityResponseType = HttpResponse<ILicenseWest>;
export type EntityArrayResponseType = HttpResponse<ILicenseWest[]>;

@Injectable({ providedIn: 'root' })
export class LicenseWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/license-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(licenseWest: NewLicenseWest): Observable<EntityResponseType> {
    return this.http.post<ILicenseWest>(this.resourceUrl, licenseWest, { observe: 'response' });
  }

  update(licenseWest: ILicenseWest): Observable<EntityResponseType> {
    return this.http.put<ILicenseWest>(`${this.resourceUrl}/${this.getLicenseWestIdentifier(licenseWest)}`, licenseWest, {
      observe: 'response',
    });
  }

  partialUpdate(licenseWest: PartialUpdateLicenseWest): Observable<EntityResponseType> {
    return this.http.patch<ILicenseWest>(`${this.resourceUrl}/${this.getLicenseWestIdentifier(licenseWest)}`, licenseWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILicenseWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILicenseWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLicenseWestIdentifier(licenseWest: Pick<ILicenseWest, 'id'>): number {
    return licenseWest.id;
  }

  compareLicenseWest(o1: Pick<ILicenseWest, 'id'> | null, o2: Pick<ILicenseWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getLicenseWestIdentifier(o1) === this.getLicenseWestIdentifier(o2) : o1 === o2;
  }

  addLicenseWestToCollectionIfMissing<Type extends Pick<ILicenseWest, 'id'>>(
    licenseWestCollection: Type[],
    ...licenseWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const licenseWests: Type[] = licenseWestsToCheck.filter(isPresent);
    if (licenseWests.length > 0) {
      const licenseWestCollectionIdentifiers = licenseWestCollection.map(
        licenseWestItem => this.getLicenseWestIdentifier(licenseWestItem)!
      );
      const licenseWestsToAdd = licenseWests.filter(licenseWestItem => {
        const licenseWestIdentifier = this.getLicenseWestIdentifier(licenseWestItem);
        if (licenseWestCollectionIdentifiers.includes(licenseWestIdentifier)) {
          return false;
        }
        licenseWestCollectionIdentifiers.push(licenseWestIdentifier);
        return true;
      });
      return [...licenseWestsToAdd, ...licenseWestCollection];
    }
    return licenseWestCollection;
  }
}
