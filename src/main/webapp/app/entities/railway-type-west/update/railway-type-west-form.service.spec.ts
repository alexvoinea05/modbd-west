import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../railway-type-west.test-samples';

import { RailwayTypeWestFormService } from './railway-type-west-form.service';

describe('RailwayTypeWest Form Service', () => {
  let service: RailwayTypeWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RailwayTypeWestFormService);
  });

  describe('Service methods', () => {
    describe('createRailwayTypeWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRailwayTypeWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IRailwayTypeWest should create a new form with FormGroup', () => {
        const formGroup = service.createRailwayTypeWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getRailwayTypeWest', () => {
      it('should return NewRailwayTypeWest for default RailwayTypeWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRailwayTypeWestFormGroup(sampleWithNewData);

        const railwayTypeWest = service.getRailwayTypeWest(formGroup) as any;

        expect(railwayTypeWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewRailwayTypeWest for empty RailwayTypeWest initial value', () => {
        const formGroup = service.createRailwayTypeWestFormGroup();

        const railwayTypeWest = service.getRailwayTypeWest(formGroup) as any;

        expect(railwayTypeWest).toMatchObject({});
      });

      it('should return IRailwayTypeWest', () => {
        const formGroup = service.createRailwayTypeWestFormGroup(sampleWithRequiredData);

        const railwayTypeWest = service.getRailwayTypeWest(formGroup) as any;

        expect(railwayTypeWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRailwayTypeWest should not enable id FormControl', () => {
        const formGroup = service.createRailwayTypeWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRailwayTypeWest should disable id FormControl', () => {
        const formGroup = service.createRailwayTypeWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
