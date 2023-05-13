import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../journey-status-west.test-samples';

import { JourneyStatusWestFormService } from './journey-status-west-form.service';

describe('JourneyStatusWest Form Service', () => {
  let service: JourneyStatusWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourneyStatusWestFormService);
  });

  describe('Service methods', () => {
    describe('createJourneyStatusWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourneyStatusWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IJourneyStatusWest should create a new form with FormGroup', () => {
        const formGroup = service.createJourneyStatusWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getJourneyStatusWest', () => {
      it('should return NewJourneyStatusWest for default JourneyStatusWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJourneyStatusWestFormGroup(sampleWithNewData);

        const journeyStatusWest = service.getJourneyStatusWest(formGroup) as any;

        expect(journeyStatusWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourneyStatusWest for empty JourneyStatusWest initial value', () => {
        const formGroup = service.createJourneyStatusWestFormGroup();

        const journeyStatusWest = service.getJourneyStatusWest(formGroup) as any;

        expect(journeyStatusWest).toMatchObject({});
      });

      it('should return IJourneyStatusWest', () => {
        const formGroup = service.createJourneyStatusWestFormGroup(sampleWithRequiredData);

        const journeyStatusWest = service.getJourneyStatusWest(formGroup) as any;

        expect(journeyStatusWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJourneyStatusWest should not enable id FormControl', () => {
        const formGroup = service.createJourneyStatusWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJourneyStatusWest should disable id FormControl', () => {
        const formGroup = service.createJourneyStatusWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
