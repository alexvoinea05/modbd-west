import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../train-west.test-samples';

import { TrainWestFormService } from './train-west-form.service';

describe('TrainWest Form Service', () => {
  let service: TrainWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrainWestFormService);
  });

  describe('Service methods', () => {
    describe('createTrainWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrainWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            numberOfSeats: expect.any(Object),
            fuelTypeId: expect.any(Object),
            trainTypeId: expect.any(Object),
          })
        );
      });

      it('passing ITrainWest should create a new form with FormGroup', () => {
        const formGroup = service.createTrainWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            numberOfSeats: expect.any(Object),
            fuelTypeId: expect.any(Object),
            trainTypeId: expect.any(Object),
          })
        );
      });
    });

    describe('getTrainWest', () => {
      it('should return NewTrainWest for default TrainWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrainWestFormGroup(sampleWithNewData);

        const trainWest = service.getTrainWest(formGroup) as any;

        expect(trainWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrainWest for empty TrainWest initial value', () => {
        const formGroup = service.createTrainWestFormGroup();

        const trainWest = service.getTrainWest(formGroup) as any;

        expect(trainWest).toMatchObject({});
      });

      it('should return ITrainWest', () => {
        const formGroup = service.createTrainWestFormGroup(sampleWithRequiredData);

        const trainWest = service.getTrainWest(formGroup) as any;

        expect(trainWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrainWest should not enable id FormControl', () => {
        const formGroup = service.createTrainWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrainWest should disable id FormControl', () => {
        const formGroup = service.createTrainWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
