import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../railway-station-west.test-samples';

import { RailwayStationWestFormService } from './railway-station-west-form.service';

describe('RailwayStationWest Form Service', () => {
  let service: RailwayStationWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RailwayStationWestFormService);
  });

  describe('Service methods', () => {
    describe('createRailwayStationWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRailwayStationWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            railwayStationName: expect.any(Object),
            railwayTypeId: expect.any(Object),
            addressId: expect.any(Object),
          })
        );
      });

      it('passing IRailwayStationWest should create a new form with FormGroup', () => {
        const formGroup = service.createRailwayStationWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            railwayStationName: expect.any(Object),
            railwayTypeId: expect.any(Object),
            addressId: expect.any(Object),
          })
        );
      });
    });

    describe('getRailwayStationWest', () => {
      it('should return NewRailwayStationWest for default RailwayStationWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRailwayStationWestFormGroup(sampleWithNewData);

        const railwayStationWest = service.getRailwayStationWest(formGroup) as any;

        expect(railwayStationWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewRailwayStationWest for empty RailwayStationWest initial value', () => {
        const formGroup = service.createRailwayStationWestFormGroup();

        const railwayStationWest = service.getRailwayStationWest(formGroup) as any;

        expect(railwayStationWest).toMatchObject({});
      });

      it('should return IRailwayStationWest', () => {
        const formGroup = service.createRailwayStationWestFormGroup(sampleWithRequiredData);

        const railwayStationWest = service.getRailwayStationWest(formGroup) as any;

        expect(railwayStationWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRailwayStationWest should not enable id FormControl', () => {
        const formGroup = service.createRailwayStationWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRailwayStationWest should disable id FormControl', () => {
        const formGroup = service.createRailwayStationWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
