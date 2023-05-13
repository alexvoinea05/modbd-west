import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../city-west.test-samples';

import { CityWestFormService } from './city-west-form.service';

describe('CityWest Form Service', () => {
  let service: CityWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CityWestFormService);
  });

  describe('Service methods', () => {
    describe('createCityWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCityWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            districtId: expect.any(Object),
          })
        );
      });

      it('passing ICityWest should create a new form with FormGroup', () => {
        const formGroup = service.createCityWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            districtId: expect.any(Object),
          })
        );
      });
    });

    describe('getCityWest', () => {
      it('should return NewCityWest for default CityWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCityWestFormGroup(sampleWithNewData);

        const cityWest = service.getCityWest(formGroup) as any;

        expect(cityWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewCityWest for empty CityWest initial value', () => {
        const formGroup = service.createCityWestFormGroup();

        const cityWest = service.getCityWest(formGroup) as any;

        expect(cityWest).toMatchObject({});
      });

      it('should return ICityWest', () => {
        const formGroup = service.createCityWestFormGroup(sampleWithRequiredData);

        const cityWest = service.getCityWest(formGroup) as any;

        expect(cityWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICityWest should not enable id FormControl', () => {
        const formGroup = service.createCityWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCityWest should disable id FormControl', () => {
        const formGroup = service.createCityWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
