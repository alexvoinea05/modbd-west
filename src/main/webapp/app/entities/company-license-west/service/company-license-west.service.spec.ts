import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICompanyLicenseWest } from '../company-license-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../company-license-west.test-samples';

import { CompanyLicenseWestService } from './company-license-west.service';

const requireRestSample: ICompanyLicenseWest = {
  ...sampleWithRequiredData,
};

describe('CompanyLicenseWest Service', () => {
  let service: CompanyLicenseWestService;
  let httpMock: HttpTestingController;
  let expectedResult: ICompanyLicenseWest | ICompanyLicenseWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CompanyLicenseWestService);
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

    it('should create a CompanyLicenseWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const companyLicenseWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(companyLicenseWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CompanyLicenseWest', () => {
      const companyLicenseWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(companyLicenseWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CompanyLicenseWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CompanyLicenseWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CompanyLicenseWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCompanyLicenseWestToCollectionIfMissing', () => {
      it('should add a CompanyLicenseWest to an empty array', () => {
        const companyLicenseWest: ICompanyLicenseWest = sampleWithRequiredData;
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing([], companyLicenseWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyLicenseWest);
      });

      it('should not add a CompanyLicenseWest to an array that contains it', () => {
        const companyLicenseWest: ICompanyLicenseWest = sampleWithRequiredData;
        const companyLicenseWestCollection: ICompanyLicenseWest[] = [
          {
            ...companyLicenseWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing(companyLicenseWestCollection, companyLicenseWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CompanyLicenseWest to an array that doesn't contain it", () => {
        const companyLicenseWest: ICompanyLicenseWest = sampleWithRequiredData;
        const companyLicenseWestCollection: ICompanyLicenseWest[] = [sampleWithPartialData];
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing(companyLicenseWestCollection, companyLicenseWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyLicenseWest);
      });

      it('should add only unique CompanyLicenseWest to an array', () => {
        const companyLicenseWestArray: ICompanyLicenseWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const companyLicenseWestCollection: ICompanyLicenseWest[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing(companyLicenseWestCollection, ...companyLicenseWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const companyLicenseWest: ICompanyLicenseWest = sampleWithRequiredData;
        const companyLicenseWest2: ICompanyLicenseWest = sampleWithPartialData;
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing([], companyLicenseWest, companyLicenseWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(companyLicenseWest);
        expect(expectedResult).toContain(companyLicenseWest2);
      });

      it('should accept null and undefined values', () => {
        const companyLicenseWest: ICompanyLicenseWest = sampleWithRequiredData;
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing([], null, companyLicenseWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(companyLicenseWest);
      });

      it('should return initial array if no CompanyLicenseWest is added', () => {
        const companyLicenseWestCollection: ICompanyLicenseWest[] = [sampleWithRequiredData];
        expectedResult = service.addCompanyLicenseWestToCollectionIfMissing(companyLicenseWestCollection, undefined, null);
        expect(expectedResult).toEqual(companyLicenseWestCollection);
      });
    });

    describe('compareCompanyLicenseWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCompanyLicenseWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCompanyLicenseWest(entity1, entity2);
        const compareResult2 = service.compareCompanyLicenseWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCompanyLicenseWest(entity1, entity2);
        const compareResult2 = service.compareCompanyLicenseWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCompanyLicenseWest(entity1, entity2);
        const compareResult2 = service.compareCompanyLicenseWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
