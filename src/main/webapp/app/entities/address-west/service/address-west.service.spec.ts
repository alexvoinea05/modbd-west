import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAddressWest } from '../address-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../address-west.test-samples';

import { AddressWestService } from './address-west.service';

const requireRestSample: IAddressWest = {
  ...sampleWithRequiredData,
};

describe('AddressWest Service', () => {
  let service: AddressWestService;
  let httpMock: HttpTestingController;
  let expectedResult: IAddressWest | IAddressWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AddressWestService);
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

    it('should create a AddressWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const addressWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(addressWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AddressWest', () => {
      const addressWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(addressWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AddressWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AddressWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AddressWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAddressWestToCollectionIfMissing', () => {
      it('should add a AddressWest to an empty array', () => {
        const addressWest: IAddressWest = sampleWithRequiredData;
        expectedResult = service.addAddressWestToCollectionIfMissing([], addressWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(addressWest);
      });

      it('should not add a AddressWest to an array that contains it', () => {
        const addressWest: IAddressWest = sampleWithRequiredData;
        const addressWestCollection: IAddressWest[] = [
          {
            ...addressWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAddressWestToCollectionIfMissing(addressWestCollection, addressWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AddressWest to an array that doesn't contain it", () => {
        const addressWest: IAddressWest = sampleWithRequiredData;
        const addressWestCollection: IAddressWest[] = [sampleWithPartialData];
        expectedResult = service.addAddressWestToCollectionIfMissing(addressWestCollection, addressWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addressWest);
      });

      it('should add only unique AddressWest to an array', () => {
        const addressWestArray: IAddressWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const addressWestCollection: IAddressWest[] = [sampleWithRequiredData];
        expectedResult = service.addAddressWestToCollectionIfMissing(addressWestCollection, ...addressWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const addressWest: IAddressWest = sampleWithRequiredData;
        const addressWest2: IAddressWest = sampleWithPartialData;
        expectedResult = service.addAddressWestToCollectionIfMissing([], addressWest, addressWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(addressWest);
        expect(expectedResult).toContain(addressWest2);
      });

      it('should accept null and undefined values', () => {
        const addressWest: IAddressWest = sampleWithRequiredData;
        expectedResult = service.addAddressWestToCollectionIfMissing([], null, addressWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(addressWest);
      });

      it('should return initial array if no AddressWest is added', () => {
        const addressWestCollection: IAddressWest[] = [sampleWithRequiredData];
        expectedResult = service.addAddressWestToCollectionIfMissing(addressWestCollection, undefined, null);
        expect(expectedResult).toEqual(addressWestCollection);
      });
    });

    describe('compareAddressWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAddressWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAddressWest(entity1, entity2);
        const compareResult2 = service.compareAddressWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAddressWest(entity1, entity2);
        const compareResult2 = service.compareAddressWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAddressWest(entity1, entity2);
        const compareResult2 = service.compareAddressWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
