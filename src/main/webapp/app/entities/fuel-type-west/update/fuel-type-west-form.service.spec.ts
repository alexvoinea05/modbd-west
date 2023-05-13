import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fuel-type-west.test-samples';

import { FuelTypeWestFormService } from './fuel-type-west-form.service';

describe('FuelTypeWest Form Service', () => {
  let service: FuelTypeWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelTypeWestFormService);
  });

  describe('Service methods', () => {
    describe('createFuelTypeWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelTypeWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IFuelTypeWest should create a new form with FormGroup', () => {
        const formGroup = service.createFuelTypeWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getFuelTypeWest', () => {
      it('should return NewFuelTypeWest for default FuelTypeWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFuelTypeWestFormGroup(sampleWithNewData);

        const fuelTypeWest = service.getFuelTypeWest(formGroup) as any;

        expect(fuelTypeWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelTypeWest for empty FuelTypeWest initial value', () => {
        const formGroup = service.createFuelTypeWestFormGroup();

        const fuelTypeWest = service.getFuelTypeWest(formGroup) as any;

        expect(fuelTypeWest).toMatchObject({});
      });

      it('should return IFuelTypeWest', () => {
        const formGroup = service.createFuelTypeWestFormGroup(sampleWithRequiredData);

        const fuelTypeWest = service.getFuelTypeWest(formGroup) as any;

        expect(fuelTypeWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelTypeWest should not enable id FormControl', () => {
        const formGroup = service.createFuelTypeWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelTypeWest should disable id FormControl', () => {
        const formGroup = service.createFuelTypeWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
