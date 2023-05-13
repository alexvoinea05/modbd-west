import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserTypeWest, NewUserTypeWest } from '../user-type-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserTypeWest for edit and NewUserTypeWestFormGroupInput for create.
 */
type UserTypeWestFormGroupInput = IUserTypeWest | PartialWithRequiredKeyOf<NewUserTypeWest>;

type UserTypeWestFormDefaults = Pick<NewUserTypeWest, 'id'>;

type UserTypeWestFormGroupContent = {
  id: FormControl<IUserTypeWest['id'] | NewUserTypeWest['id']>;
  code: FormControl<IUserTypeWest['code']>;
  discount: FormControl<IUserTypeWest['discount']>;
};

export type UserTypeWestFormGroup = FormGroup<UserTypeWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserTypeWestFormService {
  createUserTypeWestFormGroup(userTypeWest: UserTypeWestFormGroupInput = { id: null }): UserTypeWestFormGroup {
    const userTypeWestRawValue = {
      ...this.getFormDefaults(),
      ...userTypeWest,
    };
    return new FormGroup<UserTypeWestFormGroupContent>({
      id: new FormControl(
        { value: userTypeWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(userTypeWestRawValue.code),
      discount: new FormControl(userTypeWestRawValue.discount),
    });
  }

  getUserTypeWest(form: UserTypeWestFormGroup): IUserTypeWest | NewUserTypeWest {
    return form.getRawValue() as IUserTypeWest | NewUserTypeWest;
  }

  resetForm(form: UserTypeWestFormGroup, userTypeWest: UserTypeWestFormGroupInput): void {
    const userTypeWestRawValue = { ...this.getFormDefaults(), ...userTypeWest };
    form.reset(
      {
        ...userTypeWestRawValue,
        id: { value: userTypeWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserTypeWestFormDefaults {
    return {
      id: null,
    };
  }
}
