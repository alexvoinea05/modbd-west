import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICityWest } from '../city-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../city-west.test-samples';

import { CityWestService } from './city-west.service';

const requireRestSample: ICityWest = {
  ...sampleWithRequiredData,
};

describe('CityWest Service', () => {
  let service: CityWestService;
  let httpMock: HttpTestingController;
  let expectedResult: ICityWest | ICityWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CityWestService);
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

    it('should create a CityWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const cityWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cityWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CityWest', () => {
      const cityWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cityWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CityWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CityWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CityWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCityWestToCollectionIfMissing', () => {
      it('should add a CityWest to an empty array', () => {
        const cityWest: ICityWest = sampleWithRequiredData;
        expectedResult = service.addCityWestToCollectionIfMissing([], cityWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cityWest);
      });

      it('should not add a CityWest to an array that contains it', () => {
        const cityWest: ICityWest = sampleWithRequiredData;
        const cityWestCollection: ICityWest[] = [
          {
            ...cityWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCityWestToCollectionIfMissing(cityWestCollection, cityWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CityWest to an array that doesn't contain it", () => {
        const cityWest: ICityWest = sampleWithRequiredData;
        const cityWestCollection: ICityWest[] = [sampleWithPartialData];
        expectedResult = service.addCityWestToCollectionIfMissing(cityWestCollection, cityWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cityWest);
      });

      it('should add only unique CityWest to an array', () => {
        const cityWestArray: ICityWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cityWestCollection: ICityWest[] = [sampleWithRequiredData];
        expectedResult = service.addCityWestToCollectionIfMissing(cityWestCollection, ...cityWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cityWest: ICityWest = sampleWithRequiredData;
        const cityWest2: ICityWest = sampleWithPartialData;
        expectedResult = service.addCityWestToCollectionIfMissing([], cityWest, cityWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cityWest);
        expect(expectedResult).toContain(cityWest2);
      });

      it('should accept null and undefined values', () => {
        const cityWest: ICityWest = sampleWithRequiredData;
        expectedResult = service.addCityWestToCollectionIfMissing([], null, cityWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cityWest);
      });

      it('should return initial array if no CityWest is added', () => {
        const cityWestCollection: ICityWest[] = [sampleWithRequiredData];
        expectedResult = service.addCityWestToCollectionIfMissing(cityWestCollection, undefined, null);
        expect(expectedResult).toEqual(cityWestCollection);
      });
    });

    describe('compareCityWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCityWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCityWest(entity1, entity2);
        const compareResult2 = service.compareCityWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCityWest(entity1, entity2);
        const compareResult2 = service.compareCityWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCityWest(entity1, entity2);
        const compareResult2 = service.compareCityWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
