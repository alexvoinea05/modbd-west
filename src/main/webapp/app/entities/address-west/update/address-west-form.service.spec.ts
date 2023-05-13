import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../address-west.test-samples';

import { AddressWestFormService } from './address-west-form.service';

describe('AddressWest Form Service', () => {
  let service: AddressWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddressWestFormService);
  });

  describe('Service methods', () => {
    describe('createAddressWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddressWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            streetNumber: expect.any(Object),
            street: expect.any(Object),
            zipcode: expect.any(Object),
            cityId: expect.any(Object),
          })
        );
      });

      it('passing IAddressWest should create a new form with FormGroup', () => {
        const formGroup = service.createAddressWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            streetNumber: expect.any(Object),
            street: expect.any(Object),
            zipcode: expect.any(Object),
            cityId: expect.any(Object),
          })
        );
      });
    });

    describe('getAddressWest', () => {
      it('should return NewAddressWest for default AddressWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAddressWestFormGroup(sampleWithNewData);

        const addressWest = service.getAddressWest(formGroup) as any;

        expect(addressWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddressWest for empty AddressWest initial value', () => {
        const formGroup = service.createAddressWestFormGroup();

        const addressWest = service.getAddressWest(formGroup) as any;

        expect(addressWest).toMatchObject({});
      });

      it('should return IAddressWest', () => {
        const formGroup = service.createAddressWestFormGroup(sampleWithRequiredData);

        const addressWest = service.getAddressWest(formGroup) as any;

        expect(addressWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddressWest should not enable id FormControl', () => {
        const formGroup = service.createAddressWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAddressWest should disable id FormControl', () => {
        const formGroup = service.createAddressWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
