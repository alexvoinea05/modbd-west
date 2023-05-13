import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../user-type-west.test-samples';

import { UserTypeWestFormService } from './user-type-west-form.service';

describe('UserTypeWest Form Service', () => {
  let service: UserTypeWestFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserTypeWestFormService);
  });

  describe('Service methods', () => {
    describe('createUserTypeWestFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createUserTypeWestFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            discount: expect.any(Object),
          })
        );
      });

      it('passing IUserTypeWest should create a new form with FormGroup', () => {
        const formGroup = service.createUserTypeWestFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            discount: expect.any(Object),
          })
        );
      });
    });

    describe('getUserTypeWest', () => {
      it('should return NewUserTypeWest for default UserTypeWest initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createUserTypeWestFormGroup(sampleWithNewData);

        const userTypeWest = service.getUserTypeWest(formGroup) as any;

        expect(userTypeWest).toMatchObject(sampleWithNewData);
      });

      it('should return NewUserTypeWest for empty UserTypeWest initial value', () => {
        const formGroup = service.createUserTypeWestFormGroup();

        const userTypeWest = service.getUserTypeWest(formGroup) as any;

        expect(userTypeWest).toMatchObject({});
      });

      it('should return IUserTypeWest', () => {
        const formGroup = service.createUserTypeWestFormGroup(sampleWithRequiredData);

        const userTypeWest = service.getUserTypeWest(formGroup) as any;

        expect(userTypeWest).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IUserTypeWest should not enable id FormControl', () => {
        const formGroup = service.createUserTypeWestFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewUserTypeWest should disable id FormControl', () => {
        const formGroup = service.createUserTypeWestFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
