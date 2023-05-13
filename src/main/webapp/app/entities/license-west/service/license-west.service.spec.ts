import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILicenseWest } from '../license-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../license-west.test-samples';

import { LicenseWestService } from './license-west.service';

const requireRestSample: ILicenseWest = {
  ...sampleWithRequiredData,
};

describe('LicenseWest Service', () => {
  let service: LicenseWestService;
  let httpMock: HttpTestingController;
  let expectedResult: ILicenseWest | ILicenseWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LicenseWestService);
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

    it('should create a LicenseWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const licenseWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(licenseWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LicenseWest', () => {
      const licenseWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(licenseWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LicenseWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LicenseWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a LicenseWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addLicenseWestToCollectionIfMissing', () => {
      it('should add a LicenseWest to an empty array', () => {
        const licenseWest: ILicenseWest = sampleWithRequiredData;
        expectedResult = service.addLicenseWestToCollectionIfMissing([], licenseWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(licenseWest);
      });

      it('should not add a LicenseWest to an array that contains it', () => {
        const licenseWest: ILicenseWest = sampleWithRequiredData;
        const licenseWestCollection: ILicenseWest[] = [
          {
            ...licenseWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addLicenseWestToCollectionIfMissing(licenseWestCollection, licenseWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LicenseWest to an array that doesn't contain it", () => {
        const licenseWest: ILicenseWest = sampleWithRequiredData;
        const licenseWestCollection: ILicenseWest[] = [sampleWithPartialData];
        expectedResult = service.addLicenseWestToCollectionIfMissing(licenseWestCollection, licenseWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(licenseWest);
      });

      it('should add only unique LicenseWest to an array', () => {
        const licenseWestArray: ILicenseWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const licenseWestCollection: ILicenseWest[] = [sampleWithRequiredData];
        expectedResult = service.addLicenseWestToCollectionIfMissing(licenseWestCollection, ...licenseWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const licenseWest: ILicenseWest = sampleWithRequiredData;
        const licenseWest2: ILicenseWest = sampleWithPartialData;
        expectedResult = service.addLicenseWestToCollectionIfMissing([], licenseWest, licenseWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(licenseWest);
        expect(expectedResult).toContain(licenseWest2);
      });

      it('should accept null and undefined values', () => {
        const licenseWest: ILicenseWest = sampleWithRequiredData;
        expectedResult = service.addLicenseWestToCollectionIfMissing([], null, licenseWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(licenseWest);
      });

      it('should return initial array if no LicenseWest is added', () => {
        const licenseWestCollection: ILicenseWest[] = [sampleWithRequiredData];
        expectedResult = service.addLicenseWestToCollectionIfMissing(licenseWestCollection, undefined, null);
        expect(expectedResult).toEqual(licenseWestCollection);
      });
    });

    describe('compareLicenseWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareLicenseWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareLicenseWest(entity1, entity2);
        const compareResult2 = service.compareLicenseWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareLicenseWest(entity1, entity2);
        const compareResult2 = service.compareLicenseWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareLicenseWest(entity1, entity2);
        const compareResult2 = service.compareLicenseWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
