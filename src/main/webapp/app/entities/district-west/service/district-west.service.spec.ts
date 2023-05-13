import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDistrictWest } from '../district-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../district-west.test-samples';

import { DistrictWestService } from './district-west.service';

const requireRestSample: IDistrictWest = {
  ...sampleWithRequiredData,
};

describe('DistrictWest Service', () => {
  let service: DistrictWestService;
  let httpMock: HttpTestingController;
  let expectedResult: IDistrictWest | IDistrictWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DistrictWestService);
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

    it('should create a DistrictWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const districtWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(districtWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DistrictWest', () => {
      const districtWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(districtWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DistrictWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DistrictWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DistrictWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDistrictWestToCollectionIfMissing', () => {
      it('should add a DistrictWest to an empty array', () => {
        const districtWest: IDistrictWest = sampleWithRequiredData;
        expectedResult = service.addDistrictWestToCollectionIfMissing([], districtWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(districtWest);
      });

      it('should not add a DistrictWest to an array that contains it', () => {
        const districtWest: IDistrictWest = sampleWithRequiredData;
        const districtWestCollection: IDistrictWest[] = [
          {
            ...districtWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDistrictWestToCollectionIfMissing(districtWestCollection, districtWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DistrictWest to an array that doesn't contain it", () => {
        const districtWest: IDistrictWest = sampleWithRequiredData;
        const districtWestCollection: IDistrictWest[] = [sampleWithPartialData];
        expectedResult = service.addDistrictWestToCollectionIfMissing(districtWestCollection, districtWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(districtWest);
      });

      it('should add only unique DistrictWest to an array', () => {
        const districtWestArray: IDistrictWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const districtWestCollection: IDistrictWest[] = [sampleWithRequiredData];
        expectedResult = service.addDistrictWestToCollectionIfMissing(districtWestCollection, ...districtWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const districtWest: IDistrictWest = sampleWithRequiredData;
        const districtWest2: IDistrictWest = sampleWithPartialData;
        expectedResult = service.addDistrictWestToCollectionIfMissing([], districtWest, districtWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(districtWest);
        expect(expectedResult).toContain(districtWest2);
      });

      it('should accept null and undefined values', () => {
        const districtWest: IDistrictWest = sampleWithRequiredData;
        expectedResult = service.addDistrictWestToCollectionIfMissing([], null, districtWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(districtWest);
      });

      it('should return initial array if no DistrictWest is added', () => {
        const districtWestCollection: IDistrictWest[] = [sampleWithRequiredData];
        expectedResult = service.addDistrictWestToCollectionIfMissing(districtWestCollection, undefined, null);
        expect(expectedResult).toEqual(districtWestCollection);
      });
    });

    describe('compareDistrictWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDistrictWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDistrictWest(entity1, entity2);
        const compareResult2 = service.compareDistrictWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDistrictWest(entity1, entity2);
        const compareResult2 = service.compareDistrictWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDistrictWest(entity1, entity2);
        const compareResult2 = service.compareDistrictWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
