import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../train-type-west.test-samples';

import { TrainTypeWestFormService } from './train-type-west-form.service';

describe('TrainTypeWest Form Service', () => {
  let service: TrainTypeWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrainTypeWestFormService);
  });

  describe('Service methods', () => {
    describe('createTrainTypeWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrainTypeWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing ITrainTypeWest should create a new form with FormGroup', () => {
        const formGroup = service.createTrainTypeWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getTrainTypeWest', () => {
      it('should return NewTrainTypeWest for default TrainTypeWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrainTypeWestFormGroup(sampleWithNewData);

        const trainTypeWest = service.getTrainTypeWest(formGroup) as any;

        expect(trainTypeWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrainTypeWest for empty TrainTypeWest initial value', () => {
        const formGroup = service.createTrainTypeWestFormGroup();

        const trainTypeWest = service.getTrainTypeWest(formGroup) as any;

        expect(trainTypeWest).toMatchObject({});
      });

      it('should return ITrainTypeWest', () => {
        const formGroup = service.createTrainTypeWestFormGroup(sampleWithRequiredData);

        const trainTypeWest = service.getTrainTypeWest(formGroup) as any;

        expect(trainTypeWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrainTypeWest should not enable id FormControl', () => {
        const formGroup = service.createTrainTypeWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrainTypeWest should disable id FormControl', () => {
        const formGroup = service.createTrainTypeWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
