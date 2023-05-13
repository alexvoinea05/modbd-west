import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../app-user-west.test-samples';

import { AppUserWestFormService } from './app-user-west-form.service';

describe('AppUserWest Form Service', () => {
  let service: AppUserWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppUserWestFormService);
  });

  describe('Service methods', () => {
    describe('createAppUserWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAppUserWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            password: expect.any(Object),
            cnp: expect.any(Object),
          })
        );
      });

      it('passing IAppUserWest should create a new form with FormGroup', () => {
        const formGroup = service.createAppUserWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            password: expect.any(Object),
            cnp: expect.any(Object),
          })
        );
      });
    });

    describe('getAppUserWest', () => {
      it('should return NewAppUserWest for default AppUserWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAppUserWestFormGroup(sampleWithNewData);

        const appUserWest = service.getAppUserWest(formGroup) as any;

        expect(appUserWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewAppUserWest for empty AppUserWest initial value', () => {
        const formGroup = service.createAppUserWestFormGroup();

        const appUserWest = service.getAppUserWest(formGroup) as any;

        expect(appUserWest).toMatchObject({});
      });

      it('should return IAppUserWest', () => {
        const formGroup = service.createAppUserWestFormGroup(sampleWithRequiredData);

        const appUserWest = service.getAppUserWest(formGroup) as any;

        expect(appUserWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAppUserWest should not enable id FormControl', () => {
        const formGroup = service.createAppUserWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAppUserWest should disable id FormControl', () => {
        const formGroup = service.createAppUserWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
