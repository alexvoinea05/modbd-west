import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJourneyStatusWest } from '../journey-status-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../journey-status-west.test-samples';

import { JourneyStatusWestService } from './journey-status-west.service';

const requireRestSample: IJourneyStatusWest = {
  ...sampleWithRequiredData,
};

describe('JourneyStatusWest Service', () => {
  let service: JourneyStatusWestService;
  let httpMock: HttpTestingController;
  let expectedResult: IJourneyStatusWest | IJourneyStatusWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JourneyStatusWestService);
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

    it('should create a JourneyStatusWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const journeyStatusWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(journeyStatusWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JourneyStatusWest', () => {
      const journeyStatusWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(journeyStatusWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JourneyStatusWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JourneyStatusWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JourneyStatusWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJourneyStatusWestToCollectionIfMissing', () => {
      it('should add a JourneyStatusWest to an empty array', () => {
        const journeyStatusWest: IJourneyStatusWest = sampleWithRequiredData;
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing([], journeyStatusWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyStatusWest);
      });

      it('should not add a JourneyStatusWest to an array that contains it', () => {
        const journeyStatusWest: IJourneyStatusWest = sampleWithRequiredData;
        const journeyStatusWestCollection: IJourneyStatusWest[] = [
          {
            ...journeyStatusWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing(journeyStatusWestCollection, journeyStatusWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JourneyStatusWest to an array that doesn't contain it", () => {
        const journeyStatusWest: IJourneyStatusWest = sampleWithRequiredData;
        const journeyStatusWestCollection: IJourneyStatusWest[] = [sampleWithPartialData];
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing(journeyStatusWestCollection, journeyStatusWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyStatusWest);
      });

      it('should add only unique JourneyStatusWest to an array', () => {
        const journeyStatusWestArray: IJourneyStatusWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const journeyStatusWestCollection: IJourneyStatusWest[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing(journeyStatusWestCollection, ...journeyStatusWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const journeyStatusWest: IJourneyStatusWest = sampleWithRequiredData;
        const journeyStatusWest2: IJourneyStatusWest = sampleWithPartialData;
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing([], journeyStatusWest, journeyStatusWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyStatusWest);
        expect(expectedResult).toContain(journeyStatusWest2);
      });

      it('should accept null and undefined values', () => {
        const journeyStatusWest: IJourneyStatusWest = sampleWithRequiredData;
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing([], null, journeyStatusWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyStatusWest);
      });

      it('should return initial array if no JourneyStatusWest is added', () => {
        const journeyStatusWestCollection: IJourneyStatusWest[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyStatusWestToCollectionIfMissing(journeyStatusWestCollection, undefined, null);
        expect(expectedResult).toEqual(journeyStatusWestCollection);
      });
    });

    describe('compareJourneyStatusWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJourneyStatusWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJourneyStatusWest(entity1, entity2);
        const compareResult2 = service.compareJourneyStatusWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJourneyStatusWest(entity1, entity2);
        const compareResult2 = service.compareJourneyStatusWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJourneyStatusWest(entity1, entity2);
        const compareResult2 = service.compareJourneyStatusWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
