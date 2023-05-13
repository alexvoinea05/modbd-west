import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAddressWest, NewAddressWest } from '../address-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAddressWest for edit and NewAddressWestFormGroupInput for create.
 */
type AddressWestFormGroupInput = IAddressWest | PartialWithRequiredKeyOf<NewAddressWest>;

type AddressWestFormDefaults = Pick<NewAddressWest, 'id'>;

type AddressWestFormGroupContent = {
  id: FormControl<IAddressWest['id'] | NewAddressWest['id']>;
  streetNumber: FormControl<IAddressWest['streetNumber']>;
  street: FormControl<IAddressWest['street']>;
  zipcode: FormControl<IAddressWest['zipcode']>;
  cityId: FormControl<IAddressWest['cityId']>;
};

export type AddressWestFormGroup = FormGroup<AddressWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AddressWestFormService {
  createAddressWestFormGroup(addressWest: AddressWestFormGroupInput = { id: null }): AddressWestFormGroup {
    const addressWestRawValue = {
      ...this.getFormDefaults(),
      ...addressWest,
    };
    return new FormGroup<AddressWestFormGroupContent>({
      id: new FormControl(
        { value: addressWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      streetNumber: new FormControl(addressWestRawValue.streetNumber),
      street: new FormControl(addressWestRawValue.street),
      zipcode: new FormControl(addressWestRawValue.zipcode),
      cityId: new FormControl(addressWestRawValue.cityId),
    });
  }

  getAddressWest(form: AddressWestFormGroup): IAddressWest | NewAddressWest {
    return form.getRawValue() as IAddressWest | NewAddressWest;
  }

  resetForm(form: AddressWestFormGroup, addressWest: AddressWestFormGroupInput): void {
    const addressWestRawValue = { ...this.getFormDefaults(), ...addressWest };
    form.reset(
      {
        ...addressWestRawValue,
        id: { value: addressWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AddressWestFormDefaults {
    return {
      id: null,
    };
  }
}
