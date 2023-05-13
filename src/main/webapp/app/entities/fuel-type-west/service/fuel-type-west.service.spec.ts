import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFuelTypeWest } from '../fuel-type-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fuel-type-west.test-samples';

import { FuelTypeWestService } from './fuel-type-west.service';

const requireRestSample: IFuelTypeWest = {
  ...sampleWithRequiredData,
};

describe('FuelTypeWest Service', () => {
  let service: FuelTypeWestService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelTypeWest | IFuelTypeWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FuelTypeWestService);
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

    it('should create a FuelTypeWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fuelTypeWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelTypeWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelTypeWest', () => {
      const fuelTypeWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelTypeWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelTypeWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelTypeWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelTypeWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelTypeWestToCollectionIfMissing', () => {
      it('should add a FuelTypeWest to an empty array', () => {
        const fuelTypeWest: IFuelTypeWest = sampleWithRequiredData;
        expectedResult = service.addFuelTypeWestToCollectionIfMissing([], fuelTypeWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTypeWest);
      });

      it('should not add a FuelTypeWest to an array that contains it', () => {
        const fuelTypeWest: IFuelTypeWest = sampleWithRequiredData;
        const fuelTypeWestCollection: IFuelTypeWest[] = [
          {
            ...fuelTypeWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelTypeWestToCollectionIfMissing(fuelTypeWestCollection, fuelTypeWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelTypeWest to an array that doesn't contain it", () => {
        const fuelTypeWest: IFuelTypeWest = sampleWithRequiredData;
        const fuelTypeWestCollection: IFuelTypeWest[] = [sampleWithPartialData];
        expectedResult = service.addFuelTypeWestToCollectionIfMissing(fuelTypeWestCollection, fuelTypeWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTypeWest);
      });

      it('should add only unique FuelTypeWest to an array', () => {
        const fuelTypeWestArray: IFuelTypeWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelTypeWestCollection: IFuelTypeWest[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTypeWestToCollectionIfMissing(fuelTypeWestCollection, ...fuelTypeWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelTypeWest: IFuelTypeWest = sampleWithRequiredData;
        const fuelTypeWest2: IFuelTypeWest = sampleWithPartialData;
        expectedResult = service.addFuelTypeWestToCollectionIfMissing([], fuelTypeWest, fuelTypeWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTypeWest);
        expect(expectedResult).toContain(fuelTypeWest2);
      });

      it('should accept null and undefined values', () => {
        const fuelTypeWest: IFuelTypeWest = sampleWithRequiredData;
        expectedResult = service.addFuelTypeWestToCollectionIfMissing([], null, fuelTypeWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTypeWest);
      });

      it('should return initial array if no FuelTypeWest is added', () => {
        const fuelTypeWestCollection: IFuelTypeWest[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTypeWestToCollectionIfMissing(fuelTypeWestCollection, undefined, null);
        expect(expectedResult).toEqual(fuelTypeWestCollection);
      });
    });

    describe('compareFuelTypeWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelTypeWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFuelTypeWest(entity1, entity2);
        const compareResult2 = service.compareFuelTypeWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFuelTypeWest(entity1, entity2);
        const compareResult2 = service.compareFuelTypeWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFuelTypeWest(entity1, entity2);
        const compareResult2 = service.compareFuelTypeWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
