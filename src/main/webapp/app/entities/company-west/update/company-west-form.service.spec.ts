import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../company-west.test-samples';

import { CompanyWestFormService } from './company-west-form.service';

describe('CompanyWest Form Service', () => {
  let service: CompanyWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyWestFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            identificationNumber: expect.any(Object),
          })
        );
      });

      it('passing ICompanyWest should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            identificationNumber: expect.any(Object),
          })
        );
      });
    });

    describe('getCompanyWest', () => {
      it('should return NewCompanyWest for default CompanyWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompanyWestFormGroup(sampleWithNewData);

        const companyWest = service.getCompanyWest(formGroup) as any;

        expect(companyWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompanyWest for empty CompanyWest initial value', () => {
        const formGroup = service.createCompanyWestFormGroup();

        const companyWest = service.getCompanyWest(formGroup) as any;

        expect(companyWest).toMatchObject({});
      });

      it('should return ICompanyWest', () => {
        const formGroup = service.createCompanyWestFormGroup(sampleWithRequiredData);

        const companyWest = service.getCompanyWest(formGroup) as any;

        expect(companyWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompanyWest should not enable id FormControl', () => {
        const formGroup = service.createCompanyWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompanyWest should disable id FormControl', () => {
        const formGroup = service.createCompanyWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
