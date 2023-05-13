import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuelTypeWest, NewFuelTypeWest } from '../fuel-type-west.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelTypeWest for edit and NewFuelTypeWestFormGroupInput for create.
 */
type FuelTypeWestFormGroupInput = IFuelTypeWest | PartialWithRequiredKeyOf<NewFuelTypeWest>;

type FuelTypeWestFormDefaults = Pick<NewFuelTypeWest, 'id'>;

type FuelTypeWestFormGroupContent = {
  id: FormControl<IFuelTypeWest['id'] | NewFuelTypeWest['id']>;
  code: FormControl<IFuelTypeWest['code']>;
  description: FormControl<IFuelTypeWest['description']>;
};

export type FuelTypeWestFormGroup = FormGroup<FuelTypeWestFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTypeWestFormService {
  createFuelTypeWestFormGroup(fuelTypeWest: FuelTypeWestFormGroupInput = { id: null }): FuelTypeWestFormGroup {
    const fuelTypeWestRawValue = {
      ...this.getFormDefaults(),
      ...fuelTypeWest,
    };
    return new FormGroup<FuelTypeWestFormGroupContent>({
      id: new FormControl(
        { value: fuelTypeWestRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(fuelTypeWestRawValue.code),
      description: new FormControl(fuelTypeWestRawValue.description),
    });
  }

  getFuelTypeWest(form: FuelTypeWestFormGroup): IFuelTypeWest | NewFuelTypeWest {
    return form.getRawValue() as IFuelTypeWest | NewFuelTypeWest;
  }

  resetForm(form: FuelTypeWestFormGroup, fuelTypeWest: FuelTypeWestFormGroupInput): void {
    const fuelTypeWestRawValue = { ...this.getFormDefaults(), ...fuelTypeWest };
    form.reset(
      {
        ...fuelTypeWestRawValue,
        id: { value: fuelTypeWestRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FuelTypeWestFormDefaults {
    return {
      id: null,
    };
  }
}
