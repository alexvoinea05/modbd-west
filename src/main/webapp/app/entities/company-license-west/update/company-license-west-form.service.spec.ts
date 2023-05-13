import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../company-license-west.test-samples';

import { CompanyLicenseWestFormService } from './company-license-west-form.service';

describe('CompanyLicenseWest Form Service', () => {
  let service: CompanyLicenseWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyLicenseWestFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyLicenseWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyLicenseWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCompany: expect.any(Object),
            idLicense: expect.any(Object),
          })
        );
      });

      it('passing ICompanyLicenseWest should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyLicenseWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCompany: expect.any(Object),
            idLicense: expect.any(Object),
          })
        );
      });
    });

    describe('getCompanyLicenseWest', () => {
      it('should return NewCompanyLicenseWest for default CompanyLicenseWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompanyLicenseWestFormGroup(sampleWithNewData);

        const companyLicenseWest = service.getCompanyLicenseWest(formGroup) as any;

        expect(companyLicenseWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompanyLicenseWest for empty CompanyLicenseWest initial value', () => {
        const formGroup = service.createCompanyLicenseWestFormGroup();

        const companyLicenseWest = service.getCompanyLicenseWest(formGroup) as any;

        expect(companyLicenseWest).toMatchObject({});
      });

      it('should return ICompanyLicenseWest', () => {
        const formGroup = service.createCompanyLicenseWestFormGroup(sampleWithRequiredData);

        const companyLicenseWest = service.getCompanyLicenseWest(formGroup) as any;

        expect(companyLicenseWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompanyLicenseWest should not enable id FormControl', () => {
        const formGroup = service.createCompanyLicenseWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompanyLicenseWest should disable id FormControl', () => {
        const formGroup = service.createCompanyLicenseWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
