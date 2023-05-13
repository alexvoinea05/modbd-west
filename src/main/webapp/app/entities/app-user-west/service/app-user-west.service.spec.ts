import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAppUserWest } from '../app-user-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../app-user-west.test-samples';

import { AppUserWestService } from './app-user-west.service';

const requireRestSample: IAppUserWest = {
  ...sampleWithRequiredData,
};

describe('AppUserWest Service', () => {
  let service: AppUserWestService;
  let httpMock: HttpTestingController;
  let expectedResult: IAppUserWest | IAppUserWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AppUserWestService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AppUserWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const appUserWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(appUserWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AppUserWest', () => {
      const appUserWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(appUserWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AppUserWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AppUserWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AppUserWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAppUserWestToCollectionIfMissing', () => {
      it('should add a AppUserWest to an empty array', () => {
        const appUserWest: IAppUserWest = sampleWithRequiredData;
        expectedResult = service.addAppUserWestToCollectionIfMissing([], appUserWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appUserWest);
      });

      it('should not add a AppUserWest to an array that contains it', () => {
        const appUserWest: IAppUserWest = sampleWithRequiredData;
        const appUserWestCollection: IAppUserWest[] = [
          {
            ...appUserWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAppUserWestToCollectionIfMissing(appUserWestCollection, appUserWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AppUserWest to an array that doesn't contain it", () => {
        const appUserWest: IAppUserWest = sampleWithRequiredData;
        const appUserWestCollection: IAppUserWest[] = [sampleWithPartialData];
        expectedResult = service.addAppUserWestToCollectionIfMissing(appUserWestCollection, appUserWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appUserWest);
      });

      it('should add only unique AppUserWest to an array', () => {
        const appUserWestArray: IAppUserWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const appUserWestCollection: IAppUserWest[] = [sampleWithRequiredData];
        expectedResult = service.addAppUserWestToCollectionIfMissing(appUserWestCollection, ...appUserWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const appUserWest: IAppUserWest = sampleWithRequiredData;
        const appUserWest2: IAppUserWest = sampleWithPartialData;
        expectedResult = service.addAppUserWestToCollectionIfMissing([], appUserWest, appUserWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(appUserWest);
        expect(expectedResult).toContain(appUserWest2);
      });

      it('should accept null and undefined values', () => {
        const appUserWest: IAppUserWest = sampleWithRequiredData;
        expectedResult = service.addAppUserWestToCollectionIfMissing([], null, appUserWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(appUserWest);
      });

      it('should return initial array if no AppUserWest is added', () => {
        const appUserWestCollection: IAppUserWest[] = [sampleWithRequiredData];
        expectedResult = service.addAppUserWestToCollectionIfMissing(appUserWestCollection, undefined, null);
        expect(expectedResult).toEqual(appUserWestCollection);
      });
    });

    describe('compareAppUserWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAppUserWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAppUserWest(entity1, entity2);
        const compareResult2 = service.compareAppUserWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAppUserWest(entity1, entity2);
        const compareResult2 = service.compareAppUserWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAppUserWest(entity1, entity2);
        const compareResult2 = service.compareAppUserWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
