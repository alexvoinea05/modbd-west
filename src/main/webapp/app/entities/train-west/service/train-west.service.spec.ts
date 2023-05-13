import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrainWest } from '../train-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../train-west.test-samples';

import { TrainWestService } from './train-west.service';

const requireRestSample: ITrainWest = {
  ...sampleWithRequiredData,
};

describe('TrainWest Service', () => {
  let service: TrainWestService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrainWest | ITrainWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainWestService);
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

    it('should create a TrainWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trainWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trainWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainWest', () => {
      const trainWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trainWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrainWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrainWestToCollectionIfMissing', () => {
      it('should add a TrainWest to an empty array', () => {
        const trainWest: ITrainWest = sampleWithRequiredData;
        expectedResult = service.addTrainWestToCollectionIfMissing([], trainWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainWest);
      });

      it('should not add a TrainWest to an array that contains it', () => {
        const trainWest: ITrainWest = sampleWithRequiredData;
        const trainWestCollection: ITrainWest[] = [
          {
            ...trainWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrainWestToCollectionIfMissing(trainWestCollection, trainWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainWest to an array that doesn't contain it", () => {
        const trainWest: ITrainWest = sampleWithRequiredData;
        const trainWestCollection: ITrainWest[] = [sampleWithPartialData];
        expectedResult = service.addTrainWestToCollectionIfMissing(trainWestCollection, trainWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainWest);
      });

      it('should add only unique TrainWest to an array', () => {
        const trainWestArray: ITrainWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trainWestCollection: ITrainWest[] = [sampleWithRequiredData];
        expectedResult = service.addTrainWestToCollectionIfMissing(trainWestCollection, ...trainWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainWest: ITrainWest = sampleWithRequiredData;
        const trainWest2: ITrainWest = sampleWithPartialData;
        expectedResult = service.addTrainWestToCollectionIfMissing([], trainWest, trainWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainWest);
        expect(expectedResult).toContain(trainWest2);
      });

      it('should accept null and undefined values', () => {
        const trainWest: ITrainWest = sampleWithRequiredData;
        expectedResult = service.addTrainWestToCollectionIfMissing([], null, trainWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainWest);
      });

      it('should return initial array if no TrainWest is added', () => {
        const trainWestCollection: ITrainWest[] = [sampleWithRequiredData];
        expectedResult = service.addTrainWestToCollectionIfMissing(trainWestCollection, undefined, null);
        expect(expectedResult).toEqual(trainWestCollection);
      });
    });

    describe('compareTrainWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrainWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTrainWest(entity1, entity2);
        const compareResult2 = service.compareTrainWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTrainWest(entity1, entity2);
        const compareResult2 = service.compareTrainWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTrainWest(entity1, entity2);
        const compareResult2 = service.compareTrainWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
