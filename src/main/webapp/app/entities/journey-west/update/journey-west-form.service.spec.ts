import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../journey-west.test-samples';

import { JourneyWestFormService } from './journey-west-form.service';

describe('JourneyWest Form Service', () => {
  let service: JourneyWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourneyWestFormService);
  });

  describe('Service methods', () => {
    describe('createJourneyWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourneyWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            distance: expect.any(Object),
            journeyDuration: expect.any(Object),
            actualDepartureTime: expect.any(Object),
            plannedDepartureTime: expect.any(Object),
            actualArrivalTime: expect.any(Object),
            plannedArrivalTime: expect.any(Object),
            ticketPrice: expect.any(Object),
            numberOfStops: expect.any(Object),
            timeOfStops: expect.any(Object),
            minutesLate: expect.any(Object),
            journeyStatusId: expect.any(Object),
            trainId: expect.any(Object),
            companyId: expect.any(Object),
            departureRailwayStationId: expect.any(Object),
            arrivalRailwayStationId: expect.any(Object),
          })
        );
      });

      it('passing IJourneyWest should create a new form with FormGroup', () => {
        const formGroup = service.createJourneyWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            distance: expect.any(Object),
            journeyDuration: expect.any(Object),
            actualDepartureTime: expect.any(Object),
            plannedDepartureTime: expect.any(Object),
            actualArrivalTime: expect.any(Object),
            plannedArrivalTime: expect.any(Object),
            ticketPrice: expect.any(Object),
            numberOfStops: expect.any(Object),
            timeOfStops: expect.any(Object),
            minutesLate: expect.any(Object),
            journeyStatusId: expect.any(Object),
            trainId: expect.any(Object),
            companyId: expect.any(Object),
            departureRailwayStationId: expect.any(Object),
            arrivalRailwayStationId: expect.any(Object),
          })
        );
      });
    });

    describe('getJourneyWest', () => {
      it('should return NewJourneyWest for default JourneyWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJourneyWestFormGroup(sampleWithNewData);

        const journeyWest = service.getJourneyWest(formGroup) as any;

        expect(journeyWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourneyWest for empty JourneyWest initial value', () => {
        const formGroup = service.createJourneyWestFormGroup();

        const journeyWest = service.getJourneyWest(formGroup) as any;

        expect(journeyWest).toMatchObject({});
      });

      it('should return IJourneyWest', () => {
        const formGroup = service.createJourneyWestFormGroup(sampleWithRequiredData);

        const journeyWest = service.getJourneyWest(formGroup) as any;

        expect(journeyWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJourneyWest should not enable id FormControl', () => {
        const formGroup = service.createJourneyWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJourneyWest should disable id FormControl', () => {
        const formGroup = service.createJourneyWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
