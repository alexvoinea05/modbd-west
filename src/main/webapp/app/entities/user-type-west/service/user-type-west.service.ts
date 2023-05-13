import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUserTypeWest, NewUserTypeWest } from '../user-type-west.model';

export type PartialUpdateUserTypeWest = Partial<IUserTypeWest> & Pick<IUserTypeWest, 'id'>;

export type EntityResponseType = HttpResponse<IUserTypeWest>;
export type EntityArrayResponseType = HttpResponse<IUserTypeWest[]>;

@Injectable({ providedIn: 'root' })
export class UserTypeWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/user-type-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(userTypeWest: NewUserTypeWest): Observable<EntityResponseType> {
    return this.http.post<IUserTypeWest>(this.resourceUrl, userTypeWest, { observe: 'response' });
  }

  update(userTypeWest: IUserTypeWest): Observable<EntityResponseType> {
    return this.http.put<IUserTypeWest>(`${this.resourceUrl}/${this.getUserTypeWestIdentifier(userTypeWest)}`, userTypeWest, {
      observe: 'response',
    });
  }

  partialUpdate(userTypeWest: PartialUpdateUserTypeWest): Observable<EntityResponseType> {
    return this.http.patch<IUserTypeWest>(`${this.resourceUrl}/${this.getUserTypeWestIdentifier(userTypeWest)}`, userTypeWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserTypeWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserTypeWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUserTypeWestIdentifier(userTypeWest: Pick<IUserTypeWest, 'id'>): number {
    return userTypeWest.id;
  }

  compareUserTypeWest(o1: Pick<IUserTypeWest, 'id'> | null, o2: Pick<IUserTypeWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getUserTypeWestIdentifier(o1) === this.getUserTypeWestIdentifier(o2) : o1 === o2;
  }

  addUserTypeWestToCollectionIfMissing<Type extends Pick<IUserTypeWest, 'id'>>(
    userTypeWestCollection: Type[],
    ...userTypeWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const userTypeWests: Type[] = userTypeWestsToCheck.filter(isPresent);
    if (userTypeWests.length > 0) {
      const userTypeWestCollectionIdentifiers = userTypeWestCollection.map(
        userTypeWestItem => this.getUserTypeWestIdentifier(userTypeWestItem)!
      );
      const userTypeWestsToAdd = userTypeWests.filter(userTypeWestItem => {
        const userTypeWestIdentifier = this.getUserTypeWestIdentifier(userTypeWestItem);
        if (userTypeWestCollectionIdentifiers.includes(userTypeWestIdentifier)) {
          return false;
        }
        userTypeWestCollectionIdentifiers.push(userTypeWestIdentifier);
        return true;
      });
      return [...userTypeWestsToAdd, ...userTypeWestCollection];
    }
    return userTypeWestCollection;
  }
}
