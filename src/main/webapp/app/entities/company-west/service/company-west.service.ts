import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanyWest, NewCompanyWest } from '../company-west.model';

export type PartialUpdateCompanyWest = Partial<ICompanyWest> & Pick<ICompanyWest, 'id'>;

export type EntityResponseType = HttpResponse<ICompanyWest>;
export type EntityArrayResponseType = HttpResponse<ICompanyWest[]>;

@Injectable({ providedIn: 'root' })
export class CompanyWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/company-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companyWest: NewCompanyWest): Observable<EntityResponseType> {
    return this.http.post<ICompanyWest>(this.resourceUrl, companyWest, { observe: 'response' });
  }

  update(companyWest: ICompanyWest): Observable<EntityResponseType> {
    return this.http.put<ICompanyWest>(`${this.resourceUrl}/${this.getCompanyWestIdentifier(companyWest)}`, companyWest, {
      observe: 'response',
    });
  }

  partialUpdate(companyWest: PartialUpdateCompanyWest): Observable<EntityResponseType> {
    return this.http.patch<ICompanyWest>(`${this.resourceUrl}/${this.getCompanyWestIdentifier(companyWest)}`, companyWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompanyWestIdentifier(companyWest: Pick<ICompanyWest, 'id'>): number {
    return companyWest.id;
  }

  compareCompanyWest(o1: Pick<ICompanyWest, 'id'> | null, o2: Pick<ICompanyWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompanyWestIdentifier(o1) === this.getCompanyWestIdentifier(o2) : o1 === o2;
  }

  addCompanyWestToCollectionIfMissing<Type extends Pick<ICompanyWest, 'id'>>(
    companyWestCollection: Type[],
    ...companyWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const companyWests: Type[] = companyWestsToCheck.filter(isPresent);
    if (companyWests.length > 0) {
      const companyWestCollectionIdentifiers = companyWestCollection.map(
        companyWestItem => this.getCompanyWestIdentifier(companyWestItem)!
      );
      const companyWestsToAdd = companyWests.filter(companyWestItem => {
        const companyWestIdentifier = this.getCompanyWestIdentifier(companyWestItem);
        if (companyWestCollectionIdentifiers.includes(companyWestIdentifier)) {
          return false;
        }
        companyWestCollectionIdentifiers.push(companyWestIdentifier);
        return true;
      });
      return [...companyWestsToAdd, ...companyWestCollection];
    }
    return companyWestCollection;
  }
}
