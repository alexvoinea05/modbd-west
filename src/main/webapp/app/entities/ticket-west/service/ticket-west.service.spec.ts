import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITicketWest } from '../ticket-west.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ticket-west.test-samples';

import { TicketWestService, RestTicketWest } from './ticket-west.service';

const requireRestSample: RestTicketWest = {
  ...sampleWithRequiredData,
  time: sampleWithRequiredData.time?.toJSON(),
};

describe('TicketWest Service', () => {
  let service: TicketWestService;
  let httpMock: HttpTestingController;
  let expectedResult: ITicketWest | ITicketWest[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TicketWestService);
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

    it('should create a TicketWest', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const ticketWest = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ticketWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TicketWest', () => {
      const ticketWest = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ticketWest).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TicketWest', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TicketWest', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TicketWest', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTicketWestToCollectionIfMissing', () => {
      it('should add a TicketWest to an empty array', () => {
        const ticketWest: ITicketWest = sampleWithRequiredData;
        expectedResult = service.addTicketWestToCollectionIfMissing([], ticketWest);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticketWest);
      });

      it('should not add a TicketWest to an array that contains it', () => {
        const ticketWest: ITicketWest = sampleWithRequiredData;
        const ticketWestCollection: ITicketWest[] = [
          {
            ...ticketWest,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTicketWestToCollectionIfMissing(ticketWestCollection, ticketWest);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TicketWest to an array that doesn't contain it", () => {
        const ticketWest: ITicketWest = sampleWithRequiredData;
        const ticketWestCollection: ITicketWest[] = [sampleWithPartialData];
        expectedResult = service.addTicketWestToCollectionIfMissing(ticketWestCollection, ticketWest);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticketWest);
      });

      it('should add only unique TicketWest to an array', () => {
        const ticketWestArray: ITicketWest[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ticketWestCollection: ITicketWest[] = [sampleWithRequiredData];
        expectedResult = service.addTicketWestToCollectionIfMissing(ticketWestCollection, ...ticketWestArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ticketWest: ITicketWest = sampleWithRequiredData;
        const ticketWest2: ITicketWest = sampleWithPartialData;
        expectedResult = service.addTicketWestToCollectionIfMissing([], ticketWest, ticketWest2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ticketWest);
        expect(expectedResult).toContain(ticketWest2);
      });

      it('should accept null and undefined values', () => {
        const ticketWest: ITicketWest = sampleWithRequiredData;
        expectedResult = service.addTicketWestToCollectionIfMissing([], null, ticketWest, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ticketWest);
      });

      it('should return initial array if no TicketWest is added', () => {
        const ticketWestCollection: ITicketWest[] = [sampleWithRequiredData];
        expectedResult = service.addTicketWestToCollectionIfMissing(ticketWestCollection, undefined, null);
        expect(expectedResult).toEqual(ticketWestCollection);
      });
    });

    describe('compareTicketWest', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTicketWest(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTicketWest(entity1, entity2);
        const compareResult2 = service.compareTicketWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTicketWest(entity1, entity2);
        const compareResult2 = service.compareTicketWest(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTicketWest(entity1, entity2);
        const compareResult2 = service.compareTicketWest(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
