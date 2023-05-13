import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../license-west.test-samples';

import { LicenseWestFormService } from './license-west-form.service';

describe('LicenseWest Form Service', () => {
  let service: LicenseWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LicenseWestFormService);
  });

  describe('Service methods', () => {
    describe('createLicenseWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createLicenseWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            licenseNumber: expect.any(Object),
            licenseDescription: expect.any(Object),
          })
        );
      });

      it('passing ILicenseWest should create a new form with FormGroup', () => {
        const formGroup = service.createLicenseWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            licenseNumber: expect.any(Object),
            licenseDescription: expect.any(Object),
          })
        );
      });
    });

    describe('getLicenseWest', () => {
      it('should return NewLicenseWest for default LicenseWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createLicenseWestFormGroup(sampleWithNewData);

        const licenseWest = service.getLicenseWest(formGroup) as any;

        expect(licenseWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewLicenseWest for empty LicenseWest initial value', () => {
        const formGroup = service.createLicenseWestFormGroup();

        const licenseWest = service.getLicenseWest(formGroup) as any;

        expect(licenseWest).toMatchObject({});
      });

      it('should return ILicenseWest', () => {
        const formGroup = service.createLicenseWestFormGroup(sampleWithRequiredData);

        const licenseWest = service.getLicenseWest(formGroup) as any;

        expect(licenseWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ILicenseWest should not enable id FormControl', () => {
        const formGroup = service.createLicenseWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewLicenseWest should disable id FormControl', () => {
        const formGroup = service.createLicenseWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
