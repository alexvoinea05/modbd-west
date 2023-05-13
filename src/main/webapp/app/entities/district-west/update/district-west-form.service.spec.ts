import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../district-west.test-samples';

import { DistrictWestFormService } from './district-west-form.service';

describe('DistrictWest Form Service', () => {
  let service: DistrictWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DistrictWestFormService);
  });

  describe('Service methods', () => {
    describe('createDistrictWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDistrictWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            region: expect.any(Object),
          })
        );
      });

      it('passing IDistrictWest should create a new form with FormGroup', () => {
        const formGroup = service.createDistrictWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            region: expect.any(Object),
          })
        );
      });
    });

    describe('getDistrictWest', () => {
      it('should return NewDistrictWest for default DistrictWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDistrictWestFormGroup(sampleWithNewData);

        const districtWest = service.getDistrictWest(formGroup) as any;

        expect(districtWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewDistrictWest for empty DistrictWest initial value', () => {
        const formGroup = service.createDistrictWestFormGroup();

        const districtWest = service.getDistrictWest(formGroup) as any;

        expect(districtWest).toMatchObject({});
      });

      it('should return IDistrictWest', () => {
        const formGroup = service.createDistrictWestFormGroup(sampleWithRequiredData);

        const districtWest = service.getDistrictWest(formGroup) as any;

        expect(districtWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDistrictWest should not enable id FormControl', () => {
        const formGroup = service.createDistrictWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDistrictWest should disable id FormControl', () => {
        const formGroup = service.createDistrictWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
