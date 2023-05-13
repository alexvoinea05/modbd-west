import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRailwayStationWest } from '../railway-station-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../railway-station-west.test-samples';

import { RailwayStationWestService } from './railway-station-west.service';

const requireRestSample: IRailwayStationWest = {
  ...sampleWithRequiredData,
};

describe('RailwayStationWest Service', () => {
  let service: RailwayStationWestService;
  let httpMock: HttpTestingController;
  let expectedResult: IRailwayStationWest | IRailwayStationWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RailwayStationWestService);
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

    it('should create a RailwayStationWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const railwayStationWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(railwayStationWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RailwayStationWest', () => {
      const railwayStationWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(railwayStationWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RailwayStationWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RailwayStationWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RailwayStationWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRailwayStationWestToCollectionIfMissing', () => {
      it('should add a RailwayStationWest to an empty array', () => {
        const railwayStationWest: IRailwayStationWest = sampleWithRequiredData;
        expectedResult = service.addRailwayStationWestToCollectionIfMissing([], railwayStationWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayStationWest);
      });

      it('should not add a RailwayStationWest to an array that contains it', () => {
        const railwayStationWest: IRailwayStationWest = sampleWithRequiredData;
        const railwayStationWestCollection: IRailwayStationWest[] = [
          {
            ...railwayStationWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRailwayStationWestToCollectionIfMissing(railwayStationWestCollection, railwayStationWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RailwayStationWest to an array that doesn't contain it", () => {
        const railwayStationWest: IRailwayStationWest = sampleWithRequiredData;
        const railwayStationWestCollection: IRailwayStationWest[] = [sampleWithPartialData];
        expectedResult = service.addRailwayStationWestToCollectionIfMissing(railwayStationWestCollection, railwayStationWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayStationWest);
      });

      it('should add only unique RailwayStationWest to an array', () => {
        const railwayStationWestArray: IRailwayStationWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const railwayStationWestCollection: IRailwayStationWest[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayStationWestToCollectionIfMissing(railwayStationWestCollection, ...railwayStationWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const railwayStationWest: IRailwayStationWest = sampleWithRequiredData;
        const railwayStationWest2: IRailwayStationWest = sampleWithPartialData;
        expectedResult = service.addRailwayStationWestToCollectionIfMissing([], railwayStationWest, railwayStationWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayStationWest);
        expect(expectedResult).toContain(railwayStationWest2);
      });

      it('should accept null and undefined values', () => {
        const railwayStationWest: IRailwayStationWest = sampleWithRequiredData;
        expectedResult = service.addRailwayStationWestToCollectionIfMissing([], null, railwayStationWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayStationWest);
      });

      it('should return initial array if no RailwayStationWest is added', () => {
        const railwayStationWestCollection: IRailwayStationWest[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayStationWestToCollectionIfMissing(railwayStationWestCollection, undefined, null);
        expect(expectedResult).toEqual(railwayStationWestCollection);
      });
    });

    describe('compareRailwayStationWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRailwayStationWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRailwayStationWest(entity1, entity2);
        const compareResult2 = service.compareRailwayStationWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRailwayStationWest(entity1, entity2);
        const compareResult2 = service.compareRailwayStationWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRailwayStationWest(entity1, entity2);
        const compareResult2 = service.compareRailwayStationWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
