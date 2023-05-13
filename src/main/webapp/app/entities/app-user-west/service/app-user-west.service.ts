import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAppUserWest, NewAppUserWest } from '../app-user-west.model';

export type PartialUpdateAppUserWest = Partial<IAppUserWest> & Pick<IAppUserWest, 'id'>;

export type EntityResponseType = HttpResponse<IAppUserWest>;
export type EntityArrayResponseType = HttpResponse<IAppUserWest[]>;

@Injectable({ providedIn: 'root' })
export class AppUserWestService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/app-user-wests');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(appUserWest: NewAppUserWest): Observable<EntityResponseType> {
    return this.http.post<IAppUserWest>(this.resourceUrl, appUserWest, { observe: 'response' });
  }

  update(appUserWest: IAppUserWest): Observable<EntityResponseType> {
    return this.http.put<IAppUserWest>(`${this.resourceUrl}/${this.getAppUserWestIdentifier(appUserWest)}`, appUserWest, {
      observe: 'response',
    });
  }

  partialUpdate(appUserWest: PartialUpdateAppUserWest): Observable<EntityResponseType> {
    return this.http.patch<IAppUserWest>(`${this.resourceUrl}/${this.getAppUserWestIdentifier(appUserWest)}`, appUserWest, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppUserWest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppUserWest[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAppUserWestIdentifier(appUserWest: Pick<IAppUserWest, 'id'>): number {
    return appUserWest.id;
  }

  compareAppUserWest(o1: Pick<IAppUserWest, 'id'> | null, o2: Pick<IAppUserWest, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppUserWestIdentifier(o1) === this.getAppUserWestIdentifier(o2) : o1 === o2;
  }

  addAppUserWestToCollectionIfMissing<Type extends Pick<IAppUserWest, 'id'>>(
    appUserWestCollection: Type[],
    ...appUserWestsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appUserWests: Type[] = appUserWestsToCheck.filter(isPresent);
    if (appUserWests.length > 0) {
      const appUserWestCollectionIdentifiers = appUserWestCollection.map(
        appUserWestItem => this.getAppUserWestIdentifier(appUserWestItem)!
      );
      const appUserWestsToAdd = appUserWests.filter(appUserWestItem => {
        const appUserWestIdentifier = this.getAppUserWestIdentifier(appUserWestItem);
        if (appUserWestCollectionIdentifiers.includes(appUserWestIdentifier)) {
          return false;
        }
        appUserWestCollectionIdentifiers.push(appUserWestIdentifier);
        return true;
      });
      return [...appUserWestsToAdd, ...appUserWestCollection];
    }
    return appUserWestCollection;
  }
}
