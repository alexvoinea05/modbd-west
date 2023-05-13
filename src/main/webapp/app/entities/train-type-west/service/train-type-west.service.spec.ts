import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrainTypeWest } from '../train-type-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../train-type-west.test-samples';

import { TrainTypeWestService } from './train-type-west.service';

const requireRestSample: ITrainTypeWest = {
  ...sampleWithRequiredData,
};

describe('TrainTypeWest Service', () => {
  let service: TrainTypeWestService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrainTypeWest | ITrainTypeWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainTypeWestService);
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

    it('should create a TrainTypeWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trainTypeWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trainTypeWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainTypeWest', () => {
      const trainTypeWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trainTypeWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainTypeWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainTypeWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrainTypeWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrainTypeWestToCollectionIfMissing', () => {
      it('should add a TrainTypeWest to an empty array', () => {
        const trainTypeWest: ITrainTypeWest = sampleWithRequiredData;
        expectedResult = service.addTrainTypeWestToCollectionIfMissing([], trainTypeWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainTypeWest);
      });

      it('should not add a TrainTypeWest to an array that contains it', () => {
        const trainTypeWest: ITrainTypeWest = sampleWithRequiredData;
        const trainTypeWestCollection: ITrainTypeWest[] = [
          {
            ...trainTypeWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrainTypeWestToCollectionIfMissing(trainTypeWestCollection, trainTypeWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainTypeWest to an array that doesn't contain it", () => {
        const trainTypeWest: ITrainTypeWest = sampleWithRequiredData;
        const trainTypeWestCollection: ITrainTypeWest[] = [sampleWithPartialData];
        expectedResult = service.addTrainTypeWestToCollectionIfMissing(trainTypeWestCollection, trainTypeWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainTypeWest);
      });

      it('should add only unique TrainTypeWest to an array', () => {
        const trainTypeWestArray: ITrainTypeWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trainTypeWestCollection: ITrainTypeWest[] = [sampleWithRequiredData];
        expectedResult = service.addTrainTypeWestToCollectionIfMissing(trainTypeWestCollection, ...trainTypeWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainTypeWest: ITrainTypeWest = sampleWithRequiredData;
        const trainTypeWest2: ITrainTypeWest = sampleWithPartialData;
        expectedResult = service.addTrainTypeWestToCollectionIfMissing([], trainTypeWest, trainTypeWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainTypeWest);
        expect(expectedResult).toContain(trainTypeWest2);
      });

      it('should accept null and undefined values', () => {
        const trainTypeWest: ITrainTypeWest = sampleWithRequiredData;
        expectedResult = service.addTrainTypeWestToCollectionIfMissing([], null, trainTypeWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainTypeWest);
      });

      it('should return initial array if no TrainTypeWest is added', () => {
        const trainTypeWestCollection: ITrainTypeWest[] = [sampleWithRequiredData];
        expectedResult = service.addTrainTypeWestToCollectionIfMissing(trainTypeWestCollection, undefined, null);
        expect(expectedResult).toEqual(trainTypeWestCollection);
      });
    });

    describe('compareTrainTypeWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrainTypeWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTrainTypeWest(entity1, entity2);
        const compareResult2 = service.compareTrainTypeWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTrainTypeWest(entity1, entity2);
        const compareResult2 = service.compareTrainTypeWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTrainTypeWest(entity1, entity2);
        const compareResult2 = service.compareTrainTypeWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
