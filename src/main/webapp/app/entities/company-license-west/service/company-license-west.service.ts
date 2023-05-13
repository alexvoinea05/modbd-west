import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanyLicenseWest, NewCompanyLicenseWest } from '../company-license-west.model';

export type PartialUpdateCompanyLicenseWest = Partial<ICompanyLicenseWest> & Pick<ICompanyLicenseWest, 'id'>;

export type EntityResponseType = HttpResponse<ICompanyLicenseWest>;
export type EntityArrayResponseType = HttpResponse<ICompanyLicenseWest[]>;

@Injectable({ providedIn: 'root' })
export class CompanyLicenseWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/company-license-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companyLicenseWest: NewCompanyLicenseWest): Observable<EntityResponseType> {
    return this.http.post<ICompanyLicenseWest>(this.resourceUrl, companyLicenseWest, { observe: 'response' });
  }

  update(companyLicenseWest: ICompanyLicenseWest): Observable<EntityResponseType> {
    return this.http.put<ICompanyLicenseWest>(
      `${this.resourceUrl}/${this.getCompanyLicenseWestIdentifier(companyLicenseWest)}`,
      companyLicenseWest,
      { observe: 'response' }
    );
  }

  partialUpdate(companyLicenseWest: PartialUpdateCompanyLicenseWest): Observable<EntityResponseType> {
    return this.http.patch<ICompanyLicenseWest>(
      `${this.resourceUrl}/${this.getCompanyLicenseWestIdentifier(companyLicenseWest)}`,
      companyLicenseWest,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyLicenseWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyLicenseWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompanyLicenseWestIdentifier(companyLicenseWest: Pick<ICompanyLicenseWest, 'id'>): number {
    return companyLicenseWest.id;
  }

  compareCompanyLicenseWest(o1: Pick<ICompanyLicenseWest, 'id'> | null, o2: Pick<ICompanyLicenseWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompanyLicenseWestIdentifier(o1) === this.getCompanyLicenseWestIdentifier(o2) : o1 === o2;
  }

  addCompanyLicenseWestToCollectionIfMissing<Type extends Pick<ICompanyLicenseWest, 'id'>>(
    companyLicenseWestCollection: Type[],
    ...companyLicenseWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const companyLicenseWests: Type[] = companyLicenseWestsToCheck.filter(isPresent);
    if (companyLicenseWests.length > 0) {
      const companyLicenseWestCollectionIdentifiers = companyLicenseWestCollection.map(
        companyLicenseWestItem => this.getCompanyLicenseWestIdentifier(companyLicenseWestItem)!
      );
      const companyLicenseWestsToAdd = companyLicenseWests.filter(companyLicenseWestItem => {
        const companyLicenseWestIdentifier = this.getCompanyLicenseWestIdentifier(companyLicenseWestItem);
        if (companyLicenseWestCollectionIdentifiers.includes(companyLicenseWestIdentifier)) {
          return false;
        }
        companyLicenseWestCollectionIdentifiers.push(companyLicenseWestIdentifier);
        return true;
      });
      return [...companyLicenseWestsToAdd, ...companyLicenseWestCollection];
    }
    return companyLicenseWestCollection;
  }
}
